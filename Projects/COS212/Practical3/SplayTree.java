public class SplayTree {
    public Node root;
    /*
     * The functions below this line was given
     */

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
        return (root == null ? "Empty Tree" : "{" + toStringOneLine(root) + "}");
    }

    public String toStringOneLine(Node node) {
        return node.toString()
                + (node.left == null ? "{}" : "{" + toStringOneLine(node.left) + "}")
                + (node.right == null ? "{}" : "{" + toStringOneLine(node.right) + "}");
    }

    public SplayTree() {
        root = null;
    }

    /*
     * The functions above this line was given
     */

     public SplayTree(String input) {
        if (input.equals("Empty Tree")) {
        } else {
            root = null ;
            insertNodes(input);
            
        }
    }

    private void insertNodes(String input) {
        int startIndex = input.indexOf("[");
        while (startIndex != -1) {
            int endIndex = input.indexOf("]", startIndex);
            String nodeString = input.substring(startIndex + 1, endIndex);
            insert(parseNode(nodeString));
            startIndex = input.indexOf("[", endIndex + 1); 
        }
    }

    private Node parseNode(String nodeString) {
        int colonIndex = nodeString.indexOf(":");
        int studentNumber = Integer.parseInt(nodeString.substring(1, colonIndex)); 
        Integer mark = null;
        int percentIndex = nodeString.indexOf('%', colonIndex);
        String nullstr = nodeString.substring(colonIndex + 1, percentIndex + 1);
        if( nullstr.equals("null%")){
            mark = null ;
        }
        else if (percentIndex != -1) {
            mark = Integer.parseInt(nodeString.substring(colonIndex + 1, percentIndex)); 
        }

        return new Node(studentNumber, mark);
    }

    private void insert(Node newNode) {
        if (root == null) {
            root = newNode;
            return;
        }
        insertNode(root, newNode);
    }

    private void insertNode(Node current, Node newNode) {
        if (newNode.compareTo(current) < 0) {
            if (current.left == null) {
                current.left = newNode;
                newNode.parent = current; 
            } else {
                insertNode(current.left, newNode);
            }
        } else {
            if (current.right == null) {
                current.right = newNode;
                newNode.parent = current; 
            } else {
                insertNode(current.right, newNode);
            }
        }
    }





    public Node access(int studentNumber) {
        return access(studentNumber, null);
    }

    public Node access(int studentNumber, Integer mark) {
       
        if(contains(studentNumber) && mark != null){
            Node accnode = getNode(root, studentNumber);
            splay(accnode);
            updatemark(root, studentNumber, mark);
            return root ;
        }
        else if (contains(studentNumber) && mark == null){
            Node accnode = getNode(root, studentNumber);
            splay(accnode);
            return root ;
        }
        else{
            insert(studentNumber,mark);
            Node accnode = getNode(root, studentNumber);
            splay(accnode);
            return root ;
        }

    }

    private boolean contains(Integer studentNumber) {
        return containsRecur(root, studentNumber);
    }

    private boolean containsRecur(Node node, Integer studentNumber) {
        if (node == null) {
            return false; 
        }

        int compRes = studentNumber.compareTo(node.studentNumber);
        if (compRes < 0) {
            return containsRecur(node.left, studentNumber); 
        } else if (compRes > 0) {
            return containsRecur(node.right, studentNumber); 
        } else {
            return true; 
        }
    }



    private void updatemark(Node node, Integer studentNumber , Integer mark){
        while(node != null ){
            int compRes = studentNumber.compareTo(node.studentNumber);
            if(compRes == 0){
                node.mark = mark ;
                return;
            }
            else if (compRes < 0) {
                node = node.left;
            } else if (compRes > 0) {
                node = node.right ;
            }
        }
    }



    public void splay(Node current) {
        while(current.parent != null) { 
          if(current.parent == this.root) { 
            if(current == current.parent.left) {
              this.rightRotate(current.parent);
            }
            else {
              this.leftRotate(current.parent);
            }
          }
          else {
            Node parentNode = current.parent;
            Node grandparentNode = parentNode.parent; 
      
            if(current.parent.left == current && parentNode.parent.left == parentNode) { 
              this.rightRotate(grandparentNode);
              this.rightRotate(parentNode);
            }
            else if(current.parent.right == current && parentNode.parent.right == parentNode) { 
              this.leftRotate(grandparentNode);
              this.leftRotate(parentNode);
            }
            else if(current.parent.right == current && parentNode.parent.left == parentNode) {
              this.leftRotate(parentNode);
              this.rightRotate(grandparentNode);
            }
            else if(current.parent.left == current && parentNode.parent.right == parentNode) {
              this.rightRotate(parentNode);
              this.leftRotate(grandparentNode);
            }
          }
        }
      }

