public class LinkedList {
    private Node head;

    public LinkedList() {
        head = null;
    }

    public LinkedList(int value) {
        head = new Node(value);
    }

    public LinkedList(int[] values) {
        for (int i : values) {
            insert(i);
        }
    }

    public void insert(int value) {
        if (head == null) {
            head = new Node(value);
            return;
        }
        Node newNode = new Node(value);
        if (value < head.value) {
            newNode.next = head;
            head = newNode;
            return;
        }
        Node ptr = head;
        while (ptr.next != null && ptr.next.value <= value) {
            ptr = ptr.next;
        }
        newNode.next = ptr.next;
        ptr.next = newNode;
    }

    public boolean contains(int value) {
        Node ptr = head;
        while (ptr != null) {
            if (ptr.value == value) {
                return true;
            }
            ptr = ptr.next;
        }
        return false;
    }

    public void delete(int value) {
        if (!contains(value)) {
            return;
        }
        if (head.value == value) {
            head = head.next;
            return;
        }
        Node ptr = head;
        while (ptr.next.value != value) {
            ptr = ptr.next;
        }
        ptr.next = ptr.next.next;

    }

    @Override
    public String toString() {
        if (head == null) {
            return "Empty";
        }
        String res = "";
        Node ptr = head;
        while (ptr != null) {
            if (ptr != head) {
                res += "->";
            }
            res += ptr.toString();
            ptr = ptr.next;
        }
        return res;
    }
}
