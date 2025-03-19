import java.util.concurrent.atomic.AtomicBoolean;

public class TTASLock {
    private final AtomicBoolean state = new AtomicBoolean(false);

    public void lock() {
        while (true) {
            if (!state.get()) {  // Check if the lock is free
                if (!state.getAndSet(true)) {
                    return; // Lock acquired
                }
            }
        }
    }

    public void unlock() {
        state.set(false);
    }
}


