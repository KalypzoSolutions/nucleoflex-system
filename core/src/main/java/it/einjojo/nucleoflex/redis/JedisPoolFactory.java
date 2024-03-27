package it.einjojo.nucleoflex.redis;

import com.google.common.base.Preconditions;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPool;

public class JedisPoolFactory {

    public JedisPool createJedisPool(RedisCredentials credentials) {
        Preconditions.checkNotNull(credentials, "Credentials must not be null");
        Preconditions.checkNotNull(credentials.host(), "Host must not be null");
        HostAndPort hostAndPort = new HostAndPort(credentials.host(), credentials.port());
        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .clientName("nucleoflex")
                .user(credentials.username())
                .password(credentials.password())
                .build();
        return new JedisPool(hostAndPort, config);
    }


}
