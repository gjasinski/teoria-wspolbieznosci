package tw04.task2.naive;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Buffer {
    private final int bufferSize;
    private final Lock lock = new ReentrantLock();
    private final Condition waitForEmptyBuffer = lock.newCondition();
    private final Condition waitForFullBuffer = lock.newCondition();
    private String buffer = "";
    private int emptySize;
    private int filledSize;

    Buffer(int bufferSize){
        this.bufferSize = bufferSize;
        this.emptySize = bufferSize;
        this.filledSize = 0;
    }

    void put(String dataToPut) throws InterruptedException {
        int sizeToPut = dataToPut.length();
        lock.lock();
        System.out.println("chce wsadzic"+sizeToPut);
        while (sizeToPut > emptySize){
            waitForEmptyBuffer.await();
        }
        buffer = buffer + dataToPut;
        emptySize -= dataToPut.length();
        filledSize += dataToPut.length();
        waitForEmptyBuffer.signalAll();
        lock.unlock();
    }

    String get(int sizeToGet) throws InterruptedException {
        System.out.println("2chce wziasc"+ sizeToGet);
        lock.lock();
        System.out.println("3chce: " + sizeToGet + "filled" + filledSize);
        while (sizeToGet > filledSize){
            System.out.println("4chce: " + sizeToGet + "filled" + filledSize);
           // waitForFullBuffer.await();
            waitForEmptyBuffer.await();
        }
        String result = buffer.substring(0, sizeToGet - 1);
        buffer = buffer.substring(sizeToGet);
        filledSize -= sizeToGet;
        emptySize += sizeToGet;
        waitForEmptyBuffer.signalAll();
        lock.unlock();
        return result;
    }
}
