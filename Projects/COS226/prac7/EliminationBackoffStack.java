// EliminationBackoffStack.java

import java.util.concurrent.ThreadLocalRandom;

public class EliminationBackoffStack<T> extends LockFreeStack<T> {
    private static final int ELIMINATION_DURATION = 1; // Duration in milliseconds
    private final EliminationArray<T> eliminationArray = new EliminationArray<>(ELIMINATION_DURATION);
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Override
    public void push(T value) {
        while (true) {
            try {
                // Try normal push first
                Node<T> newNode = new Node<>(value);
                Node<T> currentTop = top.get();
                newNode.next = currentTop;
                if (top.compareAndSet(currentTop, newNode)) {
                    return;
                }
                
                // On contention, try elimination
                eliminationArray.visit(value, true);
                return; // If no exception, elimination succeeded
                
            } catch (TimeoutException e) {
                // If elimination fails, retry the whole operation
                Thread.yield();
            }
        }
    }

    @Override
    public T pop() {
        while (true) {
            // Try normal pop first
            Node<T> currentTop = top.get();
            if (currentTop == null) {
                throw new IllegalStateException("Stack is empty");
            }
            Node<T> newTop = currentTop.next;
            if (top.compareAndSet(currentTop, newTop)) {
                return currentTop.value;
            }
            
            // On contention, try elimination
            try {
                T value = eliminationArray.visit(null, false);
                if (value != null) {
                    return value;
                }
            } catch (TimeoutException e) {
                // If elimination fails, retry the whole operation
                Thread.yield();
            }
            
            // Small backoff before retry
            if (random.nextInt(2) == 0) {
                Thread.yield();
            }
        }
    }
}