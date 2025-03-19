import java.util.Random;
public class Main {
    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.bottomUpInsert(525);
        tree.bottomUpInsert(580);
        tree.bottomUpInsert(439);
        tree.bottomUpInsert(218);
        tree.bottomUpInsert(612);
        tree.bottomUpInsert(850);
        tree.bottomUpInsert(387);
        tree.bottomUpInsert(877);
        tree.bottomUpInsert(18);
        tree.bottomUpInsert(708);
        tree.bottomUpInsert(684);
        tree.bottomUpInsert(816);
        tree.bottomUpInsert(680);
        tree.bottomUpInsert(899);
        tree.bottomUpInsert(196);
        tree.bottomUpInsert(650);
        tree.bottomUpInsert(525);
        System.out.println(tree.toString());
        tree.topDownDelete(525);
        System.out.println(tree.toString());
        tree.topDownDelete(580);
        System.out.println(tree.toString());
        tree.topDownDelete(439);
        System.out.println(tree.toString());
        tree.topDownDelete(218);
        System.out.println(tree.toString());
        tree.topDownDelete(612);
        System.out.println(tree.toString());
        tree.topDownDelete(850);
        System.out.println(tree.toString());
        tree.topDownDelete(387);
        System.out.println(tree.toString());
        tree.topDownDelete(877);
        System.out.println(tree.toString());
        tree.topDownDelete(18);
        System.out.println(tree.toString());
        tree.topDownDelete(708);
        System.out.println(tree.toString());
        tree.topDownDelete(684);
        System.out.println(tree.toString());
        tree.topDownDelete(816);
        System.out.println(tree.toString());
        tree.topDownDelete(680);
        System.out.println(tree.toString());
        tree.topDownDelete(899);
        System.out.println(tree.toString());
        tree.topDownDelete(196);
        System.out.println(tree.toString());
        tree.topDownDelete(650);
        System.out.println(tree.toString());

        System.out.println(tree.isValidRedBlackTree());

                // Test 1: Insertion, Deletion, and Validation
                System.out.println("Test 1:");
                RedBlackTree<Integer> tree1 = new RedBlackTree<>();
                System.out.println("Inserting elements into Tree 1...");
                tree1.bottomUpInsert(10);
                tree1.bottomUpInsert(20);
                tree1.bottomUpInsert(30);
                tree1.bottomUpInsert(15);
                tree1.bottomUpInsert(25);
                System.out.println("Tree 1 after insertion:");
                System.out.println(tree1.toString());
                System.out.println("Deleting elements from Tree 1...");
                tree1.topDownDelete(20);
                System.out.println("Tree 1 after deletion:");
                System.out.println(tree1.toString());
                System.out.println("Is tree 1 a valid Red-Black Tree? " + tree1.isValidRedBlackTree());
                System.out.println();
        
                // Test 2: Insertion, Deletion, and Validation
                System.out.println("Test 2:");
                RedBlackTree<Integer> tree2 = new RedBlackTree<>();
                System.out.println("Inserting elements into Tree 2...");
                tree2.bottomUpInsert(5);
                tree2.bottomUpInsert(10);
                tree2.bottomUpInsert(15);
                tree2.bottomUpInsert(12);
                tree2.bottomUpInsert(20);
                System.out.println("Tree 2 after insertion:");
                System.out.println(tree2.toString());
                System.out.println("Deleting elements from Tree 2...");
                tree2.topDownDelete(5);
                tree2.topDownDelete(15);
                System.out.println("Tree 2 after deletion:");
                System.out.println(tree2.toString());
                System.out.println("Is tree 2 a valid Red-Black Tree? " + tree2.isValidRedBlackTree());
                System.out.println();
        
                
                System.out.println("Test 3:");
                RedBlackTree<Integer> tree3 = new RedBlackTree<>();
                System.out.println("Inserting elements into Tree 3...");
                tree3.bottomUpInsert(50);
                tree3.bottomUpInsert(30);
                tree3.bottomUpInsert(70);
                tree3.bottomUpInsert(20);
                tree3.bottomUpInsert(40);
                tree3.bottomUpInsert(60);
                tree3.bottomUpInsert(80);
                tree3.bottomUpInsert(35);
                System.out.println("Tree 3 after insertion:");
                System.out.println(tree3.toString());
                System.out.println("Deleting elements from Tree 3...");
                tree3.topDownDelete(70);
                tree3.topDownDelete(30);
                System.out.println("Tree 3 after deletion:");
                System.out.println(tree3.toString());
                System.out.println("Is tree 3 a valid Red-Black Tree? " + tree3.isValidRedBlackTree());
                System.out.println();

