package tw04.task2.fair;

import tw04.task2.IBuffer;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer implements IBuffer {
    private final Semaphore semaphorePut = new Semaphore(1, true);
    private final Semaphore semaphoreGet = new Semaphore(1, true);
    private final Lock bufferLock = new ReentrantLock();
    private final Condition waitForBuffer = bufferLock.newCondition();
    private String buffer = "";
    private int emptySize;
    private int filledSize;


    Buffer(int bufferSize) {
        this.emptySize = bufferSize;
        this.filledSize = 0;
    }

    public void put(String dataToPut) throws InterruptedException {
        semaphorePut.acquire();
        int sizeToPut = dataToPut.length();
        bufferLock.lock();
        while (sizeToPut > emptySize) {
            waitForBuffer.await();
        }
        buffer = buffer + dataToPut;
        emptySize -= dataToPut.length();
        filledSize += dataToPut.length();
        waitForBuffer.signalAll();
        bufferLock.unlock();
        semaphorePut.release();
    }

    public String get(int sizeToGet) throws InterruptedException {
        semaphoreGet.acquire();
        bufferLock.lock();
        while (sizeToGet > filledSize) {
            waitForBuffer.await();
        }
        String result = buffer.substring(0, sizeToGet);
        buffer = buffer.substring(sizeToGet);
        filledSize -= sizeToGet;
        emptySize += sizeToGet;
        waitForBuffer.signalAll();
        bufferLock.unlock();
        semaphoreGet.release();
        return result;
    }

}
