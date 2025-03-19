public class BTreeNode<T extends Comparable<T>> {
    public Comparable<T>[] nodeData;
    public BTreeNode<T>[] nodeChildren;
    public BTreeNode<T> parent;
    public int size;
    public int numKeys;

    @SuppressWarnings("unchecked")
    public BTreeNode(int size) {
        this.size = size;
        this.nodeData = new Comparable[size];
        this.nodeChildren = new BTreeNode[size + 1];
        this.parent = null;
        this.numKeys = 0;
    }

    public Comparable<T> getIndex(int i) {
        if (i >= 0 && i < nodeData.length) {
            return (T) nodeData[i];
        } else {
            return null;
        }
    }

    public BTreeNode<T> ascend() {
        return parent;
    }

    public BTreeNode<T> descend(int i) {
        if (i >= 0 && i < nodeChildren.length) {
            return nodeChildren[i];
        } else {
            return null;
        }

    }

    public void numKeys(){
        for(int i = 0 ; i < size; i++){
            if(nodeData[i] != null ){
                numKeys++;
            }
        }
    }

    public boolean isLeaf() {
        return nodeChildren[0] == null;
    }


    /* -------------------------------------------------------------------------- */
    /* Helpers */
    /* -------------------------------------------------------------------------- */

    public String toString() {
        String out = "|";
        for (int i = 0; i < nodeData.length; i++) {
            if (nodeData[i] == null) {
                out += "null|";
            } else {
                out += nodeData[i].toString() + "|";
            }

        }
        return out;
    }

}
