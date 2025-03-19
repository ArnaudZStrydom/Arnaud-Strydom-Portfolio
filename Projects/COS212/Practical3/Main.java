public class Main {
    public static void main(String[] args) {

        SplayTree SP1 = new SplayTree("{[u10:50%]{[u5:40%]{}{[u7:45%]{}{}}}{[u15:60%]{[u12:55%]{}{}}{}}}");
        System.out.println(SP1.toStringOneLine());
        System.out.println("\n");

        System.out.println(SP1.toString());

        SplayTree SP2 = new SplayTree("{[u10:50%]{}{}}");
        System.out.println(SP2.toStringOneLine());
        System.out.println("\n");

        System.out.println(SP2.toString());

        SplayTree SP3 = new SplayTree("{[u10:50%]{[u5:40%]{}{}}{[u15:60%]{}{}}}");
        System.out.println(SP3.toStringOneLine());
        System.out.println("\n");
        
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.access(13,60);
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.access(13,50);
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.access(103,null);
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.access(10);
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.remove(10);
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.remove(12);
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.remove(5);
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.remove(7);
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.remove(13);
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.remove(15);
        System.out.println(SP1.toString());
        System.out.println("\n");
        SP1.remove(103);
        System.out.println(SP1.toString());
        System.out.println("\n");

        System.out.println(SP1.sortByStudentNumber());
        System.out.println(SP1.sortByMark());

        






    }
}