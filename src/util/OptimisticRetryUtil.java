package util;

public class OptimisticRetryUtil {
    public static final int MAX_RETRY_ATTEMPTS = 5;
    public static final int TIMEOUT_MILLISECONDS = 100;

    public static void expBackOff(int attempt) {
        int delay = Math.min(TIMEOUT_MILLISECONDS * (1 << (attempt - 1)), 5000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during backoff delay.");
        }
    }
}
