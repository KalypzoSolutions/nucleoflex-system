package it.einjojo.nucleoflex.api.log;

import org.jetbrains.annotations.ApiStatus;

import java.util.logging.Logger;

@ApiStatus.Internal
public class InternalLogger {
    private final String name;
    private final Logger logger;
    private boolean debug = true;

    public InternalLogger(String name, boolean debug) {
        this.name = name;
        logger = Logger.getLogger(name, null);
        this.debug = debug;
    }


    public void info(String message) {
        logger.info(message);
    }

    public void severe(String message) {
        logger.severe(message);
    }

    public void warning(String message) {
        logger.warning(message);
    }

    public void fine(String message) {
        logger.fine(message);
    }

    public void debug(String message) {
        if (debug) logger.info(message);
    }

    public void setDebug(boolean enabled) {
        this.debug = enabled;
    }


}
