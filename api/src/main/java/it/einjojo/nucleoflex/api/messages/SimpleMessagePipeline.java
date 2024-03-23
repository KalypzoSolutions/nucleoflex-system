package it.einjojo.nucleoflex.api.messages;

import java.util.Map;

public interface SimpleMessagePipeline {

    /**
     * Convert a string to a map
     *
     * @param s the string to convert
     * @return the map
     */
    Map<String, String> toMap(String s);

    /**
     * @param data the map to convert
     * @return the string
     */
    String fromMap(Map<String, String> data);


}
