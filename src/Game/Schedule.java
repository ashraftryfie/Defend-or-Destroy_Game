package Game;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Schedule extends ScheduledThreadPoolExecutor {
    private boolean isPaused;
    private ReentrantLock lock;
    private Condition condition;

    public Schedule(int corePoolSize) {
        super(corePoolSize);
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    @Override
    protected void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        lock.lock();
        try {
            while (isPaused)
                condition.await();
        } catch (InterruptedException ie)
        { thread.interrupt();
        } finally {
            lock.unlock();
        } }
    public boolean isRunning() {
        return !isPaused;
    }
    public boolean isPaused() {
        return isPaused;
    }

    /** Pause the execution */
    public void pause() {
        lock.lock(); try {
            isPaused = true;
        }
        finally {
            lock.unlock();
        }
    }

    /** Resume pool execution */
    public void resume() {
        lock.lock(); try {
            isPaused = false;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}