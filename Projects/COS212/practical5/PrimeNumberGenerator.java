public class PrimeNumberGenerator {
    PrimeNode head;

    @Override
    public String toString() {
        String res = head.toString();
        PrimeNode ptr = head.next;
        while (ptr != null) {
            res += "->" + ptr.toString();
            ptr = ptr.next;
        }
        return res;
    }

    public PrimeNumberGenerator() {
        head = new PrimeNode(2);
    }

    public int currentPrime() {
        return head.value;
    }

    public int nextPrime() {
        if (head.next == null) {
            sieveOfEratosthenes();
        }
        head = head.next;
        return head.value;
    }

    public void sieveOfEratosthenes() {
        int Arrsize = head.value * 2 + 1;
        boolean[] notPrime = new boolean[Arrsize];
        int jump = 2;

        while (jump < Arrsize) {
            int counter = jump + jump;
            while (counter < Arrsize) {
                notPrime[counter] = true;
                counter += jump;
            }
            jump++;
        }

        PrimeNode currentNode = head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }

        for (int i = head.value + 1; i < Arrsize; i++) {
            if (!notPrime[i]) {
                PrimeNode newNode = new PrimeNode(i);
                currentNode.next = newNode;
                currentNode = newNode;
            }
        }
    }

}
