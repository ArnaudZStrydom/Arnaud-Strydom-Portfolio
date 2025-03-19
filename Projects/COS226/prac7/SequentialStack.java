import java.util.concurrent.locks.ReentrantLock;

public class SequentialStack<T> {
    private Node<T> top;
    private final ReentrantLock lock = new ReentrantLock();

    private static class Node<T> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    public void push(T value) {
        lock.lock();
        try {
            Node<T> newNode = new Node<>(value);
            newNode.next = top;
            top = newNode;
        } finally {
            lock.unlock();
        }
    }

    public T pop() {
        lock.lock();
        try {
            if (top == null) {
                throw new IllegalStateException("Stack is empty");
            }
            T value = top.value;
            top = top.next;
            return value;
        } finally {
            lock.unlock();
        }
    }
}


