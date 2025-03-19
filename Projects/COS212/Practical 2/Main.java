import java.util.Arrays;

public class Main {
    public static String ANSI_RED="\u001b[31;1m";
    public static String ANSI_GREEN="\u001b[32;1m";
    public static String ANSI_RESET="\u001b[0m";
    public static int SUITES_RUN=0;
    public static int SUITES_PASSED=0;
    public static int TESTS_RUN=0;
    public static int TESTS_PASSED=0;

    public static void start_suite(String name){
        SUITES_RUN++;
        System.out.println("======================\n Starting: "+name+"\n======================");
    }

    public static void end_suite(){
        if (TESTS_RUN == TESTS_PASSED){
            SUITES_PASSED++;
            System.out.println(ANSI_GREEN + "All tests passed \n======================" + ANSI_RESET);
        }
        else{
            System.out.println(ANSI_RED+"Some Tests failed: "+(TESTS_RUN-TESTS_PASSED)+"\n======================" + ANSI_RESET);
        }

        TESTS_RUN=0;
        TESTS_PASSED=0;
    }

    public static void assertEquals(Integer[] actual , Integer[] expect){
        TESTS_RUN++;
        if(Arrays.equals(actual, expect)){
            TESTS_PASSED++;
            System.out.println(ANSI_GREEN + "Test "+TESTS_RUN+" passed \n======================" + ANSI_RESET);
        }else{
            System.out.println(ANSI_RED + "Test "+TESTS_RUN+"Failed: Expected\n"+ Arrays.toString(expect) +"\nbut got\n"+Arrays.toString(actual)+" \n======================" + ANSI_RESET);
        }
    }

        public static void assertEquals(Integer actual , Integer expect){
            TESTS_RUN++;
            if(actual == expect){
                TESTS_PASSED++;
                System.out.println(ANSI_GREEN + "Test "+TESTS_RUN+" passed \n======================" + ANSI_RESET);
            }else{
                System.out.println(ANSI_RED + "Test "+TESTS_RUN+" Failed: Expected\n"+ expect+"\nbut got\n"+actual+" \n======================" + ANSI_RESET);
            }
    }

    public static void assertEquals(int actual , int expect){
        TESTS_RUN++;
        if(actual == expect){
            TESTS_PASSED++;
            System.out.println(ANSI_GREEN + "Test "+TESTS_RUN+" passed \n======================" + ANSI_RESET);
        }else{
            System.out.println(ANSI_RED + "Test "+TESTS_RUN+"Failed: Expected\n"+ String.valueOf(expect) +"\n but got\n"+ String.valueOf(actual)+" \n======================" + ANSI_RESET);
        }
    }

    public static void assertEquals(String actual , String expect){
        TESTS_RUN++;
        if(actual.equals(expect)){
            TESTS_PASSED++;
            System.out.println(ANSI_GREEN + "Test "+TESTS_RUN+" passed \n======================" + ANSI_RESET);
        }else{
            System.out.println(ANSI_RED + "Test "+TESTS_RUN+"Failed: Expected\n"+ expect+"\nbut got\n"+actual+" \n======================" + ANSI_RESET);
        }
    }

    public static void end_all(){
        if (SUITES_RUN == SUITES_PASSED){
            System.out.println(ANSI_GREEN + "All " + (SUITES_RUN) +" suites passed ("+SUITES_PASSED+"/"+SUITES_RUN+") \n======================" + ANSI_RESET);
        }
        else{
            System.out.println(ANSI_RED+"Some suites failed: "+(SUITES_RUN-SUITES_PASSED)+" failed ("+SUITES_PASSED+"/"+SUITES_RUN+") \n======================"  + ANSI_RESET);
        }
    }