private Node getNode(Node root, int studentNumber) {
    Node current = root;
    while (current != null) {
        if (studentNumber == current.studentNumber) {
            return current; 
        } else if (studentNumber < current.studentNumber) {
            current = current.left; 
        } else {
            current = current.right; 
        }
    }
    return null; 
}


private void insert(int studentNumber, Integer mark) {
    root = recInsert(root, null, studentNumber, mark);
}

private Node recInsert(Node node, Node parent, int studentNumber, Integer mark) {
    if (node == null) {
        Node newNode = new Node(studentNumber, mark);
        newNode.parent = parent; 
        return newNode;
    }

    int compRes = Integer.compare(studentNumber, node.studentNumber);
    if (compRes < 0) {
        node.left = recInsert(node.left, node, studentNumber, mark); 
    } else if (compRes > 0) {
        node.right = recInsert(node.right, node, studentNumber, mark); 
    }

    return node;
}

    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if(y.left != null) {
          y.left.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == null) { 
          this.root = y;
        }
        else if(x == x.parent.left) {
          x.parent.left = y;
        }
        else { 
          x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
      }
      
      public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if(y.right != null) {
          y.right.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == null) { 
          this.root = y;
        }
        else if(x == x.parent.right) { 
          x.parent.right = y;
        }
        else { 
          x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
      }

      public Node remove(int studentNumber) {

        Node removedNode = access(studentNumber);
        
        if (removedNode == null) {
            return null;
        }
        
       
        Node leftChild = removedNode.left;
        Node rightChild = removedNode.right;
        
        if (removedNode == root && leftChild == null) {
            root = rightChild;

            if (root != null) {
                root.parent = null;
            }
            return removedNode;
        }

        removedNode.left = null ;
        removedNode.right = null ;
        
        
        root = leftChild;
 
            if (leftChild != null) {
                leftChild.parent = null;
            }
        
        
        if (root == null) {
            root = rightChild;
            if (root != null) {
                root.parent = null;
            }
            return removedNode;
        }
        

        Node largestNode = findLargestNode(root);
        
        root = access(largestNode.studentNumber);
        
        root.right = rightChild;
        rightChild.parent = root ;
        
 
        
        return removedNode;
    }
    

private Node findLargestNode(Node node) {
    while (node.right != null) {
        node = node.right;
    }
    return node;
}

    public String sortByStudentNumber() {
        if (root == null){
            return "Empty Tree";
        }
        StringBuilder sortedTree = new StringBuilder();
        sortedTree.append(sortByStudentNumberRec(root));
        return sortedTree.toString();
    }

    private String sortByStudentNumberRec(Node node) {
        if (node == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(sortByStudentNumberRec(node.left));
        result.append(node.toString());
        result.append(sortByStudentNumberRec(node.right));
        return result.toString();
    }


    public String sortByMark() {
        if (root == null){
            return "Empty Tree";
        }
        
        StringBuilder nodeListAsString = new StringBuilder();
        traverse(root, nodeListAsString);
        String[] nodeArray = nodeListAsString.toString().split("]");
        
        bubbleSortByMark(nodeArray);
       
        StringBuilder sortedTree = new StringBuilder();
        for (int i = 0; i < nodeArray.length; i++) {
            
            sortedTree.append(nodeArray[i]);
            sortedTree.append("]");
            
        }
        return sortedTree.toString();
    }
    
    
    private void traverse(Node node, StringBuilder nodeListAsString) {
        if (node != null) {
            traverse(node.left, nodeListAsString);
            nodeListAsString.append(node.toString()); 
            traverse(node.right, nodeListAsString);
        }
    }
    
    
    private void bubbleSortByMark(String[] nodeArray) {
        int n = nodeArray.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int mark1 = extractMark(nodeArray[j]);
                int mark2 = extractMark(nodeArray[j + 1]);
                if (mark1 != -1 && (mark2 == -1 || mark1 > mark2)) { 
                    String temp = nodeArray[j];
                    nodeArray[j] = nodeArray[j + 1];
                    nodeArray[j + 1] = temp;
                }
            }
        }
    }
    

    private int extractMark(String nodeString) {
        int colonIndex = nodeString.indexOf(':');
        int percentIndex = nodeString.indexOf('%');
        if (colonIndex != -1 && percentIndex != -1) {
            try {
                if (nodeString.substring(colonIndex + 1, percentIndex).equals("null")) {
                    return Integer.MIN_VALUE; 
                } else {
                    return Integer.parseInt(nodeString.substring(colonIndex + 1, percentIndex));
                }
            } catch (NumberFormatException e) {
                return -1; 
            }
        }
        return -1; 
    }
}