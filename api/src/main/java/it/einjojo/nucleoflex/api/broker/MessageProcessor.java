package it.einjojo.nucleoflex.api.broker;

public interface MessageProcessor {

    /**
     * Process a message received from a channel
     *
     * @param message the message to process
     */
    void processMessage(ChannelMessage message);

    default String processingChannel() {
        return BrokerService.DEFAULT_CHANNEL;
    }
}