    public static void main(String[] args) {
        start_suite("BTS Creation and insertion");
        BST<Integer> newTree = new BST<>();
        newTree.insert(10);
        newTree.insert(5);
        newTree.insert(15);
        newTree.insert(3);
        newTree.insert(7);
        newTree.insert(12);
        newTree.insert(17);

        System.out.println("Original BST:");
        System.out.println(newTree.toString());

        BST<Integer> newTree2 = new BST<>();
        System.out.println(newTree2.toString());

        newTree2.insert(100);
        newTree2.insert(50);
        newTree2.insert(25);
        newTree2.insert(15);
        newTree2.insert(10);
        newTree2.insert(75);
        newTree2.insert(60);
        newTree2.insert(150);
        newTree2.insert(125);
        newTree2.insert(140);
        newTree2.insert(175);
        newTree2.insert(190);

        System.out.println(newTree2.toString());
        BST<Integer> SubTree = newTree2.extractBiggestSuperficiallyBalancedSubTree();
        System.out.println(SubTree.toString());
        newTree2.delete(100);
        
        System.out.println(newTree2.toString());
        newTree2.delete(50);
        System.out.println(newTree2.toString());
        System.out.println(newTree2.contains(150));
        System.out.println(newTree2.contains(1500));
        System.out.println(newTree2.contains(15));
        System.out.println(newTree2.printSearchPath(1500));
        System.out.println(newTree2.printSearchPath(190));
        System.out.println(newTree2.printSearchPath(10));
        System.out.println(newTree2.printSearchPath(140));
        System.out.println(newTree2.findMax());
        System.out.println(newTree2.findMin());
        System.out.println(newTree2.isSuperficiallyBalanced());
        System.out.println(newTree2.getNode(175));
        System.out.println(newTree2.getNode(5));
        System.out.println(newTree.isSuperficiallyBalanced());
        System.out.println(newTree2.getNumLeaves());
        System.out.println(newTree.getNumLeaves());
        System.out.println(newTree.getHeight());

        BST<Integer> newTree3 = new BST<>();
        System.out.println(newTree3.contains(150));
        System.out.println(newTree3.contains(1500));
        System.out.println(newTree3.printSearchPath(1500));
        System.out.println(newTree3.printSearchPath(190));
        System.out.println(newTree3.printSearchPath(10));
        System.out.println(newTree3.printSearchPath(140));
        System.out.println(newTree3.findMax());
        System.out.println(newTree3.findMin());
        System.out.println(newTree3.isSuperficiallyBalanced());
        System.out.println(newTree3.getNode(175));
        System.out.println(newTree3.getNode(5));
        System.out.println(newTree3.isSuperficiallyBalanced());
        System.out.println(newTree3.getNumLeaves());
        System.out.println(newTree3.getHeight());
        newTree3.delete(5);
        newTree2.delete(190);
        newTree2.delete(1000);
        newTree2.getNode(15).toString();

        
        BST<String> newTreestr = new BST<>();
        System.out.println(newTree2.toString());

        newTreestr.insert("a");
        newTreestr.insert("B");
        newTreestr.insert("C");
        newTreestr.insert("d");
        newTreestr.insert("e");
        newTreestr.insert("w");
        newTreestr.insert("j");
        newTreestr.insert("m");
        newTreestr.insert("m");
        newTreestr.insert("Q");
        newTreestr.insert("c");
        newTreestr.insert("A");


        
        System.out.println(newTreestr.toString());
        newTreestr.delete("m");
        System.out.println(newTreestr.toString());
        System.out.println(newTreestr.contains("A"));
        System.out.println(newTreestr.contains("hg"));
        System.out.println(newTreestr.contains("AL"));
        System.out.println(newTreestr.printSearchPath("G"));
        System.out.println(newTreestr.printSearchPath("j"));
        System.out.println(newTreestr.printSearchPath("m"));
        System.out.println(newTreestr.printSearchPath("a"));
        System.out.println(newTreestr.findMax());
        System.out.println(newTreestr.findMin());
        System.out.println(newTreestr.isSuperficiallyBalanced());
        System.out.println(newTreestr.getNode("b"));
        System.out.println(newTreestr.getNode("a").toString());
        System.out.println(newTreestr.isSuperficiallyBalanced());
        System.out.println(newTreestr.getNumLeaves());
        System.out.println(newTreestr.getNumLeaves());
        System.out.println(newTreestr.getHeight());

        BST<String> newTreestr2 = new BST<>();
        newTreestr2.delete("m");
        System.out.println(newTreestr2.toString());
        System.out.println(newTreestr2.contains("A"));
        System.out.println(newTreestr2.contains("hg"));
        System.out.println(newTreestr2.contains("AL"));
        System.out.println(newTreestr2.printSearchPath("G"));
        System.out.println(newTreestr2.printSearchPath("j"));
        System.out.println(newTreestr2.printSearchPath("m"));
        System.out.println(newTreestr2.printSearchPath("a"));
        System.out.println(newTreestr2.findMax());
        System.out.println(newTreestr2.findMin());
        System.out.println(newTreestr2.isSuperficiallyBalanced());
        System.out.println(newTreestr2.getNode("b"));
        System.out.println(newTreestr2.getNode(""));
        System.out.println(newTreestr2.isSuperficiallyBalanced());
        System.out.println(newTreestr2.getNumLeaves());
        System.out.println(newTreestr2.getNumLeaves());
        System.out.println(newTreestr2.getHeight());

        newTreestr2.delete("B");
        newTreestr2.delete("a");
        newTreestr2.delete("c");
        BST<String> newTreestr3 = newTreestr2.extractBiggestSuperficiallyBalancedSubTree();
        System.out.println(newTreestr3.toString());
        BST<String> newTreestr4 = new BST<>();
        newTreestr4.insert("a");
        newTreestr4.insert("b");
        BST<String> newTreestr5 = newTreestr2.extractBiggestSuperficiallyBalancedSubTree();
        System.out.println(newTreestr5.toString());

        
        BinaryNode<Integer> node1 = new BinaryNode<>(10);
        BinaryNode<Integer> node2 = new BinaryNode<>(18);
        BinaryNode<Integer> node3 = new BinaryNode<>(15,node2,null);
        node1.left = null ;
        node1.right = node2 ;
        node2.left = node1;
        node2.right = node3 ;
        node3.right = newTree.getNode(15);
        newTree.getNode(15).left = node3 ;
        System.out.println( newTree.getNode(15).left);

        BST<String> newTreestr6 = new BST<>();
        newTreestr6.insert("A");
        BST<String> newTreestr7 =newTreestr6.extractBiggestSuperficiallyBalancedSubTree();
        System.out.println( newTreestr7);
        newTreestr6.delete("A");

        BST<Integer> newTree10 = new BST<>();
        

        newTree10.insert(5);
        newTree10.insert(4);
        newTree10.insert(3);
        newTree10.insert(2);
        newTree10.insert(1);
        System.out.println(newTree10.toString());
        BST<Integer> newTree11 = newTree10.extractBiggestSuperficiallyBalancedSubTree();
        System.out.println(newTree11.toString());


        BST<Integer> newTree12 = new BST<>();
        

        newTree12.insert(1);
        newTree12.insert(2);
        newTree12.insert(3);
        newTree12.insert(4);
        newTree12.insert(5);
        System.out.println(newTree12.toString());
        BST<Integer> newTree13 = newTree12.extractBiggestSuperficiallyBalancedSubTree();
        System.out.println(newTree13.toString());

        


    



















                        
                        
    }




}
