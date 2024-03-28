import it.einjojo.nucleoflex.api.impl.ImplServer;
import it.einjojo.nucleoflex.api.server.ProxyServer;
import it.einjojo.nucleoflex.api.server.Server;
import it.einjojo.nucleoflex.server.ServerFactory;

public class DummyServerFactory implements ServerFactory {
    @Override
    public Server createServer(String serverName, String groupName) {
        return new ImplServer(serverName, groupName, null, null, null);
    }

    @Override
    public ProxyServer createProxyServer(String proxyName) {
        return null;
    }
}
