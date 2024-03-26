package it.einjojo.nucleoflex.api.broker;

/**
 * This interface represents a service that can connect to a broker and publish/subscribe to channels.
 */
public interface BrokerService {
    /**
     * @return the name of the broker
     */
    String brokerName();

    /**
     * Connect to the broker
     */
    void connect();

    /**
     * Disconnect from the broker
     */
    void disconnect();

    /**
     * @param message the message to publish
     */
    void publish(ChannelMessage message);

    /**
     * @param channel the channel to subscribe to
     */
    void subscribe(String channel);

    /**
     * @param channel the channel to unsubscribe from
     */
    void unsubscribe(String channel);


    /**
     * Register a processor to process messages received from the broker
     *
     * @param processor the processor to register
     */
    void registerMessageProcessor(MessageProcessor processor);

    /**
     * Disconnect from the broker
     *
     * @param processor the processor to unregister
     * @return true if the processor was unregistered, false otherwise
     */
    boolean unregisterMessageProcessor(MessageProcessor processor);

    /**
     * @param message the message to forward to the processors
     */
    void forwardToProcessors(ChannelMessage message);


}
