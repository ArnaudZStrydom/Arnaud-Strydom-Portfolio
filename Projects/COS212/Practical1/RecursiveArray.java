public class RecursiveArray {
    public Integer[] array;

    public RecursiveArray() {
        array = new Integer[0];
    }

    public RecursiveArray(String input) {
        if(input.isEmpty()){
            array = new Integer[0]; 
        }
        else{
            String[] values = input.split("\\s*,\\s*");
            array = new Integer[values.length];
            helpintoarray(values, 0);
        }
    }

    private void helpintoarray(String[] values, int index){
        if(index == values.length){
            return;
        }
        
        array[index]= Integer.parseInt(values[index]);
        helpintoarray(values, index + 1);
    }

    @Override
    public String toString() {
        if (array.length == 0) {
            return "Empty Array";
        } else {
            return "[" + toStringhelper(0, "") + "]";
        }
    }


    private String toStringhelper(int index , String result){
        if (index == array.length - 1) {
            return result + String.valueOf(array[index]);
        } else {
            return toStringhelper(index + 1, result + array[index].toString() + ",");
        }  
    }

    public void append(Integer value) {
        Integer[] prepArray = new Integer[array.length + 1];
        appendhelper(array, prepArray, value, 0);
        array = prepArray;
    }

    private void appendhelper(Integer[] og, Integer[] nw, Integer value, int index) {
        if (index < og.length) {
            nw[index] = og[index];
            appendhelper(og, nw, value, index + 1);
        } else {
            nw[index] = value;
        }
    }

    public void prepend(Integer value) {
        Integer[] prepArray = new Integer[array.length + 1];
        prependhelper(array, prepArray, value, 0);
        array = prepArray;
    }

    private void prependhelper(Integer[] og, Integer[] nw, Integer value, int index) {
    
        nw[0] = value;
        if (index < og.length) {
            nw[index + 1] = og[index];
            prependhelper(og, nw, value, index + 1);
        }
    }

    public boolean contains(Integer value) {
        return containshelper(array, value, 0);
    }

    private boolean containshelper(Integer[] arr, Integer value, int index) {
        if (index == arr.length) {
            return false;
        } else if (arr[index].equals(value)) {
            return true;
        } else {
            return containshelper(arr, value, index + 1);
        }
    }

    public boolean isAscending() {
        return isAscendingHelper(array, 0);
    }

    private boolean isAscendingHelper(Integer[] arr, int index) {
        if (index >= arr.length - 1) {
            return true;
        } else if (arr[index] > arr[index + 1]) {
            return false;
        } else {
            return isAscendingHelper(arr, index + 1);
        }
    }
    public boolean isDescending() {
        return isDescendingHelper(array, 0);
    }

    private boolean isDescendingHelper(Integer[] arr, int index) {
        if (index >= arr.length - 1) {
            return true;
        } else if (arr[index] < arr[index + 1]) {
            return false;
        } else {
            return isDescendingHelper(arr, index + 1);
        }
    }

    public void sortAscending() {
        sortAscendingHelper(array, array.length , 0 , array.length - 1 );
        
    }

    private void sortAscendingHelper(Integer[] arr, int length , int cIndex, int eIndex) {
        if (length <= 1 || eIndex <= 0 ) {
            return;
        }
    
        if (cIndex >= eIndex) {
            cIndex = 0;
            eIndex--;
        }
    
        if (arr[cIndex] > arr[cIndex + 1]) {
            int temp = arr[cIndex];
            arr[cIndex] = arr[cIndex + 1];
            arr[cIndex + 1] = temp;
        }
    
    
        sortAscendingHelper(arr, length , cIndex + 1 , eIndex );
    }

    
    
        
   
    public void sortDescending() {
        sortDescendingHelper(array, array.length , 0 , array.length - 1 );
    }

    private void sortDescendingHelper(Integer[] arr, int length , int cIndex, int eIndex) {
        if (length <= 1 || eIndex <= 0 ) {
            return;
        }
    
        if (cIndex >= eIndex) {
            cIndex = 0;
            eIndex--;
        }
    
        if (arr[cIndex] < arr[cIndex + 1]) {
            int temp = arr[cIndex];
            arr[cIndex] = arr[cIndex + 1];
            arr[cIndex + 1] = temp;
        }
    
    
        sortDescendingHelper(arr, length , cIndex + 1 , eIndex );
    }

}
