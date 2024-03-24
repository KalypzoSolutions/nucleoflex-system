package it.einjojo.nucleoflex.api.broker;

public interface BrokerService extends AutoCloseable {
    String brokerName();

    void publish(ChannelMessage message);


    @Override
    default void close() {

    }
}
