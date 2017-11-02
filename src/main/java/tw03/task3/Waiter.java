package tw03.task3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Waiter {
    private static final int WAIT_FOR_SECOND_PERSON = 1;
    private final Lock firstPersonSat = new ReentrantLock();
    private final Condition waitForNextPerson = firstPersonSat.newCondition();
    private final Condition waitForTable = firstPersonSat.newCondition();
    private volatile int pairId;
    private boolean firstPersonUnlocked = false;
    private boolean firstPerson = true;
    private boolean tableIsEngaged = false;

    boolean giveMeTable(int pairId) throws InterruptedException {
        firstPersonSat.lock();
        if (tableIsEngaged) {
            waitForTable.await();
        }
        boolean res;
        res = firstPerson;
        firstPerson = false;
        try {
            if (res) {
                this.pairId = pairId;
                if (!waitForNextPerson.await(WAIT_FOR_SECOND_PERSON, TimeUnit.SECONDS)) {
                    this.pairId = -1;
                    return false;
                } else {
                    this.pairId = pairId;
                    return true;
                }

            } else {
                if (this.pairId == pairId) {
                    System.out.println("");
                    tableIsEngaged = true;
                    this.pairId = -1;
                    waitForNextPerson.signal();
                    return true;
                } else {
                    return false;
                }

            }
        } finally {
            firstPersonSat.unlock();
        }
    }

    void releaseTable() {
        firstPersonSat.lock();
        try {
            if (!firstPersonUnlocked) {
                firstPersonUnlocked = true;
            } else {
                tableIsEngaged = false;
                this.firstPerson = true;
                firstPersonUnlocked = false;
                waitForTable.signalAll();
            }
        } finally {
            firstPersonSat.unlock();
        }
    }
}
