package it.einjojo.nucleoflex.api.messages;

import net.kyori.adventure.text.Component;

public interface MessageManager {
    String message(String key);
    Component withMiniMessage(String key);
    void setMessage(String key, String message);
}
