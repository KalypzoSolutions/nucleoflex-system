package it.einjojo.nucleoflex.api.log;

import org.jetbrains.annotations.ApiStatus;

import java.util.logging.Logger;

@ApiStatus.Internal
public class InternalLogger extends Logger {
    boolean debug = true;

    public InternalLogger(String name, boolean debug) {
        super(name, null);
        this.debug = debug;
    }

    protected InternalLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public void debug(String message) {
        this.info(message);
    }

    public void setDebug(boolean enabled) {
        this.debug = enabled;
    }


}
