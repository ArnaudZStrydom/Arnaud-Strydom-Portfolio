public class MaxSkewHeap {
    Node root;

    @Override
    public String toString() {
        return (root == null ? "Empty Tree" : toString(root, "", true));
    }

    public String toString(Node node, String prefix, boolean end) {
        String res = "";
        if (node.right != null) {
            res += toString(node.right, prefix + (end ? "│   " : "    "), false);
        }
        res += prefix + (end ? "└── " : "┌── ") + node.toString() + "\n";
        if (node.left != null) {
            res += toString(node.left, prefix + (end ? "    " : "│   "), true);
        }
        return res;
    }

    public String toStringOneLine() {
        return (root == null ? "{}" : toStringOneLine(root));
    }

    public String toStringOneLine(Node ptr) {
        if (ptr == null) {
            return "{}";
        }
        return "{" + ptr.toString() + toStringOneLine(ptr.left) + toStringOneLine(ptr.right) + "}";
    }

    public MaxSkewHeap() {
        root = null ;   
    }

    public MaxSkewHeap(String input) {
        
        root = MaxSkewHeaprec(input);
    }

    private Node MaxSkewHeaprec(String recinput){
        if(recinput.equals("{}")){
            return null ;
        }
        recinput = recinput.substring(1 , recinput.length()-1);
        int OpeningBrac = recinput.indexOf("{");
        String Scurrentdata = (recinput.substring(0, OpeningBrac)).trim();
        int Icurrentdata = Integer.parseInt(Scurrentdata);

        int openBraces = 1;
        int closedBraces = 0;
        int i = OpeningBrac + 1 ;
        while (openBraces != closedBraces) {
            if (recinput.charAt(i) == '{') {
                openBraces++;
            } else if (recinput.charAt(i) == '}') {
                closedBraces++;
            }
            i++;
        }

        String leftRep = recinput.substring(OpeningBrac, i  );
        String rightRep = recinput.substring(i , recinput.length() );

        Node leftChild = MaxSkewHeaprec(leftRep);
        Node rightChild = MaxSkewHeaprec(rightRep);

        Node curnode = new Node(Icurrentdata);
        curnode.left = leftChild;
        curnode.right = rightChild;
        return curnode;

    }

    public void insert(int data) {
        if (contains(root, data)) {
            return;
        }

        Node insertNode = new Node(data);


        root = merge(root, insertNode);

    }

    private Node merge(Node heap1, Node heap2) {
        if (heap1 == null) {
            return heap2;
        }
        if (heap2 == null) {
            return heap1;
        }
    
        if (heap1.data > heap2.data) {

            Node temp = heap1.right;
            heap1.right = heap1.left;
            heap1.left = temp;
            heap1.left = merge(heap1.left, heap2);
            return heap1;

        } else {
            
            Node temp = heap2.right;
            heap2.right = heap2.left;
            heap2.left = temp;
            heap2.left = merge(heap2.left, heap1);
            return heap2;

        }
    }

    private boolean contains(Node node, int data) {
        if (node == null) {
            return false;
        }
        if (node.data == data) {
            return true;
        }
        return contains(node.left, data) || contains(node.right, data);
    }

    public void remove(int data) {
        if(!contains(root,data)){
            return;
        }

        root = remove(root, data);

    }

    private Node remove(Node node, int data) {
        if (node == null) {
            return null;
        }
    
        if (node.data == data) {
            return merge(node.left, node.right);
        }
    
        node.left = remove(node.left, data);
        node.right = remove(node.right, data);
    
        return node;
    }

    public Node search(int value) {
        return searchrec(root, value);
    }

    private Node searchrec(Node node, int value) {
        if (node == null) {
            return null;
        }
    
        if (node.data == value) {
            return node;
        }
    
        if (node.data < value) {
            return null;
        }
    
        
        Node rightResult = searchrec(node.right, value);
        if (rightResult != null) {
            return rightResult; 
        }
    
        return searchrec(node.left, value); 
    }

    public String searchPath(int value) {
        if (root == null) {
            return ""; 
        }
        StringBuilder str = new StringBuilder();
        Boolean found = false ;
        found = searchpathrec(root,value ,str , found);
        if(!found){
            int delete = str.length() - 2;
            str.delete(delete, str.length());
        }
        return str.toString();
    }

    private boolean searchpathrec(Node node , int value , StringBuilder str , boolean found){
        if (node == null) {
            return false ;
        }
    
        if (node.data == value) {
           str.append("[" + node.data + "]");
           found = true ;
           return found;
        }else{
            str.append(node.data + "->");
        }
    
        if (node.data < value) {
            return false ;
        }
    
        
        if(!found){
            found = searchpathrec(node.right, value,str,found);
        }
        

        if(!found){
            found =  searchpathrec(node.left, value ,str,found); 
        }

        if(!found){
            return false;
        }

        return true ;
        
    }

    public boolean isLeftist() {
        return isLeft(root);
    }
    private boolean isLeft(Node node) {
    
        if (node == null) {
            return true;
        }

        int lNPL = NPL(node.left);
        int rNPL = NPL(node.right);
    
        if (lNPL < rNPL) {
            return false;
        }
        return isLeft(node.left) && isLeft(node.right);
    }
    
    private int NPL(Node node) {
        if (node == null) {
            return 0;
        }

        int lNPL = NPL(node.left);
        int rNPL = NPL(node.right);

        return Math.max(lNPL, rNPL) + 1;
    }

    public boolean isRightist() {
        return isRight(root);
    }

    private boolean isRight(Node node) {
    
        if (node == null) {
            return true;
        }

        int lNPL = NPL(node.left);
        int rNPL = NPL(node.right);
    
        if (lNPL > rNPL) {
            return false;
        }
        return isRight(node.left) && isRight(node.right);
    }
}
