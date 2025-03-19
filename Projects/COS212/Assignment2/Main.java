
public class Main {
    public static void main(String[] args) throws Exception {
        Hashmap<String, Integer> hashMap = new Hashmap<>();

       
        hashMap.insert("apple", 10);
        hashMap.insert("banana", 20);
        hashMap.insert("orange", 30);

        
        System.out.println("Value for key 'apple': " + hashMap.get("apple")); // Output: 10
        System.out.println("Value for key 'banana': " + hashMap.get("banana")); // Output: 20
        System.out.println("Value for key 'orange': " + hashMap.get("orange")); // Output: 30
        System.out.println(hashMap.toString());


        hashMap.delete("banana");

        
        System.out.println("Value for key 'banana' after deletion: " + hashMap.get("banana")); // Output: null

       
        Object[] keys = hashMap.getKeys();
        System.out.println("Keys in the hash map:");
        for (Object key : keys) {
            System.out.println(key);
        }

        
        hashMap.clear();

        Hashmap<String, Integer> hashMap1 = new Hashmap<>();
        Hashmap<Integer, Integer> hashMap2 = new Hashmap<>();
        Hashmap<String, String> hashMap3 = new Hashmap<>();
        Hashmap<Integer, String> hashMap4 = new Hashmap<>();
       for(int i = 0 ; i < 10001 ; i++){
        String strNumber = "" + i;
        hashMap1.insert(strNumber, 10);
       }

       for(int i = 0 ; i < 10001 ; i++){
        String strNumber = "" + i;
        hashMap4.insert( 10 , strNumber);
       }

       
       for(int i = 0 ; i < 10001 ; i++){
        String strNumber = "" + i;
        String strNumber1 = "" + i*20;
        hashMap3.insert(strNumber, strNumber1);
       }

       for(int i = 0 ; i < 10001 ; i++){
        int strNumber = i;
        int strNumber1 = i*20;
        hashMap2.insert(strNumber, strNumber1);
       }

       for(int i = 0; i < 10001 ; i++){
        hashMap2.delete(i);
       }

       for(int i = 0; i < 10001 ; i++){
        String strNumber = "" + i;
        hashMap3.delete(strNumber);
       }

       for(int i = 0; i < 10001 ; i++){
        hashMap4.delete(i);
       }


       for(int i = 0 ; i < 10001 ; i++){
        int strNumber = i;
        int strNumber1 = i*20;
        hashMap2.insert(strNumber, strNumber1);
       }

       for(int i = 0 ; i < 10001 ; i++){
        String strNumber = "" + i;
        String strNumber1 = "" + i*20;
        hashMap3.insert(strNumber, strNumber1);
       }

       for(int i = 0 ; i < 10001 ; i++){
        int strNumber =  i;
        String strNumber1 = "" + i*20;
        hashMap4.insert(strNumber, strNumber1);
       }
        
      
      
        
        System.out.println("Value for key 'u23536013': " + hashMap1.get("u23536013")); 
        System.out.println("Value for key 'u23536014': " + hashMap1.get("u23536014")); 
        System.out.println("Value for key 'u23536015': " + hashMap1.get("u23536015"));
        System.out.println("Value for key 'u23536016': " + hashMap1.get("u23536016")); 
        System.out.println("Value for key 'u23536017': " + hashMap1.get("u23536017")); 
        System.out.println("Value for key 'u23536018': " + hashMap1.get("u23536018")); 
        System.out.println("Value for key 'u23536019': " + hashMap1.get("u23536019")); 
        System.out.println("Value for key 'u23536020': " + hashMap1.get("u23536020")); 
        System.out.println("Value for key 'u23536021': " + hashMap1.get("u23536021")); 
        System.out.println(hashMap1.toString());

        Object[] keys2 = hashMap1.getKeys();
        System.out.println("Keys in the hash map:");
        for (Object key : keys2) {
            System.out.println(key);
        }


        hashMap1.delete("u23536019");
        System.out.println(hashMap1.toString());
        hashMap1.delete("u23536013");
        System.out.println(hashMap1.toString());
        hashMap1.insert("u23536013", 10);

        hashMap1.delete("u23536014");
        System.out.println(hashMap1.toString());

        hashMap1.delete("u23536015");
        System.out.println(hashMap1.toString());

        hashMap1.delete("u23536016");
        System.out.println(hashMap1.toString());

        hashMap1.delete("u23536017");
        System.out.println(hashMap1.toString());

        hashMap1.delete("u23536018");
        System.out.println(hashMap1.toString());

        
        hashMap1.delete("u23536021");
        System.out.println(hashMap1.toString());


        hashMap1.delete("u23536020");
        System.out.println(hashMap1.toString());
        hashMap1.delete("u23536020");

        hashMap1.insert("u23536013", 10);
        hashMap1.insert("u23536014", 20);
        hashMap1.insert("u23536015", 30);
        hashMap1.insert("u23536016", 5);
        hashMap1.insert("u23536017", 3);
        hashMap1.insert("u23536018", 2);
        hashMap1.insert("u23536019", 100);
        hashMap1.insert("u23536020", 81);
        hashMap1.insert("u23536021", 95);
        hashMap1.insert("u23536020", 81);
        hashMap1.insert("u23536020", 81);
        hashMap1.insert("u23536021", 95);

        hashMap1.clear();

        hashMap1.insert("u23536013", 10);
        hashMap1.insert("u23536014", 20);
        hashMap1.insert("u23536015", 30);
        hashMap1.insert("u23536016", 5);
        hashMap1.insert("u23536017", 3);
        hashMap1.insert("u23536018", 2);
        hashMap1.insert("u23536019", 100);
        hashMap1.insert("u23536020", 81);
        hashMap1.insert("u23536021", 95);
        hashMap1.insert("u23536020", 81);
        hashMap1.insert("u23536020", 81);
        hashMap1.insert("u23536021", 95);

        System.out.println(hashMap1.toString());
        
        System.out.println("Value for key 'u23536019' after deletion: " + hashMap1.get("u23536019")); // Output: null

       
        Object[] keys1 = hashMap1.getKeys();
        System.out.println("Keys in the hash map:");
        for (Object key : keys1) {
            System.out.println(key);
        }

        
        hashMap.clear();
        hashMap1.clear();


        
        System.out.println("Number of values after clearing: " + hashMap.getKeys().length); // Output: 0
        System.out.println(hashMap2.toString());
        System.out.println("Value for key '25215245245': " + hashMap4.get(252152452)); 
        Hashmap<Integer, String> hashMap5 = new Hashmap<>();
        hashMap5.insert(5, "1");
        hashMap5.insert(6, "1");
        System.out.println(hashMap5.getKeys());
        System.out.println(hashMap5.get(6));

        hashMap5.delete(5);
        hashMap5.delete(6);


        BTree<Integer> bTree = new BTree<>(3);

        // Insert some keys into the B-tree
        bTree.insert(10);
        System.out.println(bTree.toString());
        bTree.insert(20);
        System.out.println(bTree.toString());
        bTree.insert(5);
        System.out.println(bTree.toString());
        bTree.insert(30);
        System.out.println(bTree.toString());
        bTree.insert(15);
        System.out.println(bTree.toString());
        bTree.insert(25);
        System.out.println(bTree.toString());
        bTree.insert(35);
        System.out.println(bTree.toString());
        bTree.insert(45);
        System.out.println(bTree.toString());
        bTree.insert(40);
        System.out.println(bTree.toString());
        bTree.insert(50);
        System.out.println(bTree.toString());
        bTree.insert(47);
        System.out.println(bTree.toString());
        bTree.insert(4);
        System.out.println(bTree.toString());
        bTree.insert(11);
        System.out.println(bTree.toString());
        bTree.insert(13);
        System.out.println(bTree.toString());
        bTree.insert(2);
        
        System.out.println("B-tree:");
        System.out.println(bTree.toString());

        System.out.println("Path to key 15: " + bTree.printPath(15));
        System.out.println("Path to key 25: " + bTree.printPath(25));
        System.out.println("Path to key 25: " + bTree.printPath(50));
        System.out.println("Path to key 25: " + bTree.printPath(35));
        System.out.println("Path to key 25: " + bTree.printPath(36));



        PlayerMap playerMap1 = new PlayerMap(100, 123456, PlayerMap.PlayerType.RANDOM); 
        SemiRandomPlayer semiRandomPlayer = new SemiRandomPlayer(playerMap1, 123456);
        playerMap1.play();
        System.out.println(playerMap1.moves);
        

        PlayerMap playerMap2 = new PlayerMap(100, 123456, PlayerMap.PlayerType.AUTO); 
        AutomatedPlayer AutomatedPlayer = new AutomatedPlayer(playerMap2);
        playerMap2.play();
        System.out.println(playerMap2.moves);


    }
}

