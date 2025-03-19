import java.util.*;

public class ExecutionOrderChecker {
    public static List<List<MethodCall>> findPossibleOrders(List<MethodCall> operations) {
        List<List<MethodCall>> result = new ArrayList<>();
        Map<String, List<MethodCall>> threadGroups = groupByThread(operations);
        List<String> threadIds = new ArrayList<>(threadGroups.keySet());
        
        generateOrders(new ArrayList<>(), threadGroups, new LinkedList<>(), result, threadIds);
        
        return result;
    }

    private static Map<String, List<MethodCall>> groupByThread(List<MethodCall> operations) {
        Map<String, List<MethodCall>> groups = new TreeMap<>();
        for (MethodCall call : operations) {
            groups.computeIfAbsent(call.threadId, k -> new ArrayList<>()).add(call);
        }
        return groups;
    }

    private static void generateOrders(List<MethodCall> current, Map<String, List<MethodCall>> threadGroups, 
                                       Queue<String> queue, List<List<MethodCall>> result, List<String> threadIds) {
        if (allThreadsExhausted(threadGroups)) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (String threadId : threadIds) {
            List<MethodCall> threadCalls = threadGroups.get(threadId);
            if (!threadCalls.isEmpty()) {
                MethodCall call = threadCalls.get(0);
                threadCalls.remove(0);
                current.add(call);

                Queue<String> newQueue = new LinkedList<>(queue);
                boolean valid = true;

                if (call.action.startsWith("enq")) {
                    newQueue.offer(call.action.substring(4, 5));
                } else if (call.action.startsWith("deq")) {
                    if (newQueue.isEmpty() || !newQueue.poll().equals(call.action.substring(4, 5))) {
                        valid = false;
                    }
                }

                if (valid) {
                    generateOrders(current, threadGroups, newQueue, result, threadIds);
                }

                current.remove(current.size() - 1);
                threadCalls.add(0, call);
            }
        }
    }

    private static boolean allThreadsExhausted(Map<String, List<MethodCall>> threadGroups) {
        return threadGroups.values().stream().allMatch(List::isEmpty);
    }
}