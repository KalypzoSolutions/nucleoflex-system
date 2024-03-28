package it.einjojo.nucleoflex.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceUtil {

    public static void close(ExecutorService service) { // Copied from ExecutorService because it is not available in Java 17
        boolean terminated = service.isTerminated();
        if (!terminated) {
            service.shutdown();
            boolean interrupted = false;
            while (!terminated) {
                try {
                    terminated = service.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    if (!interrupted) {
                        service.shutdownNow();
                        interrupted = true;
                    }
                }
            }
        }
    }

}
