import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class BackoffLock {
    private final AtomicBoolean state = new AtomicBoolean(false);
    private final int minDelay, maxDelay;
    private final Random random = new Random();

    public BackoffLock(int minDelay, int maxDelay) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
    }

    public void lock() throws InterruptedException {
        int delay = minDelay;
        while (true) {
            if (!state.get() && !state.getAndSet(true)) {
                return; // Lock acquired
            }
            // Exponential backoff
            LockSupport.parkNanos(random.nextInt(delay));
            if (delay < maxDelay) {
                delay = Math.min(maxDelay, delay * 2);
            }
        }
    }

    public void unlock() {
        state.set(false);
    }
}


