import com.google.gson.Gson;
import it.einjojo.nucleoflex.api.broker.*;
import it.einjojo.nucleoflex.api.log.LogManager;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.redis.JedisPoolFactory;
import it.einjojo.nucleoflex.redis.RedisRequestService;
import it.einjojo.nucleoflex.server.ServerFactory;
import redis.clients.jedis.JedisPool;

public class RequestServiceTest implements MessageProcessor{
    private final JedisPool pool;
    private final Server server1;
    private final Server server2;
    RequestService service1;
    RequestService service2;
    private final Gson gson = new Gson();

    public RequestServiceTest() {
        this.pool = new JedisPoolFactory().createJedisPool(new TestRedisCredentials());
        ServerFactory serverFactory = new NullServerFactory();
        server1 = serverFactory.createServer("server1", "server");
        server2 = serverFactory.createServer("server2", "server");
    }

    public static void main(String[] args) throws InterruptedException {
        new RequestServiceTest().run();
    }

    public void run() throws InterruptedException {
        LogManager.get().setDebug(true);
        System.out.println("Starting test");
        service1 = new RedisRequestService(pool, gson, server1);
        service1.connect();
        System.out.println("Connected to server1");
        service2 = new RedisRequestService(pool, gson, server2);
        service2.registerMessageProcessor(this);
        service1.subscribe(BrokerService.DEFAULT_CHANNEL);
        service2.subscribe(BrokerService.DEFAULT_CHANNEL);
        service1.publishRequest(ChannelMessage.builder()
                .sender(ChannelSender.of(server1.serverName()))
                .messageTypeID("d")
                .content("Hallo an den Server2").recipient(ChannelReceiver.server(server2.serverName()))
                .build()).whenComplete((response, throwable) -> {
            System.out.println("Received response on request: " + response);
        });
        System.out.println("Published Request");
        Thread.sleep(2000);
        service1.disconnect();
        service2.disconnect();
    }


    @Override
    public void processMessage(ChannelMessage message) {
        System.out.println("Received message: " + message);
        var reponse = ChannelMessage.responseTo(message, true).sender(ChannelSender.of(server2.serverName())).content("Hallo an Server1").build();
        service2.publish(reponse);
    }
}
