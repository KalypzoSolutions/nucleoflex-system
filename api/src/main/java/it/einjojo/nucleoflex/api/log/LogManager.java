package it.einjojo.nucleoflex.api.log;

public interface LogManager {

    InternalLogger logger(String name);

    InternalLogger logger(Class<?> clazz);

    void setDebug(boolean enabled);

    boolean debug();

}
