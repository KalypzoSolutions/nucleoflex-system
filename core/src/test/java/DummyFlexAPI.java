import it.einjojo.nucleoflex.api.NucleoFlexAPI;
import it.einjojo.nucleoflex.api.economy.EconomyManager;
import it.einjojo.nucleoflex.api.messages.MessageContainer;
import it.einjojo.nucleoflex.api.player.PlayerContainerManager;
import it.einjojo.nucleoflex.api.player.PlayerManager;
import it.einjojo.nucleoflex.api.server.NetworkManager;
import it.einjojo.nucleoflex.api.server.Server;

public class DummyFlexAPI implements NucleoFlexAPI {
    private final Server server;

    public DummyFlexAPI(Server server) {
        this.server = server;
    }

    @Override
    public MessageContainer messages() {
        return null;
    }

    @Override
    public PlayerManager playerManager() {
        return null;
    }

    @Override
    public NetworkManager networkManager() {
        return null;
    }

    @Override
    public EconomyManager economyManager() {
        return null;
    }

    @Override
    public PlayerContainerManager playerContainerManager() {
        return null;
    }

    @Override
    public Server server() {
        return null;
    }
}
