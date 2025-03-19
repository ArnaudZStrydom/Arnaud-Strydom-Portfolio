public class RedBlackTree<T extends Comparable<T>> {

    /*
     * Sentinel is not the root. Go check the text book if this doesn't make sense
     */
    public RedBlackNode<T> SENTINEL;
    public RedBlackNode<T> NULL_NODE;

    public static final int RED = 1;
    public static final int BLACK = 0;

    public RedBlackTree() {
        NULL_NODE = new RedBlackNode<>(null); 
        NULL_NODE.colour = BLACK; 
        NULL_NODE.left = NULL_NODE; 
        NULL_NODE.right = NULL_NODE;
        
        SENTINEL = new RedBlackNode<>(null); 
        SENTINEL.colour = BLACK; 
        SENTINEL.left = NULL_NODE;
        SENTINEL.right = NULL_NODE;

    
        
    }

    public RedBlackNode<T> getRoot() {
        if (SENTINEL.right == NULL_NODE) {
            return NULL_NODE;
        } else {
            return SENTINEL.right;
        }
    }

    public void bottomUpInsert(T data) {
        if (contains(data)) {
            return; 
        }

        RedBlackNode<T> newNode = new RedBlackNode<>(data);

        
        RedBlackNode<T> parent = NULL_NODE;
        RedBlackNode<T> current = SENTINEL.right; 
        while (current != NULL_NODE) {
            parent = current;
            int cmp = data.compareTo(current.data);
            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        newNode.left = NULL_NODE;
        newNode.right = NULL_NODE;
        if (parent == NULL_NODE) {
            SENTINEL.right = newNode;
            newNode.parent = NULL_NODE ;
        } else {
            int cmp = data.compareTo(parent.data);
            if (cmp < 0) {
                parent.left = newNode;
                newNode.parent = parent;
                newNode.colour = RED ;
            } else {
                parent.right = newNode;
                newNode.parent = parent;
                newNode.colour = RED ;
            }
        }
    
        
        fixtree(newNode);
    }

    private void fixtree(RedBlackNode<T> node) {

        if (node == SENTINEL.right) {
            node.colour = BLACK;
            return;
        }

        RedBlackNode<T> parent = node.parent;
        RedBlackNode<T> grandparent = parent.parent;
        RedBlackNode<T> uncle = getUncle(node);

        while (parent.colour == RED) {
            if (uncle != NULL_NODE && uncle.colour == RED) {
                parent.colour = BLACK;
                uncle.colour = BLACK;
                grandparent.colour = RED;
                node = grandparent;
                parent = node.parent;
                grandparent = parent.parent;
                uncle = getUncle(node);
            } else {
                
                if (node == parent.right && parent == grandparent.left) {
                    rotateLeft(parent);
                    node = node.left;
                } else if (node == parent.left && parent == grandparent.right) {
                    rotateRight(parent);
                    node = node.right;
                }
                
                parent = node.parent;
                grandparent = parent.parent;

                if (node == parent.left && parent == grandparent.left) {
                    parent.colour = BLACK;
                    grandparent.colour = RED;
                    rotateRight(grandparent);
                } 
                else {
                    parent.colour = BLACK;
                    grandparent.colour = RED;
                    rotateLeft(grandparent);
                }
            }
        }

        SENTINEL.right.colour = BLACK;
    }
    
    private RedBlackNode<T> getUncle(RedBlackNode<T> node) {
        if (node.parent == NULL_NODE || node.parent.parent == NULL_NODE) {
            return NULL_NODE; 
        }
    
        if (node.parent == node.parent.parent.left) {
            return node.parent.parent.right; 
        } else {
            return node.parent.parent.left; 
        }
    }

    private RedBlackNode<T> getSibling(RedBlackNode<T> node) {
        if (node.parent == NULL_NODE ) {
            return NULL_NODE; 
        }
    
        if (node == node.parent.left) {
            return node.parent.right; 
        } else if(node == node.parent.right) {
            return node.parent.left; 
        }
        else{
            return NULL_NODE ;
        }
    }
    
    private void rotateLeft(RedBlackNode<T> node) {
        RedBlackNode<T> rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != NULL_NODE) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == NULL_NODE) {
            SENTINEL.right = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }
        rightChild.left = node;
        node.parent = rightChild;
    }
    
    private void rotateRight(RedBlackNode<T> node) {
        RedBlackNode<T> leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != NULL_NODE) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == NULL_NODE) {
            SENTINEL.right = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
    }

    private boolean contains(T data) {
        
        RedBlackNode<T> current = SENTINEL.right;
        
        
        while (current != NULL_NODE) {
            int comparenode = data.compareTo(current.data);
            if (comparenode == 0) {
                return true;
            } else if (comparenode < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        return false;
    }

    public boolean isValidRedBlackTree() {
        return isValidRedBlackTreeRecursive(getRoot(),0, -1);
    }

    private boolean isValidRedBlackTreeRecursive(RedBlackNode<T> node, int blackHeight, int expectedBlackHeight) {
        if(SENTINEL.right == NULL_NODE){
            return true ;
        }
        if (node == NULL_NODE) {
            if (expectedBlackHeight == -1) {
                
                expectedBlackHeight = blackHeight;
            } else if (blackHeight != expectedBlackHeight) {
                
                return false;
            }
            return true;
        }

        
        if (node.colour != RED && node.colour != BLACK) {
            return false;
        }

        
        if (node == SENTINEL.right && node.colour != BLACK) {
            return false;
        }

        
        if (node.parent != null && node.parent.colour == RED && node.colour == RED) {
            return false;
        }

        
        if (node.colour == BLACK) {
            blackHeight++;
        }

        
        return isValidRedBlackTreeRecursive(node.left, blackHeight, expectedBlackHeight) &&
            isValidRedBlackTreeRecursive(node.right, blackHeight, expectedBlackHeight);
    }

   
    public void topDownDelete(T data) {
        if (SENTINEL.right == NULL_NODE) {
            return;
        }
        if (!contains(data)) {
            return;
        }
    
        RedBlackNode<T> root = SENTINEL.right;
        RedBlackNode<T> currentX = root;
        RedBlackNode<T> siblingT = NULL_NODE;
        if (root.left.colour == BLACK && root.right.colour == BLACK && currentX.data != data) {
            root.colour = RED;
            if (data.compareTo(currentX.data) < 0) {
                currentX = currentX.left;
                siblingT = getSibling(currentX);
            } else if (data.compareTo(currentX.data) > 0) {
                currentX = currentX.right;
                siblingT = getSibling(currentX);
            }
            while (data.compareTo(currentX.data) != 0) {
    
                if (currentX.left.colour == BLACK && currentX.right.colour == BLACK && siblingT.left.colour == BLACK && siblingT.right.colour == BLACK && data.compareTo(currentX.data) != 0) {
                    if (siblingT.left.colour == BLACK && siblingT.right.colour == BLACK) {
                        if (currentX.colour == RED) {
                            currentX.colour = BLACK;
                        } else {
                            currentX.colour = RED;
                        }
    
                        if (siblingT.colour == RED) {
                            siblingT.colour = BLACK;
                        } else {
                            siblingT.colour = RED;
                        }
    
                        if (currentX.parent.colour == RED) {
                            currentX.parent.colour = BLACK;
                        } else {
                            currentX.parent.colour = RED;
                        }
    
                        if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                            currentX = currentX.left;
                            siblingT = getSibling(currentX);
                        } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                            currentX = currentX.right;
                            siblingT = getSibling(currentX);
                        }
                    } else if (currentX.left.colour == BLACK && currentX.right.colour == BLACK && siblingT.left.colour == RED) {
                        if (siblingT.parent.right == siblingT) {
                            rotateRight(siblingT);
                            rotateLeft(siblingT.parent);
                            if (currentX.colour == RED) {
                                currentX.colour = BLACK;
                            } else {
                                currentX.colour = RED;
                            }
                            if (currentX.parent.colour == RED) {
                                currentX.parent.colour = BLACK;
                            } else {
                                currentX.parent.colour = RED;
                            }
    
                            if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.left;
                                siblingT = getSibling(currentX);
                            } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.right;
                                siblingT = getSibling(currentX);
                            }
    
    
                        } else {
                            rotateRight(siblingT);
                            rotateRight(siblingT.parent);
                            if (currentX.colour == RED) {
                                currentX.colour = BLACK;
                            } else {
                                currentX.colour = RED;
                            }
                            if (currentX.parent.colour == RED) {
                                currentX.parent.colour = BLACK;
                            } else {
                                currentX.parent.colour = RED;
                            }
    
                            if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.left;
                                siblingT = getSibling(currentX);
                            } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.right;
                                siblingT = getSibling(currentX);
                            }
    
    
                        }
                    } else if ((currentX.left.colour == BLACK && currentX.right.colour == BLACK && siblingT.right.colour == RED) || (currentX.left.colour == BLACK && currentX.right.colour == BLACK && siblingT.right.colour == RED && siblingT.left.colour == RED && data.compareTo(currentX.data) != 0)) {
                        if (siblingT.parent.right == siblingT) {
                            rotateLeft(siblingT.parent);
                            if (currentX.colour == RED) {
                                currentX.colour = BLACK;
                            } else {
                                currentX.colour = RED;
                            }
                            if (currentX.parent.colour == RED) {
                                currentX.parent.colour = BLACK;
                            } else {
                                currentX.parent.colour = RED;
                            }
    
                            if (siblingT.colour == RED) {
                                siblingT.colour = BLACK;
                            } else {
                                siblingT.colour = RED;
                            }
    
                            if (siblingT.right.colour == RED) {
                                siblingT.right.colour = BLACK;
                            } else {
                                siblingT.right.colour = RED;
                            }
    
                            if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.left;
                                siblingT = getSibling(currentX);
                            } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.right;
                                siblingT = getSibling(currentX);
                            }
    
    
                        } else {
                            rotateRight(siblingT.parent);
                            if (currentX.colour == RED) {
                                currentX.colour = BLACK;
                            } else {
                                currentX.colour = BLACK;
                            }
                            if (currentX.parent.colour == RED) {
                                currentX.parent.colour = BLACK;
                            } else {
                                currentX.parent.colour = BLACK;
                            }
    
                            if (siblingT.colour == RED) {
                                siblingT.colour = BLACK;
                            } else {
                                siblingT.colour = RED;
                            }
    
                            if (siblingT.right.colour == RED) {
                                siblingT.right.colour = BLACK;
                            } else {
                                siblingT.right.colour = RED;
                            }
    
                            if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.left;
                                siblingT = getSibling(currentX);
                            } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.right;
                                siblingT = getSibling(currentX);
                            }
    
    
                        }
                    }
                } else if(data.compareTo(currentX.data) != 0) {
                    if (data.compareTo(currentX.data) < 0) {
                        currentX = currentX.left;
                        siblingT = getSibling(currentX);
                    } else if (data.compareTo(currentX.data) > 0) {
                        currentX = currentX.right;
                        siblingT = getSibling(currentX);
                    }
                    while (currentX.colour != BLACK && data.compareTo(currentX.data) != 0) {
                        if (data.compareTo(currentX.data) < 0) {
                            currentX = currentX.left;
                            siblingT = getSibling(currentX);
                        } else if (data.compareTo(currentX.data) > 0) {
                            currentX = currentX.right;
                            siblingT = getSibling(currentX);
                        }
                    }
    
                    if (siblingT != NULL_NODE && siblingT == siblingT.parent.right && data.compareTo(currentX.data) != 0 ) {
                        rotateLeft(siblingT.parent);
                        if (currentX.parent.colour == RED) {
                            currentX.parent.colour = BLACK;
                        } else {
                            currentX.parent.colour = BLACK;
                        }
    
                        if (siblingT.colour == RED) {
                            siblingT.colour = BLACK;
                        } else {
                            siblingT.colour = RED;
                        }
    
    
                    } else if(data.compareTo(currentX.data) != 0){
                        rotateRight(siblingT.parent);
                        if (currentX.parent.colour == RED) {
                            currentX.parent.colour = BLACK;
                        } else {
                            currentX.parent.colour = BLACK;
                        }
    
                        if (siblingT.colour == RED) {
                            siblingT.colour = BLACK;
                        } else {
                            siblingT.colour = RED;
                        }
    
                    }
                }
            }
    
    
        } else if (data.compareTo(currentX.data) != 0) {
            currentX = root;
            if (data.compareTo(currentX.data) < 0) {
                currentX = currentX.left;
                siblingT = getSibling(currentX);
            } else if (data.compareTo(currentX.data) > 0) {
                currentX = currentX.right;
                siblingT = getSibling(currentX);
            }
            while (currentX.colour != BLACK && data.compareTo(currentX.data) != 0) {
                if (data.compareTo(currentX.data) < 0) {
                    currentX = currentX.left;
                    siblingT = getSibling(currentX);
                } else if (data.compareTo(currentX.data) > 0) {
                    currentX = currentX.right;
                    siblingT = getSibling(currentX);
                }
            }
    
            if (siblingT != NULL_NODE && siblingT == siblingT.parent.right && data.compareTo(currentX.data) != 0) {
                rotateLeft(siblingT.parent);
                if (currentX.parent.colour == RED) {
                    currentX.parent.colour = BLACK;
                } else {
                    currentX.parent.colour = BLACK;
                }
    
                if (siblingT.colour == RED) {
                    siblingT.colour = BLACK;
                } else {
                    siblingT.colour = RED;
                }
    
    
            } else if(data.compareTo(currentX.data) != 0){
                rotateRight(siblingT.parent);
                if (currentX.parent.colour == RED) {
                    currentX.parent.colour = BLACK;
                } else {
                    currentX.parent.colour = BLACK;
                }
    
                if (siblingT.colour == RED) {
                    siblingT.colour = BLACK;
                } else {
                    siblingT.colour = RED;
                }
    
            }
    
            while (data.compareTo(currentX.data) != 0) {
    
                if (currentX.left.colour == BLACK && currentX.right.colour == BLACK && siblingT.left.colour == BLACK && siblingT.right.colour == BLACK && data.compareTo(currentX.data) != 0) {
                    if (siblingT.left.colour == BLACK && siblingT.right.colour == BLACK) {
                        if (currentX.colour == RED) {
                            currentX.colour = BLACK;
                        } else {
                            currentX.colour = RED;
                        }
    
                        if (siblingT.colour == RED) {
                            siblingT.colour = BLACK;
                        } else {
                            siblingT.colour = RED;
                        }
    
                        if (currentX.parent.colour == RED) {
                            currentX.parent.colour = BLACK;
                        } else {
                            currentX.parent.colour = RED;
                        }
    
                        if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                            currentX = currentX.left;
                            siblingT = getSibling(currentX);
                        } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                            currentX = currentX.right;
                            siblingT = getSibling(currentX);
                        }
    
    
                    } else if (currentX.left.colour == BLACK && currentX.right.colour == BLACK && siblingT.left.colour == RED && data.compareTo(currentX.data) != 0) {
                        if (siblingT.parent.right == siblingT) {
                            rotateRight(siblingT);
                            rotateLeft(siblingT.parent);
                            if (currentX.colour == RED) {
                                currentX.colour = BLACK;
                            } else {
                                currentX.colour = RED;
                            }
                            if (currentX.parent.colour == RED) {
                                currentX.parent.colour = BLACK;
                            } else {
                                currentX.parent.colour = RED;
                            }
    
                            if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.left;
                                siblingT = getSibling(currentX);
                            } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.right;
                                siblingT = getSibling(currentX);
                            }
    
    
                        } else {
                            rotateRight(siblingT);
                            rotateRight(siblingT.parent);
                            if (currentX.colour == RED) {
                                currentX.colour = BLACK;
                            } else {
                                currentX.colour = RED;
                            }
                            if (currentX.parent.colour == RED) {
                                currentX.parent.colour = BLACK;
                            } else {
                                currentX.parent.colour = RED;
                            }
    
                            if (data.compareTo(currentX.data) < 0) {
                                currentX = currentX.left;
                                siblingT = getSibling(currentX);
                            } else if (data.compareTo(currentX.data) > 0) {
                                currentX = currentX.right;
                                siblingT = getSibling(currentX);
                            }
    
    
                        }
                    } else if ((currentX.left.colour == BLACK && currentX.right.colour == BLACK && siblingT.right.colour == RED) || (currentX.left.colour == BLACK && currentX.right.colour == BLACK && siblingT.right.colour == RED && siblingT.left.colour == RED && data.compareTo(currentX.data) != 0)) {
                        if (siblingT.parent.right == siblingT) {
                            rotateLeft(siblingT.parent);
                            if (currentX.colour == RED) {
                                currentX.colour = BLACK;
                            } else {
                                currentX.colour = RED;
                            }
                            if (currentX.parent.colour == RED) {
                                currentX.parent.colour = BLACK;
                            } else {
                                currentX.parent.colour = RED;
                            }
    
                            if (siblingT.colour == RED) {
                                siblingT.colour = BLACK;
                            } else {
                                siblingT.colour = RED;
                            }
    
                            if (siblingT.right.colour == RED) {
                                siblingT.right.colour = BLACK;
                            } else {
                                siblingT.right.colour = RED;
                            }
    
                            if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.left;
                                siblingT = getSibling(currentX);
                            } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.right;
                                siblingT = getSibling(currentX);
                            }
    
    
                        } else {
                            rotateRight(siblingT.parent);
                            if (currentX.colour == RED) {
                                currentX.colour = BLACK;
                            } else {
                                currentX.colour = BLACK;
                            }
                            if (currentX.parent.colour == RED) {
                                currentX.parent.colour = BLACK;
                            } else {
                                currentX.parent.colour = BLACK;
                            }
    
                            if (siblingT.colour == RED) {
                                siblingT.colour = BLACK;
                            } else {
                                siblingT.colour = RED;
                            }
    
                            if (siblingT.right.colour == RED) {
                                siblingT.right.colour = BLACK;
                            } else {
                                siblingT.right.colour = RED;
                            }
    
                            if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.left;
                                siblingT = getSibling(currentX);
                            } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                                currentX = currentX.right;
                                siblingT = getSibling(currentX);
                            }
    
    
                        }
                    }
                } else if( data.compareTo(currentX.data) != 0){
                    if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                        currentX = currentX.left;
                        siblingT = getSibling(currentX);
                    } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                        currentX = currentX.right;
                        siblingT = getSibling(currentX);
                    }
                    while (currentX.colour != BLACK && data.compareTo(currentX.data) != 0) {
                        if (data.compareTo(currentX.data) < 0 && data.compareTo(currentX.data) != 0) {
                            currentX = currentX.left;
                            siblingT = getSibling(currentX);
                        } else if (data.compareTo(currentX.data) > 0 && data.compareTo(currentX.data) != 0) {
                            currentX = currentX.right;
                            siblingT = getSibling(currentX);
                        }
                    }
    
                    if (siblingT != NULL_NODE && siblingT == siblingT.parent.right && data.compareTo(currentX.data) != 0) {
                        rotateLeft(siblingT.parent);
                        if (currentX.parent.colour == RED) {
                            currentX.parent.colour = BLACK;
                        } else {
                            currentX.parent.colour = BLACK;
                        }
    
                        if (siblingT.colour == RED) {
                            siblingT.colour = BLACK;
                        } else {
                            siblingT.colour = RED;
                        }
    
    
                    } else if( data.compareTo(currentX.data) != 0) {
                        rotateRight(siblingT.parent);
                        if (currentX.parent.colour == RED) {
                            currentX.parent.colour = BLACK;
                        } else {
                            currentX.parent.colour = BLACK;
                        }
    
                        if (siblingT.colour == RED) {
                            siblingT.colour = BLACK;
                        } else {
                            siblingT.colour = RED;
                        }
    
                    }
                }
            }
        }
        deleteNode(currentX);

        SENTINEL.right.colour = BLACK ;

    }
    
    private void deleteNode(RedBlackNode<T> nodeToDelete) {
        RedBlackNode<T> x, y;
        if (nodeToDelete.left == NULL_NODE || nodeToDelete.right == NULL_NODE) {
            y = nodeToDelete;
        } else {
            y = successor(nodeToDelete);
        }
        
        if (y.left != NULL_NODE) {
            x = y.left;
        } else {
            x = y.right;
        }
        
        x.parent = y.parent;
        if (y.parent == NULL_NODE) {
            SENTINEL.right = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }
        
        if (y != nodeToDelete) {
            nodeToDelete.data = y.data; 
        }
        
       
    }
    
    
    private RedBlackNode<T> successor(RedBlackNode<T> x) {
        if (x.right != NULL_NODE) {
            return minimum(x.right);
        }
        RedBlackNode<T> y = x.parent;
        while (y != NULL_NODE && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }
    
    private RedBlackNode<T> minimum(RedBlackNode<T> node) {
        while (node.left != NULL_NODE) {
            node = node.left;
        }
        return node;
    }






    /* -------------------------------------------------------------------------- */
    /* Private methods, which shouldn't be called from outside the class */
    /* -------------------------------------------------------------------------- */

    /* -------------------------------------------------------------------------- */
    /* Please don't change this toString, I worked really hard to make it pretty. */
    /* Also, it matches the website. -------------------------------------------- */
    /* Also, also, we might test against it ;) ---------------------------------- */
    /* -------------------------------------------------------------------------- */
    private StringBuilder toString(RedBlackNode<T> node, StringBuilder prefix, boolean isTail, StringBuilder sb) {
        if (node.right != NULL_NODE) {
            toString(node.right, new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
        }
        sb.append(prefix).append(isTail ? "└── " : "┌── ").append(node.toString()).append("\n");
        if (node.left != NULL_NODE) {
            toString(node.left, new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
        }
        return sb;
    }

    @Override
    public String toString() {
        return SENTINEL.right == NULL_NODE ? "Empty tree"
                : toString(SENTINEL.right, new StringBuilder(), true, new StringBuilder()).toString();
    }

    public String toVis() {
        return toVisHelper(getRoot());
    }

    private String toVisHelper(RedBlackNode<T> node) {
        if (node == NULL_NODE) {
            return "{}";
        }
        String leftStr = toVisHelper(node.left);
        String rightStr = toVisHelper(node.right);
        return "{" + node.toString() + leftStr + rightStr + "}";
    }

}
