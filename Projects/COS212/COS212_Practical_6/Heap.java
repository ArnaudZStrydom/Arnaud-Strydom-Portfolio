public abstract class Heap<T extends Comparable<T>> {

    public Comparable<T>[] data;
    public int size;

    public Heap() {
        this.data = (Comparable<T>[]) new Comparable[2];
        size = 0;
    }

    public Heap(Comparable<T>[] array) {
        this.data = array;
        this.size = array.length;
        heapify();
    }


    private void heapify() {
        int Index = ((size-1)-1)/2; 
      
        while (Index >= 0) {
            Comparable<T> parent = data[Index];
            int currentIndex = Index ;

            while (currentIndex * 2 + 1 < size ) {
                int leftChildIndex = currentIndex * 2 + 1;
                int rightChildIndex = currentIndex * 2 + 2;

                int largerChildIndex = leftChildIndex; 
                if (rightChildIndex < size && compare(data[rightChildIndex],data[leftChildIndex])) {
                    largerChildIndex = rightChildIndex;

                }


                if (compare(data[largerChildIndex],parent)) {
                    swap(currentIndex, largerChildIndex);
                    currentIndex = largerChildIndex ;
                    parent = data[currentIndex];
                    
                } else {
                    break; 
                }

                    
            }
            
            
            Index--;
            
        }
    }

    private void swap(int i, int j) {
        Comparable<T> temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    protected abstract boolean compare(Comparable<T> child, Comparable<T> parent);

    public void push(T item) {
        if (size == data.length) {
            resize();
        }
        
        data[size] = item;
        size++;
        

        heapifyUp(size - 1);
    }

    private void heapifyUp(int index) {
        while (index > 0 && compare(data[index], data[(index - 1) / 2])) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void resize() {
        Comparable<T>[] newData = (Comparable<T>[]) new Comparable[data.length * 2];
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }

    public Comparable<T> pop() {
        if (size == 0) {
            return null; 
        }
        
        Comparable<T> root = data[0];
        
        
        data[0] = data[size - 1];
        size--;
        
        
        data[size] = null;
        
        
        heapifyDown(0);
        
        return root;
    }

    private void heapifyDown(int index) {
        while (index * 2 + 1 < size ) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = leftChildIndex + 1;
            
            int largerChildIndex = leftChildIndex; 
            if (rightChildIndex < size && compare(data[rightChildIndex],data[leftChildIndex])) {
                largerChildIndex = rightChildIndex;
            }
            
            if (compare(data[largerChildIndex], data[index]) ) {
                swap(largerChildIndex, index);
                index = largerChildIndex;
            } else {
                break;
            }
        }
    }

    public Comparable<T> peek() {
        if (size == 0) return null;
        return data[0];
    }

    /*
     * 
     * Functions used for the toString
     * Do not change them but feel free to use them
     * 
     */

    private int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    private int getRightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildString(0, "", true, sb); // Start from the root
        return sb.toString();
    }

    private void buildString(int index, String prefix, boolean isTail, StringBuilder sb) {
        if (index < size) {
            String linePrefix = isTail ? "└── " : "┌── ";
            if (getRightChildIndex(index) < size) { // Check if there is a right child
                buildString(getRightChildIndex(index), prefix + (isTail ? "|   " : "    "), false, sb);
            }
            sb.append(prefix).append(linePrefix).append(data[index]).append("\n");
            if (getLeftChildIndex(index) < size) { // Check if there is a left child
                buildString(getLeftChildIndex(index), prefix + (isTail ? "    " : "│   "), true, sb);
            }
        }
    }

}
