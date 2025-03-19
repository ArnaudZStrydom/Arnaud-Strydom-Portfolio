public class BTree<T extends Comparable<T>> {
    public BTreeNode<T> root;
    public int m;

    public BTree(int m) {
        this.m = m;
        this.root = null;
    }

    public void insert(T data) {
        if (root == null) {
            root = new BTreeNode<>(m);
            root.nodeData[0] = data;
            root.numKeys++;
        } else {
            insert(root, data);
        }
    }

    private void insert(BTreeNode<T> node, T data) {
        if (node.isLeaf()) {
            insertIntoLeafNode(node, data);
            if (node.numKeys > m - 1) {
                splitNode(node);
            }
            
        } else {
            int index = findChildIndexToDescend(node, data);
            insert(node.nodeChildren[index], data);
            if (node.numKeys > m - 1) {
                splitNode(node);
            }
            
        }
        
    }

    private void insertIntoLeafNode(BTreeNode<T> node, T data) {
        int i = node.numKeys - 1;
        while (i >= 0 && data.compareTo((T)node.nodeData[i]) < 0) {
            node.nodeData[i + 1] = node.nodeData[i];
            i--;
        }
        node.nodeData[i + 1] = data;
        node.numKeys++;
    }

    private int findChildIndexToDescend(BTreeNode<T> node, T data) {
        int i = 0;
        while (i < node.numKeys && data.compareTo((T)node.nodeData[i]) >= 0) {
            i++;
        }
        return i;
    }

    private void splitNode(BTreeNode<T> node) {
        int midIndex = node.numKeys / 2 ;

        BTreeNode<T> rightNode = new BTreeNode<>(m);
        rightNode.parent = node.parent ;
    
        for (int i = midIndex + 1; i < node.numKeys ; i++) {
            rightNode.nodeData[i - midIndex - 1] = node.nodeData[i];
            node.nodeData[i] = null;
        }
        for (int i = midIndex + 1; i < node.numKeys + 1 ; i++) {
            rightNode.nodeChildren[i - midIndex - 1] = node.nodeChildren[i];
            if(rightNode.nodeChildren[i - midIndex - 1] != null){
                rightNode.nodeChildren[i - midIndex - 1].parent = rightNode ;
            }
            node.nodeChildren[i] = null;

        }


        rightNode.numKeys = node.numKeys - midIndex - 1;
        node.numKeys = midIndex ;
    
        if (node == root) {
            BTreeNode<T> newRoot = new BTreeNode<>(m);
            newRoot.nodeData[0] = node.nodeData[midIndex];
            node.nodeData[midIndex] = null ;
            newRoot.nodeChildren[0] = node;
            newRoot.nodeChildren[1] = rightNode;
            node.parent = newRoot;
            rightNode.parent = newRoot;
            newRoot.numKeys = 1;
            root = newRoot;
        } else {

            BTreeNode<T> parent = node.parent;
            Comparable<T> middleKey = node.nodeData[midIndex];
            int insertIndex = parent.numKeys ;
            while (insertIndex > 0 && parent.nodeData[insertIndex - 1].compareTo((T) middleKey) > 0) {
                parent.nodeData[insertIndex] = parent.nodeData[insertIndex - 1];
                parent.nodeChildren[insertIndex + 1] = parent.nodeChildren[insertIndex];
                insertIndex--;
            }
            parent.nodeData[insertIndex] = middleKey;

            parent.nodeChildren[insertIndex + 1] = rightNode;
            parent.numKeys++;
            node.nodeData[midIndex] = null ;
            rightNode.parent = parent;
        }
    }

    




    public String printPath(T key) {
        StringBuilder path = new StringBuilder();
        BTreeNode<T> node = root;
        
        while (node != null) {
            int i = 0;
            while (i < node.numKeys && key.compareTo((T) node.nodeData[i]) > 0 ) {
                path.append(node.getIndex(i)).append(" -> ");
                i++;
            }
            if (i < node.numKeys && key.compareTo((T) node.nodeData[i]) == 0) {
                path.append(node.getIndex(i));
                return path.toString();
            }
            
            if (node.nodeChildren[i] != null) {
                if(node.getIndex(i) != null){
                    path.append(node.getIndex(i)).append(" -> ");
                }
                
                node = node.descend(i);
            } else {
                
                break;
            }
        }
    
        path.append("Null");
        return path.toString();
    }

    /* -------------------------------------------------------------------------- */
    /* Please don't change this toString, I tried to make it pretty. */
    /* -------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------- */
    /* Also we may test against it */
    /* -------------------------------------------------------------------------- */

    @Override
    public String toString() {
        if (root == null) {
            return "The B-Tree is empty";
        }
        StringBuilder builder = new StringBuilder();
        buildString(root, builder, "", true);
        return builder.toString();
    }

    private void buildString(BTreeNode<T> node, StringBuilder builder, String prefix, boolean isTail) {
        if (node == null)
            return;

        builder.append(prefix).append(isTail ? "└── " : "├── ");
        for (int i = 0; i < node.nodeData.length; i++) {
            if (node.nodeData[i] != null) {
                builder.append(node.nodeData[i]);
                if (i < node.nodeData.length - 1 && node.nodeData[i + 1] != null) {
                    builder.append(", ");
                }
            }

        }
        if (node.parent != null)
            builder.append("\t(p:" + node.parent.nodeData[0].toString() + ")");
        builder.append("\n");

        int numberOfChildren = m;
        for (int i = 0; i < numberOfChildren; i++) {

            BTreeNode<T> child = node.descend(i);
            buildString(child, builder, prefix + (isTail ? "    " : "│   "), i == numberOfChildren - 1);
        }
    }
}
