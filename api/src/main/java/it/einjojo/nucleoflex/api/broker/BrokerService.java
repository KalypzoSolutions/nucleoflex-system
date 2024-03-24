package it.einjojo.nucleoflex.api.broker;

/**
 * This interface represents a service that can connect to a broker and publish/subscribe to channels.
 */
public interface BrokerService extends AutoCloseable {
    /**
     * @return the name of the broker
     */
    String brokerName();

    /**
     * Connect to the broker
     */
    void connect();

    /**
     * @param message the message to publish
     */
    void publish(ChannelMessage message);

    /**
     * @param channel the channel to subscribe to
     */
    void subscribe(String channel);

    /**
     * Process a message received from the broker
     *
     * @param message the message to process
     */
    void processMessage(ChannelMessage message);


}
