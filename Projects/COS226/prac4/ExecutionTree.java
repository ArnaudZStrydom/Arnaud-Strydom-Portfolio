import java.util.*;

class Node {
    Integer value;
    String path;
    List<Node> children;
    List<Label> labels;
    HashSet<Integer> childrenValues;
    boolean has0Valent;
    boolean has1Valent;
    boolean hasCritical;

    public Node(Integer value, String path) {
        this.value = value;
        this.path = path;
        this.children = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.childrenValues = new HashSet<>();
        this.has0Valent = false ;
        this.has1Valent = false ;
        this.hasCritical = false;
    }

    public Node(String path) {
        this.value = null;
        this.path = path;
        this.children = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.childrenValues = new HashSet<>();
                this.has0Valent = false ;
        this.has1Valent = false ;
        this.hasCritical = false;
    }

    public void addChild(Node child) {
        this.children.add(child);
        if(child.value != null && child.value == 1){
            has1Valent =true ;
        }

        if(child.value != null && child.value == 0 ){
            has0Valent = true;
        }
    }

    public void addLabel(Label l) {
        this.labels.add(l);
    }



    // Maybe adding a method here would wake it easier ¯\_(ツ)_/¯

    public void printTree(String prefix, boolean isTail) {

        System.out.println(
                prefix + (isTail ? "└── " : "├── ") + path + " Labels:" + labels + " value: (" + value + ")");
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).printTree(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1).printTree(prefix + (isTail ? "    " : "│   "), true);
        }
    }

}

public class ExecutionTree {
    public Random r;
    public Node root;
    public boolean has0;
    public boolean has1;


    public void assignLabels() {
        has0 = false ;
        has1 = false;
        if (root != null) {
            assignLabelsRecursive(root);
        }
        for(Node child : root.children){
            if(root.labels.contains(Label.BIVALENT) && child.labels.contains(Label.UNIVALENT)){
                root.labels.add(Label.CRITICAL);
                break;
            }
        }
    }
    
    private void assignLabelsRecursive(Node node) {


        if (node.children == null || node.children.isEmpty()) {
            // No children means it's a final state
            node.labels.add(Label.FINAL);
            if(node.value == 1){
                node.has1Valent =true ;
            }

            if(node.value == 0 ){
                node.has0Valent = true;
            }
            return;
        }
    
        
        // Process children nodes
        for (Node child : node.children) {
            assignLabelsRecursive(child);
    
            // Check if the child nodes are 0-valent or 1-valent
            if (child.has0Valent) {
                node.has0Valent = true;
                has0 = true ;
            }
            if (child.has1Valent) {
                node.has1Valent = true;
                has1 =true ;
            }
            
            
            // If any child is not UNIVALENT or has BIVALENT children, this node might not be critical
            if (!child.labels.contains(Label.UNIVALENT)) {
                node.hasCritical = false;
            }else if(node.labels.contains(Label.BIVALENT) && child.labels.contains(Label.UNIVALENT)){
                node.hasCritical = true ;
            }
        }

        if (node == root && node.labels.isEmpty()) {
            node.labels.add(Label.INITIAL);
            
        }
    
        // Determine if the current node is bivalent
        if (node.has0Valent && node.has1Valent || (node.labels.contains(Label.INITIAL) && has0 && has1)) {
            node.labels.add(Label.BIVALENT);
            return;
        } else if(!node.labels.contains(Label.BIVALENT) ) {
            node.labels.add(Label.UNIVALENT);
            return;
        }
    
        // Determine if the node is critical
        if (node.hasCritical) {
            node.labels.add(Label.CRITICAL);
        }

    
        // If the node is the root and no labels have been assigned, mark it as INITIAL
        
    }
    
    

    // I wouldn't change this if I were you
    public ExecutionTree(List<Character> threads, int n, int seed) {
        this.r = new Random(seed);
        int[] counts = new int[threads.size()]; // Array to keep track of counts

        this.root = new Node("");

        // Start the generation with each character
        for (int i = 0; i < threads.size(); i++) {
            counts[i]++;
            root.addChild(generateTree(threads.get(i) + "", counts, n, threads));
            counts[i]--; // Backtrack
        }

        root.addLabel(Label.INITIAL);
    }

    public Node generateTree(String path, int[] counts, int n, List<Character> chars) {
        boolean isComplete = true;
        for (int count : counts) {
            if (count < n) {
                isComplete = false;
                break;
            }
        }
        if (isComplete) {
            return new Node(r.nextInt(counts.length), path);
        }

        Node node = new Node(path);

        for (int i = 0; i < chars.size(); i++) {
            if (counts[i] < n) {
                counts[i]++;
                node.addChild(generateTree(path + chars.get(i), counts, n, chars));
                counts[i]--; // Backtrack
            }
        }
        return node;
    }

}
