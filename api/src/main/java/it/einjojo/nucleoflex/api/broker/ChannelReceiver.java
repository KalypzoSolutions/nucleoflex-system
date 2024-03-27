package it.einjojo.nucleoflex.api.broker;

import it.einjojo.nucleoflex.api.NucleoFlexAPIProvider;

public record ChannelReceiver(Type type, String name) {
    private static final ChannelReceiver ALL = new ChannelReceiver(Type.ALL, null);

    public static ChannelReceiver all() {
        return ALL;
    }

    public static ChannelReceiver group(String name) {
        return new ChannelReceiver(Type.GROUP, name);
    }

    public static ChannelReceiver server(String name) {
        return new ChannelReceiver(Type.SERVER, name);
    }

    public static ChannelReceiver selfGroup() {
        return group(NucleoFlexAPIProvider.get().server().groupName());
    }

    public enum Type {
        ALL,
        GROUP,
        SERVER
    }

}
