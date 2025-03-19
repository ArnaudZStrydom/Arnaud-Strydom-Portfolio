
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Threeterson implements Lock {
    private final ReentrantLock printLock;
    private final Output output;

    private static final int num_thr = 3;
    private final int[] level = new int[num_thr];
    private final int[] victim = new int[num_thr];

    public Threeterson(Output output) {
        this.printLock = new ReentrantLock();
        this.output = output;
        for (int i = 0; i < num_thr; i++) {
            level[i] = 0;
        }
    }

    private int getThreadId() {
        return (int) (Thread.currentThread().getId() % num_thr);
    }

    @Override
    public void lock() {
        int id = getThreadId();
        for (int i = 1; i < num_thr ; i++) {
            level[id] = i;
            victim[i] = id;

            
            if (i > 0) {
                printLock.lock();
                try {
                    output.println(Thread.currentThread().getName() + " at L" + i);
                } finally {
                    printLock.unlock();
                }
            }

            
            while (exists(id,i)){};

            
            printLock.lock();
            try {
                if (victim[i] == id) {
                    output.println(Thread.currentThread().getName() + " is the victim of L" + i);
                }
            } finally {
                printLock.unlock();
            }
        }

        printLock.lock();
        try {
            output.println(Thread.currentThread().getName() + " has the lock");
        } finally {
            printLock.unlock();
        }

    }

    private boolean exists(int id, int i){
        for(int k=0 ; k< num_thr ; k++){
            if(k==id)
                continue;
            
            if(level[k]>=i && victim[i]==id)
                return true;
        }
        return false;
    }


    @Override
    public void unlock() {
        printLock.lock();
        try {
            output.println(Thread.currentThread().getName() + " unlocked the lock");
        } finally {
            printLock.unlock();
        }
    
        level[getThreadId()] = 0;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