                RedBlackTree<Integer> tree5 = new RedBlackTree<>();
                tree5.bottomUpInsert(552);
                tree5.bottomUpInsert(189);
                tree5.bottomUpInsert(82);
                tree5.bottomUpInsert(51);
                tree5.bottomUpInsert(32);
                tree5.bottomUpInsert(23);
                tree5.bottomUpInsert(20);
                tree5.bottomUpInsert(41);
                tree5.bottomUpInsert(33);
                tree5.bottomUpInsert(48);
                tree5.bottomUpInsert(67);
                tree5.bottomUpInsert(58);
                tree5.bottomUpInsert(56);
                tree5.bottomUpInsert(61);
                tree5.bottomUpInsert(73);
                tree5.bottomUpInsert(68);
                tree5.bottomUpInsert(69);
                tree5.bottomUpInsert(78);
                tree5.bottomUpInsert(117);
                tree5.bottomUpInsert(97);
                tree5.bottomUpInsert(85);
                tree5.bottomUpInsert(83);
                tree5.bottomUpInsert(89);
                tree5.bottomUpInsert(88);
                tree5.bottomUpInsert(102);
                tree5.bottomUpInsert(159);
                tree5.bottomUpInsert(137);
                tree5.bottomUpInsert(120);
                tree5.bottomUpInsert(149);
                tree5.bottomUpInsert(141);
                tree5.bottomUpInsert(164);
                tree5.bottomUpInsert(161);
                tree5.bottomUpInsert(175);
                tree5.bottomUpInsert(363);
                tree5.bottomUpInsert(289);
                tree5.bottomUpInsert(242);
                tree5.bottomUpInsert(206);
                tree5.bottomUpInsert(198);
                tree5.bottomUpInsert(231);
                tree5.bottomUpInsert(218);
                tree5.bottomUpInsert(248);
                tree5.bottomUpInsert(246);
                tree5.bottomUpInsert(325);
                tree5.bottomUpInsert(294);
                tree5.bottomUpInsert(290);
                tree5.bottomUpInsert(296);
                tree5.bottomUpInsert(350);
                tree5.bottomUpInsert(356);
                tree5.bottomUpInsert(479);
                tree5.bottomUpInsert(409);
                tree5.bottomUpInsert(389);
                tree5.bottomUpInsert(373);
                tree5.bottomUpInsert(370);
                tree5.bottomUpInsert(378);
                tree5.bottomUpInsert(398);
                tree5.bottomUpInsert(396);
                tree5.bottomUpInsert(405);
                tree5.bottomUpInsert(433);
                tree5.bottomUpInsert(431);
                tree5.bottomUpInsert(425);
                tree5.bottomUpInsert(463);
                tree5.bottomUpInsert(441);
                tree5.bottomUpInsert(473);
                tree5.bottomUpInsert(464);
                tree5.bottomUpInsert(543);
                tree5.bottomUpInsert(502);
                tree5.bottomUpInsert(497);
                tree5.bottomUpInsert(492);
                tree5.bottomUpInsert(514);
                tree5.bottomUpInsert(513);
                tree5.bottomUpInsert(538);
                tree5.bottomUpInsert(546);
                tree5.bottomUpInsert(545);
                tree5.bottomUpInsert(548);
                tree5.bottomUpInsert(762);
                tree5.bottomUpInsert(653);
                tree5.bottomUpInsert(613);
                tree5.bottomUpInsert(601);
                tree5.bottomUpInsert(577);
                tree5.bottomUpInsert(564);
                tree5.bottomUpInsert(586);
                tree5.bottomUpInsert(611);
                tree5.bottomUpInsert(606);
                tree5.bottomUpInsert(645);
                tree5.bottomUpInsert(633);
                tree5.bottomUpInsert(719);
                tree5.bottomUpInsert(683);
                tree5.bottomUpInsert(672);
                tree5.bottomUpInsert(657);
                tree5.bottomUpInsert(682);
                tree5.bottomUpInsert(707);
                tree5.bottomUpInsert(692);
                tree5.bottomUpInsert(735);
                tree5.bottomUpInsert(732);
                tree5.bottomUpInsert(755);
                tree5.bottomUpInsert(738);
                tree5.bottomUpInsert(921);
                tree5.bottomUpInsert(848);
                tree5.bottomUpInsert(797);
                tree5.bottomUpInsert(775);
                tree5.bottomUpInsert(768);
                tree5.bottomUpInsert(778);
                tree5.bottomUpInsert(781);
                tree5.bottomUpInsert(816);
                tree5.bottomUpInsert(814);
                tree5.bottomUpInsert(820);
                tree5.bottomUpInsert(829);
                tree5.bottomUpInsert(902);
                tree5.bottomUpInsert(880);
                tree5.bottomUpInsert(870);
                tree5.bottomUpInsert(869);
                tree5.bottomUpInsert(881);
                tree5.bottomUpInsert(916);
                tree5.bottomUpInsert(909);
                tree5.bottomUpInsert(905);
                tree5.bottomUpInsert(918);
                tree5.bottomUpInsert(961);
                tree5.bottomUpInsert(939);
                tree5.bottomUpInsert(935);
                tree5.bottomUpInsert(926);
                tree5.bottomUpInsert(938);
                tree5.bottomUpInsert(948);
                tree5.bottomUpInsert(944);
                tree5.bottomUpInsert(950);
                tree5.bottomUpInsert(958);
                tree5.bottomUpInsert(991);
                tree5.bottomUpInsert(982);
                tree5.bottomUpInsert(972);
                tree5.bottomUpInsert(976);
                tree5.bottomUpInsert(983);
                tree5.bottomUpInsert(997);


