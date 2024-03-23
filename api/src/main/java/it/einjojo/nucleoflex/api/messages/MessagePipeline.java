package it.einjojo.nucleoflex.api.messages;

import java.util.Map;

public interface MessagePipeline {

    Map<String, String> deserialize(String s);

    String serialize(Map<String, String> data);


}
