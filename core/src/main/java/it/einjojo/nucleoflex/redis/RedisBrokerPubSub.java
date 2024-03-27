package it.einjojo.nucleoflex.redis;

import com.google.gson.Gson;
import it.einjojo.nucleoflex.api.broker.ChannelMessage;
import redis.clients.jedis.JedisPubSub;

public class RedisBrokerPubSub extends JedisPubSub {

    private final RedisRequestService requestService;

    private final Gson gson;

    public RedisBrokerPubSub(RedisRequestService requestService, Gson gson) {
        this.requestService = requestService;

        this.gson = gson;
    }

    @Override
    public void onMessage(String channel, String message) {
        try {
            requestService.logger().debug("Received message on channel " + channel + ": " + message);
            ChannelMessage channelMessage = gson.fromJson(message, ChannelMessage.class);
            if (!requestService.isChannelMessageForMe(channelMessage)) return;
            if (requestService.completePendingRequest(channelMessage))
                return; // if the message is a response to a pending request, we don't need to forward it to the processors
            requestService.forwardToProcessors(channelMessage);
        } catch (Exception e) {
            requestService.logger().severe("Error while handling message: " + message);
            requestService.logger().severe("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
