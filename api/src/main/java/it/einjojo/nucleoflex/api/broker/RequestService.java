package it.einjojo.nucleoflex.api.broker;

import java.util.concurrent.CompletableFuture;

/**
 * This interface represents a service that sends requests (ChannelMessages that expect a response)
 */
public interface RequestService extends BrokerService {

    /**
     * Send a request to a service
     *
     * @param message the message to send
     * @return a CompletableFuture that will be completed when the response is received
     * @throws IllegalArgumentException if the message is not a request
     * @throws IllegalStateException    if the service is not connected
     */
    CompletableFuture<ChannelMessage> publishRequest(ChannelMessage message);

    /**
     * Send a request to a service with a timeout
     *
     * @param message the message to send
     * @param timeout the timeout in milliseconds
     * @return a CompletableFuture that will be completed when the response is received
     */
    CompletableFuture<ChannelMessage> publishRequest(ChannelMessage message, long timeout);

    /**
     * Get the future for a request with the given request ID
     *
     * @param requestID the request ID
     * @return the future for the request
     */
    CompletableFuture<ChannelMessage> getFutureOfRequest(String requestID);


}
