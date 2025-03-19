import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LockTest {

    // Shared resources for the scenarios
    private static Counter counter = new Counter();
    private static Dequeuer dequeuer = new Dequeuer();

    public static void main(String[] args) throws InterruptedException {
        // Locks
        TASLock tasLock = new TASLock();
        TTASLock ttasLock = new TTASLock();
        BackoffLock backoffLock = new BackoffLock(10, 1000);

        // Scenario 1: Incrementing a shared counter
        System.out.println("Scenario 1: Incrementing a shared counter 10 threads");
        testLockInScenario("TASLock", tasLock, 10, "counter");
        testLockInScenario("TTASLock", ttasLock, 10, "counter");
        testLockInScenario("BackoffLock", backoffLock, 10, "counter");

        System.out.println("Scenario 1: Incrementing a shared counter 20 threads");
        testLockInScenario("TASLock", tasLock, 20, "counter");
        testLockInScenario("TTASLock", ttasLock, 20, "counter");
        testLockInScenario("BackoffLock", backoffLock, 20, "counter");

        System.out.println("Scenario 1: Incrementing a shared counter 30 threads");
        testLockInScenario("TASLock", tasLock, 30, "counter");
        testLockInScenario("TTASLock", ttasLock, 30, "counter");
        testLockInScenario("BackoffLock", backoffLock, 30, "counter");

        // Scenario 2: Dequeuing from a shared queue
        System.out.println("\nScenario 2: Dequeuing from a shared queue 10 threads");
        testLockInScenario("TASLock", tasLock, 10, "dequeuer");
        testLockInScenario("TTASLock", ttasLock, 10, "dequeuer");
        testLockInScenario("BackoffLock", backoffLock, 10, "dequeuer");

        System.out.println("\nScenario 2: Dequeuing from a shared queue 20 threads");
        testLockInScenario("TASLock", tasLock, 20, "dequeuer");
        testLockInScenario("TTASLock", ttasLock, 20, "dequeuer");
        testLockInScenario("BackoffLock", backoffLock, 20, "dequeuer");

        System.out.println("\nScenario 2: Dequeuing from a shared queue 30 threads");
        testLockInScenario("TASLock", tasLock, 30, "dequeuer");
        testLockInScenario("TTASLock", ttasLock, 30, "dequeuer");
        testLockInScenario("BackoffLock", backoffLock, 30, "dequeuer");

        // Test with higher thread count for deeper analysis
        System.out.println("\nTesting with higher thread counts (50 threads)...");
        testLockInScenario("TASLock", tasLock, 50, "counter");
        testLockInScenario("TTASLock", ttasLock, 50, "counter");
        testLockInScenario("BackoffLock", backoffLock, 50, "counter");
    }

    // Function to test locks in different scenarios
    private static void testLockInScenario(String lockName, Object lock, int threadCount, String scenario) throws InterruptedException {
        System.out.println("Testing " + lockName + " with " + threadCount + " threads on " + scenario);

        // Reset resources before each test
        counter = new Counter();
        dequeuer = new Dequeuer();

        long startTime = System.nanoTime();

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    // Scenario 1: Counter incrementing
                    if ("counter".equals(scenario)) {
                        if (lock instanceof TASLock) {
                            ((TASLock) lock).lock();
                            counter.increment();
                            ((TASLock) lock).unlock();
                        } else if (lock instanceof TTASLock) {
                            ((TTASLock) lock).lock();
                            counter.increment();
                            ((TTASLock) lock).unlock();
                        } else if (lock instanceof BackoffLock) {
                            ((BackoffLock) lock).lock();
                            counter.increment();
                            ((BackoffLock) lock).unlock();
                        }
                    }

                    // Scenario 2: Dequeuing from a shared queue
                    if ("dequeuer".equals(scenario)) {
                        if (lock instanceof TASLock) {
                            ((TASLock) lock).lock();
                            dequeuer.dequeue();
                            ((TASLock) lock).unlock();
                        } else if (lock instanceof TTASLock) {
                            ((TTASLock) lock).lock();
                            dequeuer.dequeue();
                            ((TTASLock) lock).unlock();
                        } else if (lock instanceof BackoffLock) {
                            ((BackoffLock) lock).lock();
                            dequeuer.dequeue();
                            ((BackoffLock) lock).unlock();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Completed in " + (duration / 1_000_000) + " ms");

        // Output final result of the counter scenario
        if ("counter".equals(scenario)) {
            System.out.println("Final Counter: " + counter.getCount());
        }
    }
}




