// LockFreeStack.java
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T> {
    protected AtomicReference<Node<T>> top = new AtomicReference<>(null);

    protected static class Node<T> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        while (true) {
            Node<T> currentTop = top.get();
            newNode.next = currentTop;
            if (top.compareAndSet(currentTop, newNode)) {
                return;
            }
        }
    }

    public T pop() {
        while (true) {
            Node<T> currentTop = top.get();
            if (currentTop == null) {
                throw new IllegalStateException("Stack is empty");
            }
            Node<T> newTop = currentTop.next;
            if (top.compareAndSet(currentTop, newTop)) {
                return currentTop.value;
            }
        }
    }
}