                tree5.topDownDelete(218);
                tree5.topDownDelete(350);

        
                System.out.println("Initial Tree:");
                System.out.println(tree5.toString());
        
                System.out.println("Tree Validation:");
                System.out.println("Is the tree a valid Red-Black Tree? " + tree5.isValidRedBlackTree());
        
                

                

                
                RedBlackTree<String> treeS = new RedBlackTree<>();
        
                // Insert strings into the tree
                treeS.bottomUpInsert("apple");
                treeS.bottomUpInsert("banana");
                treeS.bottomUpInsert("cherry");
                treeS.bottomUpInsert("date");
                treeS.bottomUpInsert("elderberry");
                treeS.bottomUpInsert("fig");
                treeS.bottomUpInsert("grape");
                treeS.bottomUpInsert("honeydew");
                treeS.bottomUpInsert("kiwi");
                treeS.bottomUpInsert("lemon");
                treeS.bottomUpInsert("mango");
                treeS.bottomUpInsert("nectarine");
                treeS.bottomUpInsert("orange");
                treeS.bottomUpInsert("pear");
                treeS.bottomUpInsert("quince");
                treeS.bottomUpInsert("raspberry");
                treeS.bottomUpInsert("strawberry");
                treeS.bottomUpInsert("tangerine");
                treeS.bottomUpInsert("ugli");
                treeS.bottomUpInsert("watermelon");
                
                System.out.println("Initial Tree:");
                System.out.println(treeS.toString());
        
                System.out.println("Tree Validation:");
                System.out.println("Is the tree a valid Red-Black Tree? " + treeS.isValidRedBlackTree());
        
                
            }
    }
    

