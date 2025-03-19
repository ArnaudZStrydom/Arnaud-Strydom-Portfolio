public class Main {
    /*
     * 1 Testing
     * 2 Normal
     */
    public static int PRINT_MODE = 1;

    public static String ANSI_RED = "\u001b[31;1m";
    public static String ANSI_GREEN = "\u001b[32;1m";
    public static String ANSI_BLUE = "\033[0;34m";
    public static String ANSI_RESET = "\u001b[0m";
    public static int SUITES_RUN = 0;
    public static int SUITES_PASSED = 0;
    public static int TESTS_RUN = 0;
    public static int TESTS_PASSED = 0;

    public static void main(String[] args) {
        if (args.length == 0) {
            task1();
            task2();
        } else {
            int taskNum = Integer.valueOf(args[0]);
            switch (taskNum) {
                case 1:
                    task1();
                    break;
                case 2:
                    task2();
                    break;

            }
        }
        endAll();
    }

    public static void task1() {
        startSuite("Node testing");
        Node n1 = new Node(5);
        assertEquals(n1.toString(), "[5]");
        Node n2 = new Node(2, n1);
        assertEquals(n2.toString(), "[2]");
        endSuite();
    }

    public static void task2() {
        startSuite("LinkedList testing");
        LinkedList l1 = new LinkedList();
        assertEquals(l1.toString(), "Empty");
        LinkedList l2 = new LinkedList(4);
        assertEquals(l2.toString(), "[4]");
        LinkedList l3 = new LinkedList(new int[] { 1, 2, 3, 4 });
        assertEquals(l3.toString(), "[1]->[2]->[3]->[4]");
        l3.insert(-1);
        assertEquals(l3.toString(), "[-1]->[1]->[2]->[3]->[4]");
        assertEquals(l3.contains(1), true);
        assertEquals(l3.contains(0), false);
        l3.delete(1);
        assertEquals(l3.toString(), "[-1]->[2]->[3]->[4]");
        l3.delete(69);
        assertEquals(l3.toString(), "[-1]->[2]->[3]->[4]");
        l3.delete(10);
        assertEquals(l3.toString(), "[-1]->[2]->[3]->[4]");
        l3.delete(-1);
        assertEquals(l3.toString(), "[2]->[3]->[4]");
        l3.delete(4);
        assertEquals(l3.toString(), "[2]->[3]");
        endSuite();
    }

    public static void startSuite(String name) {
        switch (PRINT_MODE) {
            case 1:
                SUITES_RUN++;
                System.out.println("===================\nStarting: " + name + "\n===================");
                break;
        }
    }

    public static void endSuite() {
        switch (PRINT_MODE) {
            case 1:
                if (TESTS_RUN == TESTS_PASSED) {
                    SUITES_PASSED++;
                    System.out.println(ANSI_GREEN + "All Tests Passed " + ANSI_RESET + "\n===================");
                } else {
                    System.out.println(ANSI_RED + "Tests Failed: " + (TESTS_RUN - TESTS_PASSED)
                            + "\n===================" + ANSI_RESET);
                }
                TESTS_RUN = 0;
                TESTS_PASSED = 0;
                break;
        }
    }

    public static <T> void assertEquals(T actual, T expected) {
        switch (PRINT_MODE) {
            case 1:
                TESTS_RUN++;
                if (actual.equals(expected)) {
                    TESTS_PASSED++;
                    System.out.println(ANSI_GREEN + "Test " + TESTS_RUN + " Passed " + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "Test " + TESTS_RUN + " Failed: Expected " + expected + " but got "
                            + actual + ANSI_RESET);
                }
                break;
            case 2:
                System.out.println(actual);
                break;
        }
    }

    public static void assertEquals(String actual, String expected) {
        switch (PRINT_MODE) {
            case 1:
                TESTS_RUN++;
                if (actual.equals(expected)) {
                    TESTS_PASSED++;
                    System.out.println(ANSI_GREEN + "Test " + TESTS_RUN + " Passed " + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "Test " + TESTS_RUN + " Failed: Expected " + ANSI_RESET);
                    boolean wrong = false;
                    for (int i = 0; i < expected.length(); i++) {
                        if (i < actual.length() && actual.charAt(i) == expected.charAt(i)) {
                            if (wrong) {
                                System.out.print(ANSI_RESET);
                                wrong = false;
                            }
                        } else if (!wrong) {
                            System.out.print(ANSI_RED);
                            wrong = true;
                        }
                        System.out.print(expected.charAt(i));
                    }
                    System.out.print(ANSI_RESET + " but got ");
                    wrong = false;
                    for (int i = 0; i < actual.length(); i++) {
                        if (i < expected.length() && actual.charAt(i) == expected.charAt(i)) {
                            if (wrong) {
                                System.out.print(ANSI_RESET);
                                wrong = false;
                            }
                        } else if (!wrong) {
                            System.out.print(ANSI_RED);
                            wrong = true;
                        }
                        System.out.print(actual.charAt(i));
                    }
                    System.err.println(ANSI_RESET);
                }
                break;
            case 2:
                System.out.println(actual);
                break;
        }
    }

    public static void endAll() {
        switch (PRINT_MODE) {
            case 1:
                if (SUITES_RUN == SUITES_PASSED) {
                    System.out.println(
                            ANSI_GREEN + "\n\n===================\n" + "All Suites Passed " + SUITES_PASSED + "/"
                                    + SUITES_RUN + "\n===================" + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "===================\n" + "Some Suites Failed: " + SUITES_PASSED + "/"
                            + SUITES_RUN + "\n===================" + ANSI_RESET);
                }
                break;
        }
    }
}