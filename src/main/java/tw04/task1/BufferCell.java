package tw04.task1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BufferCell {
    private int prevId = -1;
    private final Lock lock = new ReentrantLock();
    private final Condition waitForYourTime = lock.newCondition();
    private int resource = 0;

    int getResource(int id) throws InterruptedException {
        lock.lock();
        while (prevId + 1 != id) {
            waitForYourTime.await();
        }
        return resource;
    }

    void setResource(int newValue){
        resource = newValue;
    }

    void releaseResource(int id) {
        this.prevId = id;
        waitForYourTime.signalAll();
        lock.unlock();
    }
}
