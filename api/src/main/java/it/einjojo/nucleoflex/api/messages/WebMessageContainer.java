package it.einjojo.nucleoflex.api.messages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


public class WebMessageContainer implements MessageContainer {
    private final URL webUrl;
    private final SimpleMessagePipeline pipeline;
    private final Map<String, String> messages;
    private MiniMessage miniMessage;


    public WebMessageContainer(String webUrl, SimpleMessagePipeline pipeline) throws MalformedURLException {
        this(new URL(webUrl), pipeline, null);
    }

    public WebMessageContainer(URL webUrl, SimpleMessagePipeline pipeline, MiniMessage miniMessage) {
        this.webUrl = webUrl;
        this.pipeline = pipeline;
        this.miniMessage = miniMessage;
        this.messages = new HashMap<>();
    }

    public void load() {
        StringBuilder builder = new StringBuilder();
        try {
            URLConnection connection = webUrl.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            messages.putAll(pipeline.toMap(builder.toString()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Can not save messages to web.");
    }

    @Override
    public String message(String key) {
        String message = messages.get(key);
        if (message == null) throw new IllegalArgumentException("Message with key " + key + " not found.");
        return message;
    }

    @Override
    public Component withMiniMessage(String key) {
        if (miniMessage == null) throw new IllegalStateException("MiniMessage is not provided.");
        return miniMessage.deserialize(message(key));
    }

    @Override
    public void setMessage(String key, String message) {
        messages.put(key, message);
    }

    public void setMiniMessage(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }
}
