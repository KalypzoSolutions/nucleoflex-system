package it.einjojo.nucleoflex.api.broker;

import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.List;

public record ChannelMessage(
        String channel,
        String messageTypeID,
        String content,
        ChannelSender sender,
        Collection<ChannelReceiver> recipients,
        String requestID
) {

    public static Builder builder() {
        return new Builder();
    }

    public static Builder responseTo(ChannelMessage message) {
        if (message.sender().equals(ChannelSender.self()))
            throw new IllegalArgumentException("Cannot respond to self");
        return new Builder(message)
                .content("")
                .recipient(ChannelReceiver.service(message.sender().name()));
    }

    boolean isRequest() {
        return requestID != null && !requestID.isEmpty();
    }

    public static class Builder {
        private String requestID;
        private String channel;
        private String messageTypeID = "";
        private String content = "";
        private Collection<ChannelReceiver> recipients = List.of(ChannelReceiver.all());

        public Builder() {
            this.requestID = null;
        }

        public Builder(ChannelMessage other) {
            this.messageTypeID = other.messageTypeID();
            this.requestID = other.requestID();
            this.channel = other.channel();
            this.content = other.content();
            recipients = other.recipients();

        }


        /**
         * In welchen Channel soll die Nachricht gesendet werden
         *
         * @return this
         */
        public Builder channel(String channel) {
            this.channel = channel;
            return this;
        }

        /**
         * @param messageTypeID Die ID des Nachrichtentyps, welche zum Identifizieren der Nachricht genutzt wird.
         *                      Das ist wichtig um die Verarbeitung des contents zu steuern.
         */
        public Builder messageTypeID(String messageTypeID) {
            this.messageTypeID = messageTypeID;
            return this;
        }

        /**
         * @param content Der Inhalt der Nachricht. Das kann ein JSON-String sein, oder ein einfacher Text, oder eine UUID.
         */
        public Builder content(String content) {
            this.content = content;
            return this;
        }


        public Builder recipient(ChannelReceiver recipient) {
            this.recipients = List.of(recipient);
            return this;
        }

        public Builder recipients(ChannelReceiver... recipients) {
            this.recipients = List.of(recipients);
            return this;
        }

        public Builder recipients(Collection<ChannelReceiver> recipients) {
            this.recipients = recipients;
            return this;
        }

        @ApiStatus.Internal
        public Builder requestID(String requestID) {
            this.requestID = requestID;
            return this;
        }

        public ChannelMessage build() {
            return new ChannelMessage(channel, messageTypeID, content, ChannelSender.self(), recipients, requestID);
        }
    }

}
