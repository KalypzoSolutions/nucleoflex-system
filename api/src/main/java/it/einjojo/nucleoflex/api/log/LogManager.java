package it.einjojo.nucleoflex.api.log;

import java.util.concurrent.ConcurrentHashMap;

public class LogManager {
    private static LogManager instance;
    private final ConcurrentHashMap<String, InternalLogger> loggers;
    private boolean debug;

    public LogManager() {
        loggers = new ConcurrentHashMap<>();
        debug = false;
    }

    public static InternalLogger getLogger(String name) {
        return get().logger(name);
    }

    public static InternalLogger getLogger(Class<?> clazz) {
        return get().logger(clazz);
    }

    public static LogManager get() {
        if (instance == null) {
            instance = new LogManager();
        }
        return instance;
    }

    public InternalLogger logger(String name) {
        InternalLogger logger = loggers.get(name);
        if (logger == null) {
            logger = new InternalLogger(name, debug);
            loggers.put(name, logger);
        }
        return logger;
    }

    public InternalLogger logger(Class<?> clazz) {
        return logger(clazz.getSimpleName());
    }

    public void setDebug(boolean enabled) {
        debug = enabled;
        loggers.values().forEach(logger -> logger.setDebug(enabled));
    }

    public boolean debug() {
        return debug;
    }

}
