import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Test cases
        List<MethodCall> test1 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("A", 2, "deq(x)")
        );
        testExecutionOrder("Single Thread Enqueue-Dequeue", test1);

        List<MethodCall> test2 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("A", 2, "enq(y)"),
            new MethodCall("A", 3, "deq(x)"),
            new MethodCall("A", 4, "deq(y)")
        );
        testExecutionOrder("Single Thread Multiple Operations", test2);

        List<MethodCall> test3 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("B", 1, "enq(y)"),
            new MethodCall("A", 2, "deq(x)"),
            new MethodCall("B", 2, "deq(y)")
        );
        testExecutionOrder("Two Threads, Independent Items", test3);

        List<MethodCall> test4 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("B", 1, "enq(x)"),
            new MethodCall("A", 2, "deq(x)"),
            new MethodCall("B", 2, "deq(x)")
        );
        testExecutionOrder("Two Threads, Same Item", test4);

        List<MethodCall> test5 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("A", 2, "enq(y)"),
            new MethodCall("B", 1, "enq(z)"),
            new MethodCall("B", 2, "enq(w)")
        );
        testExecutionOrder("Enqueue Only", test5);

        List<MethodCall> test6 = Arrays.asList(
            new MethodCall("A", 1, "deq(x)"),
            new MethodCall("B", 1, "deq(y)")
        );
        testExecutionOrder("Dequeue Without Enqueue", test6);

        List<MethodCall> test7 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("B", 1, "deq(x)"),
            new MethodCall("A", 2, "enq(y)"),
            new MethodCall("B", 2, "deq(y)")
        );
        testExecutionOrder("Multiple Threads, Mixed Operations", test7);

        List<MethodCall> test8 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("B", 1, "deq(y)"),
            new MethodCall("A", 2, "deq(x)"),
            new MethodCall("B", 2, "enq(y)")
        );
        testExecutionOrder("Interleaved Enqueues and Dequeues", test8);

        List<MethodCall> test9 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("A", 2, "enq(y)"),
            new MethodCall("B", 1, "enq(x)"),
            new MethodCall("B", 2, "enq(y)"),
            new MethodCall("A", 3, "deq(x)"),
            new MethodCall("B", 3, "deq(y)")
        );
        testExecutionOrder("Complex Interleaving", test9);

        List<MethodCall> test10 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("B", 1, "enq(x)"),
            new MethodCall("C", 1, "enq(x)"),
            new MethodCall("A", 2, "deq(x)"),
            new MethodCall("B", 2, "deq(x)"),
            new MethodCall("C", 2, "deq(x)")
        );
        testExecutionOrder("Complex Interleaving with Three Threads", test10);

        List<MethodCall> test11 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("B", 1, "enq(x)"),
            new MethodCall("C", 1, "enq(x)"),
            new MethodCall("D", 1, "enq(x)"),
            new MethodCall("A", 2, "deq(x)"),
            new MethodCall("B", 2, "deq(x)"),
            new MethodCall("C", 2, "deq(x)"),
            new MethodCall("D", 2, "deq(x)")
        );
        testExecutionOrder("Complex Interleaving with Four Threads", test11);

        List<MethodCall> test12 = Arrays.asList(
            new MethodCall("A", 1, "enq(x)"),
            new MethodCall("B", 1, "enq(y)"),
            new MethodCall("A", 2, "deq(x)"),
            new MethodCall("C", 1, "enq(z)"),
            new MethodCall("B", 2, "deq(y)"),
            new MethodCall("C", 2, "deq(z)"),
            new MethodCall("D", 1, "enq(w)"),
            new MethodCall("D", 2, "deq(w)")
        );
        testExecutionOrder("Complex Interleaving with Tight Dependencies", test12);
    }

    private static void testExecutionOrder(String testName, List<MethodCall> operations) {
        System.out.println("Running Test: " + testName);
        List<List<MethodCall>> possibleOrders = ExecutionOrderChecker.findPossibleOrders(operations);
        // Print each possible order
        for (List<MethodCall> order : possibleOrders) {
            System.out.println(order);
        }
        // Optionally, you can also add expected results and compare here
        System.out.println("Test: " + testName + " passed: " + !possibleOrders.isEmpty());
        System.out.println();
    }
}