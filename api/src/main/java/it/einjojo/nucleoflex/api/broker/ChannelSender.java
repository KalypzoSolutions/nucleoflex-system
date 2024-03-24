package it.einjojo.nucleoflex.api.broker;

import it.einjojo.nucleoflex.api.NucleoFlexAPIProvider;

import java.util.Objects;

/**
 * @param name der Name des Senders
 */
public record ChannelSender(String name) {
    private static ChannelSender self;

    public static ChannelSender of(String name) {
        return new ChannelSender(name);
    }

    public static ChannelSender self() {
        if (self == null) {
            self = new ChannelSender(NucleoFlexAPIProvider.get().server().serverName());
        }
        return self;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelSender that = (ChannelSender) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
