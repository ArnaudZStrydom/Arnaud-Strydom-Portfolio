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
        System.out.println(ANSI_RED + "Test "+TESTS_RUN+" Failed: Expected "+ Arrays.toString(expect) +" but got "+Arrays.toString(actual)+" \n======================" + ANSI_RESET);
    }
}

public static void assertEquals(int actual , int expect){
    TESTS_RUN++;
    if(actual == expect){
        TESTS_PASSED++;
        System.out.println(ANSI_GREEN + "Test "+TESTS_RUN+" passed \n======================" + ANSI_RESET);
    }else{
        System.out.println(ANSI_RED + "Test "+TESTS_RUN+" Failed: Expected "+ String.valueOf(expect) +" but got "+ String.valueOf(actual)+" \n======================" + ANSI_RESET);
    }
}

public static void assertEquals(String actual , String expect){
    TESTS_RUN++;
    if(actual.equals(expect)){
        TESTS_PASSED++;
        System.out.println(ANSI_GREEN + "Test "+TESTS_RUN+" passed \n======================" + ANSI_RESET);
    }else{
        System.out.println(ANSI_RED + "Test "+TESTS_RUN+" Failed: Expected "+ expect+" but got "+actual+" \n======================" + ANSI_RESET);
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
        start_suite("array testing");
        RecursiveArray Ar1 = new RecursiveArray("100000,20101,3,4,145252524,36325215,5");
        Integer[] array1 ;
        Integer[] array2 ;
        array1 = new Integer[]{100000,20101,3,4,145252524,36325215,5};
        assertEquals(Ar1.array, array1);
        Ar1.append(2542);
        array2 = Ar1.array;
        assertEquals(Ar1.array, array2);
        RecursiveArray Ar5 = new RecursiveArray();
        RecursiveArray Ar2 = new RecursiveArray("");
        RecursiveArray Ar3 = new RecursiveArray("5,36325215,145252524,4,3,20101,100000");
        RecursiveArray Ar4 = new RecursiveArray("");
        Ar2.prepend(100000);
        Ar2.prepend(20101);
        Ar2.prepend(3);
        Ar2.prepend(4);
        Ar2.prepend(145252524);
        Ar2.prepend(36325215);
        Ar2.prepend(5);

        assertEquals(Ar2.array, Ar3.array);

        end_suite();

        start_suite("String test");
        String Sar1 = Ar1.toString();
        String Sar2 = Arrays.toString(array2);
        String Sar3 = Arrays.toString(array1);
        String Sar4 = Ar2.toString();
        String Sar5 = Ar3.toString();
        String Sar6 = Ar4.toString();
        String Sar9 = Ar5.toString();


        Sar2 = Sar2.replaceAll("\\s+", "");
        assertEquals(Sar1, Sar2);
        assertEquals(Sar5, Sar4);
        assertEquals(Sar4, Sar5);
        assertEquals(Sar6, "Empty Array");
        assertEquals(Sar9, "Empty Array");


        end_suite();

        System.out.println(Sar1);   
        System.out.println(Sar2);
        System.out.println(Sar3);
        System.out.println(Sar4);
        System.out.println(Sar5);
        System.out.println(Sar6);
        System.out.println(Ar1.contains(3));
        System.out.println(Ar1.contains(7));
        Ar1.sortAscending();
        String Sar7 = Ar1.toString();

        start_suite("Accending testing");
        assertEquals(Sar7, "[3,4,5,2542,20101,100000,36325215,145252524]");
        Ar1.isAscending();
        System.out.println(Sar7);
        end_suite();


        Ar1.sortDescending();
        String Sar8 = Ar1.toString();

        start_suite("Decending testing");
        assertEquals(Sar8, "[145252524,36325215,100000,20101,2542,5,4,3]");
        System.out.println(Ar1.isDescending());
        System.out.println(Sar8);
        end_suite();

        end_all();
    }
}