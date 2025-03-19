public class Node {
    public int value;
    public Node next;

    public Node(int v) {
        value = v;
        next = null;
    }

    public Node(int v, Node n) {
        value = v;
        next = n;
    }

    @Override
    public String toString() {
        return "[" + value + "]";
    }
}
