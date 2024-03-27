package it.einjojo.nucleoflex.api.broker;

public class RequestException extends RuntimeException {
    private static final String MESSAGE = "The request failed!";

    private final ChannelMessage sentMessage;

    public RequestException(ChannelMessage sentMessage, Throwable cause) {
        super(MESSAGE + " Request: " + sentMessage.requestID(), cause);
        this.sentMessage = sentMessage;
    }

    public String requestId() {
        return sentMessage.requestID();
    }


}
