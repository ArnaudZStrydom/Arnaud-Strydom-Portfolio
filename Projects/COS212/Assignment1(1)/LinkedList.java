public class LinkedList {
    public CoordinateNode head;

    public LinkedList() {
        head = null ;
    }

    public LinkedList(int x, int y) {
        head = new CoordinateNode(x, y);
    }

    public void append(int x, int y) {
        appendRec(head,x,y);
    }

    private void appendRec(CoordinateNode current , int x , int y){
        if(current == null){
            head = new CoordinateNode(x, y);
        }
        else if(current.next != null){
             appendRec(current.next , x, y);
        }
        else{
            current.next = new CoordinateNode(x, y);
        }
    }

    public void appendList(LinkedList other) {
        if (other.head != null) {
            if (head == null) {
                head = new CoordinateNode(other.head.x, other.head.y);
                appendListRec(head, other.head.next);
            }
            else{
                appendListRec(head, other.head);
            }
            
        }
    }
    
    private void appendListRec(CoordinateNode current, CoordinateNode otherCurrent) {
        if (current.next != null) {
            appendListRec(current.next, otherCurrent);
        } else if(otherCurrent != null) {
            current.next = new CoordinateNode(otherCurrent.x, otherCurrent.y);
            if (otherCurrent.next != null) {
                appendListRec(current.next, otherCurrent.next);
            }
        }
    }

    public boolean contains(int x, int y) {
        if(head != null){
            return containsRec(head , x , y);
        }
        else {
            return false ;
        }
    }

    private boolean containsRec(CoordinateNode current , int x , int y){
        if (current == null){
            return false ;
        }
        if((current.x == x) && (current.y ==  y)){
            return true ;
        }

        return containsRec( current.next,  x ,  y);
    
    }

    @Override
    public String toString() {
        StringBuilder Mystring = new StringBuilder();
        if (head == null) {
            Mystring.append("Empty List");
        } else {
            toStringRec(Mystring, head);
        }
        return Mystring.toString();

    }

    private void toStringRec(StringBuilder Mystring,CoordinateNode current){
        Mystring.append("[").append(current.x).append(",").append(current.y).append("]");
        if(current.next != null){
            Mystring.append(" -> ");
            toStringRec(Mystring, current.next);
        }
    }

    public int length() {
        if(head == null){
            return 0 ;
        }
        return lengthRec(head);
    }

    private int lengthRec(CoordinateNode current){
        if(current == null){
            return 0 ;
        }
        return 1 + lengthRec(current.next);
    }


    public LinkedList reversed() {
        LinkedList revList = new LinkedList();
        revList.head = revRec(head);
        return revList ;
    }

    private CoordinateNode revRec(CoordinateNode current){
        if(current == null || current.next == null){
            return current;
        }

        CoordinateNode revHead = revRec(current.next);
        current.next.next = current ;
        current.next = null ;
        
        return revHead;
    }
}
