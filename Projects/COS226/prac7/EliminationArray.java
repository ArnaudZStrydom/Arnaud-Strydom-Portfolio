// EliminationArray.java
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.ThreadLocalRandom;

public class EliminationArray<T> {
    private static final int CAPACITY = 8;
    private final AtomicStampedReference<ExchangeCell<T>>[] array;
    private final int duration;

    private static class ExchangeCell<T> {
        final T value;
        final boolean isPush;

        ExchangeCell(T value, boolean isPush) {
            this.value = value;
            this.isPush = isPush;
        }
    }

    @SuppressWarnings("unchecked")
    public EliminationArray(int duration) {
        this.duration = duration;
        array = new AtomicStampedReference[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            array[i] = new AtomicStampedReference<>(null, 0);
        }
    }

    public T visit(T value, boolean isPush) throws TimeoutException {
        int position = ThreadLocalRandom.current().nextInt(CAPACITY);
        long startTime = System.nanoTime();
        int[] stamp = new int[1];
        ExchangeCell<T> cell = new ExchangeCell<>(value, isPush);
        
        while ((System.nanoTime() - startTime) < duration * 1_000_000L) {
            ExchangeCell<T> current = array[position].get(stamp);
            
            if (current == null) {
                // Try to place our exchange cell
                if (array[position].compareAndSet(null, cell, stamp[0], stamp[0] + 1)) {
                    // Wait briefly for a partner
                    Thread.yield();
                    current = array[position].get(stamp);
                    
                    // Clear our cell
                    array[position].compareAndSet(cell, null, stamp[0], stamp[0] + 1);
                    
                    if (current != cell && current != null && current.isPush != isPush) {
                        return isPush ? null : current.value;
                    }
                }
            } else if (current.isPush != isPush) {
                // Try to exchange with existing cell
                if (array[position].compareAndSet(current, null, stamp[0], stamp[0] + 1)) {
                    return isPush ? null : current.value;
                }
            }
            
            // Randomized backoff
            if (ThreadLocalRandom.current().nextInt(2) == 0) {
                Thread.yield();
            }
            
            // Try a different position
            position = (position + 1) % CAPACITY;
        }
        
        throw new TimeoutException("Elimination attempt timed out");
    }
}