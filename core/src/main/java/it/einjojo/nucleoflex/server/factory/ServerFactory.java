package it.einjojo.nucleoflex.server.factory;

import it.einjojo.nucleoflex.api.server.Server;

public interface ServerFactory {

    Server createServer(String serverName, String groupName);

}
