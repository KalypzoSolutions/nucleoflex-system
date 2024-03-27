package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.broker.*;
import it.einjojo.nucleoflex.api.log.InternalLogger;
import it.einjojo.nucleoflex.api.log.LogManager;
import it.einjojo.nucleoflex.api.server.Server;
import org.jetbrains.annotations.Nullable;

import java.util.*;
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
    protected final Server server;
    protected final int timeout = 5000;
    protected final InternalLogger logger = LogManager.getLogger(getClass());
    protected final Map<String, Set<MessageProcessor>> messageProcessors = new HashMap<>();
    protected Map<String, CompletableFuture<ChannelMessage>> pendingRequests;


    public AbstractRequestService(String brokerName, Server server) {
        this.brokerName = brokerName;
        this.server = server;
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
            logger.debug("Request failed: " + request);
            throw new RequestException(request, throwable);
        }
        pendingRequests.remove(request.requestID());
        logger.debug("Request completed: " + request);
        return response;
    }

    /**
     * Returns the future of a request with a given request ID.
     *
     * @param requestID The ID of the request.
     * @return The future of the request.
     */
    @Override
    public @Nullable CompletableFuture<ChannelMessage> getFutureOfRequest(String requestID) {
        if (requestID == null) return null;
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

    protected void printHandlingException(MessageProcessor processor, Exception ex) {
        logger.severe("=-=-=[ Message-Process-Exception ]=-=-=");
        logger.severe("Error handling message with processor: " + processor.getClass().getName());
        ex.printStackTrace();
        logger.severe("=-=-=[ Message-Process-Exception ]=-=-=");
    }


    public boolean registerMessageProcessor(String channel, MessageProcessor processor) {
        Set<MessageProcessor> processors = messageProcessors.computeIfAbsent(channel, k -> new HashSet<>());
        return processors.add(processor);
    }


    public boolean unregisterMessageProcessor(String channel, MessageProcessor processor) {
        Set<MessageProcessor> processors = messageProcessors.get(channel);
        return processors != null && processors.remove(processor);
    }

    /**
     * @return true if a pending request was completed, false otherwise.
     */
    public boolean completePendingRequest(ChannelMessage message) {
        CompletableFuture<ChannelMessage> future = getFutureOfRequest(message.requestID());
        if (future == null) {
            return false;
        }
        future.complete(message);
        return true;
    }

    public void forwardToProcessors(ChannelMessage message) {
        Set<MessageProcessor> processors = messageProcessors.get(message.channel());
        if (processors == null) {
            logger.debug("Received message but no processors registered for channel: " + message.channel());
            return;
        }

        for (MessageProcessor processor : processors) {
            try {
                logger.debug("Forwarding message (" + message.messageTypeID() + ") to processor: " + processor.getClass().getSimpleName());
                processor.processMessage(message);
            } catch (Exception e) {
                printHandlingException(processor, e);
            }
        }
    }

    public boolean isChannelMessageForMe(ChannelMessage message) {
        for (ChannelReceiver recipient : message.recipients()) {
            ChannelReceiver.Type type = recipient.type();
            String name = recipient.name();
            if (type.equals(ChannelReceiver.Type.SERVER) && name.equals(server.serverName())) return true;
            if (type.equals(ChannelReceiver.Type.ALL)) return true;
            if (type.equals(ChannelReceiver.Type.GROUP) && name.equals(server.groupName())) return true;
        }
        return false;
    }

    public InternalLogger logger() {
        return logger;
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
