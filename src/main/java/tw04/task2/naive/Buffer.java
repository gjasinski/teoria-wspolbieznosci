package tw04.task2.naive;

import tw04.task2.IBuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Buffer implements IBuffer{
    private final Lock lock = new ReentrantLock();
    private final Condition waitForBuffer = lock.newCondition();
    private String buffer = "";
    private int emptySize;
    private int filledSize;

    Buffer(int bufferSize) {
        this.emptySize = bufferSize;
        this.filledSize = 0;
    }

    @Override
    public void put(String dataToPut) throws InterruptedException {
        int sizeToPut = dataToPut.length();
        lock.lock();
        while (sizeToPut > emptySize) {
            waitForBuffer.await();
        }
        buffer = buffer + dataToPut;
        emptySize -= dataToPut.length();
        filledSize += dataToPut.length();
        waitForBuffer.signalAll();
        lock.unlock();
    }

    @Override
    public String get(int sizeToGet) throws InterruptedException {
        lock.lock();
        while (sizeToGet > filledSize) {
            waitForBuffer.await();
        }
        String result = buffer.substring(0, sizeToGet);
        buffer = buffer.substring(sizeToGet);
        filledSize -= sizeToGet;
        emptySize += sizeToGet;
        waitForBuffer.signalAll();
        lock.unlock();
        return result;
    }
}
