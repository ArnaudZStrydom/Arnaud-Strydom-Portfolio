public class BST<T extends Comparable<T>> {

    public BinaryNode<T> root;

    public BST() {
        this.root = null; 
    }

    public void delete(T data) {

        deleteRec(root,data);
    }

    private BinaryNode<T> deleteRec(BinaryNode<T> node, T data) {
        if(node == null){
            return node ;
        }

        int compres = data.compareTo( node.data );
        if(compres < 0){
            node.left = deleteRec(node.left, data);
        }
        else if(compres > 0){
            node.right = deleteRec(node.right, data);
        }
        else if (  node.left != null && node.right != null){
            node.data = findMindel(node.right).data;
            node.right = deleteRec(node.right,node.data);
        }
        else {
            node = ( node.left != null ) ? node.left : node.right;
        }
        return node ;
    }

    private BinaryNode<T> findMindel(BinaryNode<T> node) {
        if(node == null){
            return null ;
        }
        else if(node.left == null) {
            return node ;
        }
        return findMindel( node.left );
    }

    public boolean contains(T data) {

        return containsRecur(root, data);
    }

    private boolean containsRecur(BinaryNode<T> node, T data) {
        if (node == null) {
            return false; 
        }

        int compRes = data.compareTo(node.data);
        if (compRes < 0) {
            return containsRecur(node.left, data); 
        } else if (compRes > 0) {
            return containsRecur(node.right, data); 
        } else {
            return true; 
        }
    }

    public void insert(T data) {

        root = Recinsert(root, data);
    }

    private BinaryNode<T> Recinsert(BinaryNode<T> node, T data) {
        if (node == null) {
            return new BinaryNode<>(data);
        }
    
        int compRes = data.compareTo(node.data);
        if (compRes < 0) {
            node.left = Recinsert(node.left, data);
        } else if (compRes > 0) {
            node.right = Recinsert(node.right, data);
        }
        
        return node;
        
    }


    public int getHeight() {
        return getHeightRec(root);
    }


    private int getHeightRec(BinaryNode<T> node){
        if (node == null) {
            return 0; 
        }
    
        int leftHeight = getHeightRec(node.left);
        int rightHeight = getHeightRec(node.right);
    
        
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public String printSearchPath(T data) {

        StringBuilder SearchPath = new StringBuilder();
        printSearchPathRec(root, data, SearchPath);
        return SearchPath.toString();
    }

    private boolean  printSearchPathRec(BinaryNode<T> node, T data , StringBuilder SearchPath) {
        if (node == null) {
            SearchPath.append("Null");
            return false;
        }
    
        SearchPath.append(node.data);
        if (data.compareTo(node.data) == 0) {
            return true;
        }
    
        SearchPath.append(" -> ");
    
        if (data.compareTo(node.data) < 0) {
            return printSearchPathRec(node.left, data, SearchPath);
        } else {
            return printSearchPathRec(node.right, data, SearchPath);
        }
    }

    public int getNumLeaves() {
        return getNumLeavesRec(root);
    }

    private int getNumLeavesRec(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        }
    
        if (node.left == null && node.right == null) {
            return 1;
        }
    
        int leftLeaves = getNumLeavesRec(node.left);
        int rightLeaves = getNumLeavesRec(node.right);
    
        return leftLeaves + rightLeaves;
    }

    public BST<T> extractBiggestSuperficiallyBalancedSubTree() {
        BinaryNode<T> result = extractBiggestSuperficiallyBalancedSubTreeRec(root);
        BST<T> newTree = new BST<>();
        if (result != null) {
            cloneTree(result, newTree);
        }
        return newTree;
    }

    private BinaryNode<T> extractBiggestSuperficiallyBalancedSubTreeRec(BinaryNode<T> node) {
        if (node == null) {
            return null;
        }

        BinaryNode<T> leftResult = extractBiggestSuperficiallyBalancedSubTreeRec(node.left);
        BinaryNode<T> rightResult = extractBiggestSuperficiallyBalancedSubTreeRec(node.right);

    
        int leftCount = CountNodes(leftResult);
        int rightCount = CountNodes(rightResult);

        boolean leftisBalanced = isSuperficiallyBalancedRec(node.left);
        boolean rightisBalanced = isSuperficiallyBalancedRec(node.right);
        boolean isBalanced = isSuperficiallyBalancedRec(node);
        boolean foundbalencedleft= isSuperficiallyBalancedRec(leftResult) ;
        boolean foundbalencedright = isSuperficiallyBalancedRec(rightResult);


            
        if (leftCount > rightCount && leftisBalanced) {
            return leftResult;
        }

        else if (rightCount > leftCount && rightisBalanced) {
            return rightResult;
        }

        else if (isBalanced) {
            return node;
        }
        else if((foundbalencedleft) && leftResult!= null){
            return leftResult;
        }
        else if(foundbalencedright && rightResult!= null){
            return rightResult ;
        }
        
        return null;
        

        


    }
    


    private void cloneTree(BinaryNode<T> node, BST<T> newTree) {
        if (node == null) {
            return;
        }
        newTree.insert(node.data);
        cloneTree(node.left, newTree);
        cloneTree(node.right, newTree);
    }



    public BinaryNode<T> getNode(T data) {

        return getNodeRec(root , data);
    }

    
    private BinaryNode<T> getNodeRec(BinaryNode<T> node, T data) {
        if (node == null) {
            return null; 
        }

        int compRes = data.compareTo(node.data);
        if (compRes < 0) {
            return getNodeRec(node.left, data); 
        } else if (compRes > 0) {
            return getNodeRec(node.right, data); 
        } else {
            return node; 
        }
    }

    public boolean isSuperficiallyBalanced() {
        return isSuperficiallyBalancedRec(root);
    }

    private boolean isSuperficiallyBalancedRec(BinaryNode<T> node){
        if (node == null) {
            return true; 
        }

        int subleftcount = CountNodes(node.left);
        int subrightcount = CountNodes(node.right);

        if (subleftcount == subrightcount){
            return true ;
        }

        return false ;

    }

    private int CountNodes(BinaryNode<T> node){
        if (node == null){
            return 0;
        }

        return 1 + CountNodes(node.left) + CountNodes(node.right);
    }



    public BinaryNode<T> findMax() {
        return findmaxRec(root);
    }

    private BinaryNode<T> findmaxRec(BinaryNode<T> node){
        if(node == null){
            return null ;
        }
        else if(node.right == null) {
            return node ;
        }
        return findmaxRec( node.right );
    }

    public BinaryNode<T> findMin() {
        return findminRec(root);
 
    }

    private BinaryNode<T> findminRec(BinaryNode<T> node){
        if(node == null){
            return null ;
        }
        else if(node.left == null) {
            return node ;
        }
        return findminRec( node.left );
    }

    ///////////////

    private StringBuilder toString(BinaryNode<T> node, StringBuilder prefix, boolean isTail, StringBuilder sb) {
        if (node.right != null) {
            toString(node.right, new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
        }
        sb.append(prefix).append(isTail ? "└── " : "┌── ").append(node.data.toString()).append("\n");
        if (node.left != null) {
            toString(node.left, new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
        }
        return sb;
    }

    @Override
    public String toString() {
        return root == null ? "Empty tree" : toString(root, new StringBuilder(), true, new StringBuilder()).toString();
    }

}
