package it.einjojo.nucleoflex.redis;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import it.einjojo.nucleoflex.api.broker.ChannelMessage;
import it.einjojo.nucleoflex.api.impl.AbstractRequestService;
import it.einjojo.nucleoflex.api.server.Server;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisRequestService extends AbstractRequestService {

    private final Set<String> subscribedChannels = new HashSet<>();
    private final RedisBrokerPubSub pubSub;

    private final JedisPool pool;
    private final Gson gson;

    private ExecutorService pubSubService;
    private Jedis jedis;
    private boolean connected;

    public RedisRequestService(JedisPool pool, Gson gson, Server whoAmI) {
        super("RedisBroker", whoAmI);
        this.gson = gson;
        pubSub = new RedisBrokerPubSub(this, gson);
        this.pool = pool;

    }


    @Override
    public void connect() {
        if (connected) return;
        if (subscribedChannels.isEmpty()) {
            logger.info("Currently no channels to subscribe to. Skipping establishing connection.");
            return;
        }
        jedis = pool.getResource();
        this.pubSubService = Executors.newSingleThreadExecutor();
        logger.info("Connecting to redis pub sub service...");
        pubSubService.execute(() -> {
            jedis.subscribe(pubSub, subscribedChannels.toArray(new String[0]));
        });
    }

    @Override
    public void disconnect() {
        if (!connected) return;
        Preconditions.checkArgument(jedis != null, "Jedis not initialized");
        logger.info("Disconnecting from redis pub sub service...");
        pubSub.unsubscribe();
        jedis.close(); // return to pool
        jedis = null;
        pubSubService.close(); // shutdown
        connected = false;
        logger.info("Disconnected from redis pub sub service");
    }

    @Override
    public void publish(ChannelMessage message) {
        try (Jedis jedis = pool.getResource()) {
            String payload = gson.toJson(message);
            jedis.publish(message.channel(), payload);
            logger.debug("Published message to channel: " + message.channel() + " with payload: " + payload);
        }
    }

    @Override
    public void subscribe(String channel) {
        subscribedChannels.add(channel);
        if (!connected) {
            connect(); // When connecting to redis, it will automatically subscribe to the channels provided in set
        } else {
            pubSub.subscribe(channel);
            logger.info("Subscribed to channel: " + channel + "(" + pubSub.getSubscribedChannels() + ") on redis pub sub service");
        }

    }

    @Override
    public void unsubscribe(String channel) {
        subscribedChannels.remove(channel);
        if (!connected) return; // No need to unsubscribe if not connected
        pubSub.unsubscribe(channel);
        logger.info("Unsubscribed from channel: " + channel + "(" + pubSub.getSubscribedChannels() + ") on redis pub sub service");
        if (subscribedChannels.isEmpty()) {
            logger.info("Unsubscribed from all channels. ");
            disconnect();
        }
    }


}