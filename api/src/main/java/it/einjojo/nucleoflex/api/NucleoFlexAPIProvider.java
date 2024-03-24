package it.einjojo.nucleoflex.api;

import org.jetbrains.annotations.ApiStatus;

public class NucleoFlexAPIProvider {

    @ApiStatus.Internal
    private static NucleoFlexAPI instance;

    @ApiStatus.Internal
    private NucleoFlexAPIProvider() {
        throw new UnsupportedOperationException("NucleoFlexAPIProvider is a utility class and cannot be instantiated.");
    }

    public static NucleoFlexAPI get() {
        if (instance == null) {
            throw new NotLoadedException();
        }
        return instance;
    }

    @ApiStatus.Internal
    public static void register(NucleoFlexAPI instance) {
        NucleoFlexAPIProvider.instance = instance;
    }

    @ApiStatus.Internal
    public static void unregister() {
        NucleoFlexAPIProvider.instance = null;
    }


    protected static class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE = "NucleoFlexAPIProvider is not loaded.";

        public NotLoadedException() {
            super(MESSAGE);
        }
    }
}
