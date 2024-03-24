package it.einjojo.nucleoflex.api.impl;

import it.einjojo.nucleoflex.api.broker.ChannelMessage;
import it.einjojo.nucleoflex.api.broker.RequestException;
import it.einjojo.nucleoflex.api.broker.RequestService;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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

    @Override
    public CompletableFuture<ChannelMessage> publishRequest(ChannelMessage originalMessage, long timeout) {
        var message = applyRequestID(originalMessage, generateRequestID());
        var future = new CompletableFuture<ChannelMessage>();
        future.orTimeout(timeout, TimeUnit.MILLISECONDS);
        future.handle(this::futureCompletion);
        try {
            publish(message);
            pendingRequests.put(message.requestID(), future);
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        return future;
    }

    public ChannelMessage futureCompletion(ChannelMessage message, Throwable throwable) {
        if (throwable != null) {
            pendingRequests.remove(message.requestID());
            throw new RequestException(throwable);
        }
        pendingRequests.remove(message.requestID());
        return message;
    }

    @Override
    public CompletableFuture<ChannelMessage> getFutureOfRequest(String requestID) {
        return pendingRequests.get(requestID);
    }


    protected String generateRequestID() {
        return UUID.randomUUID().toString();
    }

    protected ChannelMessage applyRequestID(ChannelMessage message, String requestID) {
        return new ChannelMessage.Builder(message).requestID(requestID).build();
    }
}
