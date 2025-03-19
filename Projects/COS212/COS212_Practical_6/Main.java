
public class Main {
    public static void main(String[] args) throws Exception {
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        
        maxHeap.push(5);
        maxHeap.push(3);
        maxHeap.push(8);
        maxHeap.push(2);
        maxHeap.push(7);
        maxHeap.push(6);
        maxHeap.push(13);
        maxHeap.push(655);
        maxHeap.push(525);
        
        
        System.out.println("Max-Heap:");
        System.out.println(maxHeap.toString());
        
        int size = maxHeap.size ;
        System.out.println("Removing elements from the Max-Heap:");
        for (int i = 0 ; i < size + 1 ; i++) {
            System.out.println("Popped element: " + maxHeap.pop());
            System.out.println("Heap after popping:");
            System.out.println(maxHeap);
        }

        MinHeap<Integer> minHeap = new MinHeap<>();
        
        // Add elements to the heap
        minHeap.push(5);
        minHeap.push(3);
        minHeap.push(8);
        minHeap.push(2);
        minHeap.push(7);
        minHeap.push(6);
        minHeap.push(13);
        minHeap.push(655);
        minHeap.push(525);
        
        
        System.out.println("Min-Heap:");
        System.out.println(minHeap.toString());
        
         size = minHeap.size ;
        System.out.println("Removing elements from the Min-Heap:");
        for (int i = 0 ; i < size + 1; i++) {
            System.out.println("Popped element: " + minHeap.pop());
            System.out.println("Heap after popping:");
            System.out.println(minHeap);
        }

        Integer[] maxheaparray = {5,3,8,2,7,6,13,655,525};
        MaxHeap<Integer> maxHeap1 = new MaxHeap<>(maxheaparray);
        System.out.println("Max-Heap:");
        System.out.println(maxHeap1.toString());
        
         size = maxHeap1.size ;
        System.out.println("Removing elements from the Max-Heap:");
        for (int i = 0 ; i < size + 1 ; i++) {
            System.out.println("Popped element: " + maxHeap1.pop());
            System.out.println("Heap after popping:");
            System.out.println(maxHeap1.toString());
        }

        Integer[] minheaparray = {5,3,8,2,7,6,13,655,525};
        MinHeap<Integer> minHeap1 = new MinHeap<>(minheaparray);
        System.out.println("Min-Heap:");
        System.out.println(minHeap1.toString());
        
         size = minHeap1.size ;
        System.out.println("Removing elements from the Min-Heap:");
        for (int i = 0 ; i < size + 1 ; i++) {
            System.out.println("Popped element: " + minHeap1.pop());
            System.out.println("Heap after popping:");
            System.out.println(minHeap1.toString());
        }

        minHeap1.peek();

    }
}


    

