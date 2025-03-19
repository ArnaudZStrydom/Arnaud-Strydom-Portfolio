public class Main {
    public static void main(String[] args) {
        String inputString = "{30{20{15{}{}}{17{}{}}}{10{8{}{}}{6{}{}}}}";
        MaxSkewHeap heap = new MaxSkewHeap(inputString);
        System.out.println("Heap constructed from input string:");
        System.out.println(heap.toString());
        heap.insert(21);
        System.out.println("Heap inserted 21:");
        System.out.println(heap.toString());
        heap.insert(50);
        System.out.println("Heap inserted 50:");
        System.out.println(heap.toString());
        heap.insert(80);
        System.out.println("Heap inserted 80:");
        System.out.println(heap.toString());
        heap.insert(5);
        System.out.println("Heap inserted 5:");
        System.out.println(heap.toString());
        Node searchedNode = heap.search(10);
        System.out.println("Value of searched node = " + searchedNode.toString() );
        System.out.println("Value of searched node = " + heap.toStringOneLine(searchedNode) );

        heap.remove(10);
        System.out.println("Heap deleted 10:");
        System.out.println(heap.toString());
        String searchPath = heap.searchPath(6);
        System.out.println("Heap path to 6:");
        System.out.println(searchPath);
        System.out.println(heap.toStringOneLine());
        String searchPath2 = heap.searchPath(10);
        System.out.println("Heap path to 10:");
        System.out.println(searchPath2);
        System.out.println(heap.isLeftist());
        System.out.println(heap.isRightist());

        MaxSkewHeap heap1 = new MaxSkewHeap(inputString);
        System.out.println(heap1.isLeftist());
        System.out.println(heap1.isRightist());
        heap1.insert(5);
        System.out.println(heap1.isLeftist());
        System.out.println(heap1.isRightist());

     
        MaxSkewHeap heap2 = new MaxSkewHeap();
        System.out.println(heap2.isLeftist());
        System.out.println(heap2.isRightist());
        String searchPath3 = heap2.searchPath(10);
        System.out.println("Heap path to 10:");
        heap2.insert(5);
        System.out.println(heap2.isLeftist());
        System.out.println(heap2.isRightist());

    }
}