package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.broker.ChannelMessage;
import it.einjojo.nucleoflex.api.broker.RequestException;
import it.einjojo.nucleoflex.api.broker.RequestService;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * This abstract class provides a base for implementing the RequestService interface.
 * It includes a broker name and a timeout for requests.
 * It also provides methods for publishing requests and handling their responses.
 */
public abstract class AbstractRequestService implements RequestService {
    protected final String brokerName;
    protected final int timeout = 5000;
    protected Map<String, CompletableFuture<ChannelMessage>> pendingRequests;

    public AbstractRequestService(String brokerName) {
        this.brokerName = brokerName;
        pendingRequests = new ConcurrentHashMap<>();
    }

    @Override
    public String brokerName() {
        return brokerName;
    }


    @Override
    public CompletableFuture<ChannelMessage> publishRequest(ChannelMessage message) {
        return publishRequest(message, timeout);
    }

    /**
     * Publishes a request with a given message and a timeout.
     *
     * @param originalMessage The message to be published.
     * @param timeout         The timeout for the request.
     * @return A CompletableFuture that will complete when the request is responded to or the timeout is reached.
     * @throws RequestException If the request fails.
     */
    @Override
    public CompletableFuture<ChannelMessage> publishRequest(ChannelMessage originalMessage, long timeout) {
        var message = applyRequestID(originalMessage, generateRequestID());
        var future = new CompletableFuture<ChannelMessage>();
        future.orTimeout(timeout, TimeUnit.MILLISECONDS);
        future.handle((response, throwable) -> handleFutureCompletion(message, response, throwable));
        try {
            publish(message);
            pendingRequests.put(message.requestID(), future);
        } catch (Exception e) {
            future.completeExceptionally(e); // will be caught by handleFutureCompletion and rethrown
        }
        return future;
    }


    /**
     * Handles the completion of a future.
     *
     * @param request   The message that was sent with the request.
     * @param response  The response that was received, if any.
     * @param throwable The exception that was thrown, if any.
     * @return The message that was sent with the request.
     */
    protected ChannelMessage handleFutureCompletion(ChannelMessage request, @Nullable ChannelMessage response, @Nullable Throwable throwable) {
        if (throwable != null) {
            pendingRequests.remove(request.requestID());
            throw new RequestException(request, throwable);
        }
        pendingRequests.remove(request.requestID());
        return response;
    }

    /**
     * Returns the future of a request with a given request ID.
     *
     * @param requestID The ID of the request.
     * @return The future of the request.
     */
    @Override
    public CompletableFuture<ChannelMessage> getFutureOfRequest(String requestID) {
        return pendingRequests.get(requestID);
    }

    /**
     * Generates a unique request ID.
     *
     * @return The generated request ID.
     */
    protected String generateRequestID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Applies a request ID to a message.
     *
     * @param message   The message to which to apply the request ID.
     * @param requestID The request ID to apply.
     * @return The message with the applied request ID.
     */
    protected ChannelMessage applyRequestID(ChannelMessage message, String requestID) {
        return new ChannelMessage.Builder(message).requestID(requestID).build();
    }

    @Override
    public String toString() {
        return "AbstractRequestService{" +
                "brokerName='" + brokerName + '\'' +
                ", timeout=" + timeout +
                ", pendingRequests=" + pendingRequests.keySet() +
                '}';
    }
}
