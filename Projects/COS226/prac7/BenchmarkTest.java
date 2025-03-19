import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BenchmarkTest {

    public static void main(String[] args) throws InterruptedException {
        int[] threadCounts = {2, 4, 8, 16}; // Varying thread counts
        int operationsPerThread = 100000;   // Number of operations per thread

        // Create stack instances
        SequentialStack<Integer> sequentialStack = new SequentialStack<>();
        LockFreeStack<Integer> lockFreeStack = new LockFreeStack<>();
        EliminationBackoffStack<Integer> eliminationStack = new EliminationBackoffStack<>();

        // 1. Time taken vs Number of Threads
        System.out.println("Time taken vs Number of Threads:");
        runBenchmarkTimeVsThreads(sequentialStack, threadCounts, operationsPerThread, "Sequential/Blocking Stack");
        runBenchmarkTimeVsThreads(lockFreeStack, threadCounts, operationsPerThread, "Lock-Free Stack");
        runBenchmarkTimeVsThreads(eliminationStack, threadCounts, operationsPerThread, "Elimination Backoff Stack");

        // 2. Average Time Spent Waiting vs Number of Operations
        System.out.println("\nAverage Time Spent Waiting vs Number of Operations:");
        runBenchmarkWaitingTime(sequentialStack, threadCounts, operationsPerThread, "Sequential/Blocking Stack");
        runBenchmarkWaitingTime(lockFreeStack, threadCounts, operationsPerThread, "Lock-Free Stack");
        runBenchmarkWaitingTime(eliminationStack, threadCounts, operationsPerThread, "Elimination Backoff Stack");

        // 3. Throughput vs Number of Threads
        System.out.println("\nThroughput vs Number of Threads:");
        runBenchmarkThroughput(sequentialStack, threadCounts, operationsPerThread, "Sequential/Blocking Stack");
        runBenchmarkThroughput(lockFreeStack, threadCounts, operationsPerThread, "Lock-Free Stack");
        runBenchmarkThroughput(eliminationStack, threadCounts, operationsPerThread, "Elimination Backoff Stack");
    }

    private static void runBenchmarkTimeVsThreads(Object stack, int[] threadCounts, int operationsPerThread, String stackType) 
            throws InterruptedException {
        System.out.println(stackType + " Benchmark:");
        for (int threadCount : threadCounts) {
            long startTime = System.nanoTime();
            runTest(stack, threadCount, operationsPerThread);
            long endTime = System.nanoTime();
            long totalTime = (endTime - startTime) / 1_000_000; // Convert to milliseconds
            System.out.printf("Threads: %d, Time Taken: %d ms%n", threadCount, totalTime);
        }
    }

    private static void runBenchmarkWaitingTime(Object stack, int[] threadCounts, int operationsPerThread, String stackType) 
            throws InterruptedException {
        System.out.println(stackType + " Benchmark:");
        for (int threadCount : threadCounts) {
            long totalWaitTime = 0;
            for (int i = 0; i < threadCount; i++) {
                long threadWaitTime = measureThreadWaitTime(stack, operationsPerThread);
                totalWaitTime += threadWaitTime;
            }
            double averageWaitTime = (double) totalWaitTime / threadCount;
            System.out.printf("Threads: %d, Average Wait Time: %.2f ms%n", threadCount, averageWaitTime);
        }
    }

    private static void runBenchmarkThroughput(Object stack, int[] threadCounts, int operationsPerThread, String stackType) 
            throws InterruptedException {
        System.out.println(stackType + " Benchmark:");
        for (int threadCount : threadCounts) {
            long startTime = System.nanoTime();
            runTest(stack, threadCount, operationsPerThread);
            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            long totalOps = (long) threadCount * operationsPerThread;
            double throughput = (double) totalOps / (totalTime / 1_000_000_000.0);
            System.out.printf("Threads: %d, Throughput: %.2f ops/sec%n", threadCount, throughput);
        }
    }

    private static void runTest(Object stack, int threadCount, int operationsPerThread) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    performStackOperations(stack, j);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
    }

    private static void performStackOperations(Object stack, int value) {
        if (stack instanceof SequentialStack<?>) {
            SequentialStack<Integer> seqStack = (SequentialStack<Integer>) stack;
            seqStack.push(value);
            seqStack.pop();
        } else if (stack instanceof LockFreeStack<?>) {
            LockFreeStack<Integer> lockFreeStack = (LockFreeStack<Integer>) stack;
            lockFreeStack.push(value);
            lockFreeStack.pop();
        } else if (stack instanceof EliminationBackoffStack<?>) {
            EliminationBackoffStack<Integer> elimStack = (EliminationBackoffStack<Integer>) stack;
            elimStack.push(value);
            elimStack.pop();
        }
    }

    private static long measureThreadWaitTime(Object stack, int operationsPerThread) {
        long waitTime = 0;
        for (int j = 0; j < operationsPerThread; j++) {
            long startWait = System.nanoTime();
            performStackOperations(stack, j);
            long endWait = System.nanoTime();
            waitTime += (endWait - startWait);
        }
        return waitTime / 1_000_000; // Convert to milliseconds
    }
}

