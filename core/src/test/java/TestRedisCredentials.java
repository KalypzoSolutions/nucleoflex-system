import it.einjojo.nucleoflex.redis.RedisCredentials;

public class TestRedisCredentials implements RedisCredentials {
    @Override
    public String host() {
        return "localhost";
    }

    @Override
    public int port() {
        return 6379;
    }

    @Override
    public String username() {
        return null;
    }

    @Override
    public String password() {
        return null;
    }
}
