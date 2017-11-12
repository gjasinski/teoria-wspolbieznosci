package tw04.task2.fair;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Buffer {
    private final Lock lock = new ReentrantLock();
    private final Condition waitForBuffer = lock.newCondition();
    private String buffer = "";
    private int emptySize;
    private int filledSize;
    private final int[] waitingRoomPut;
    private final int[] waitingRoomGet;
    private int numberOfProducers;
    private int numberOfConsumers;
    private int currNumberOfProducers = 0;
    private int currNumberOfConsumers = 0;
    private boolean producentsCanPut;

    Buffer(int bufferSize, int numberOfConsumers, int numberOfProducers) {
        this.emptySize = bufferSize;
        this.filledSize = 0;
        this.waitingRoomPut = new int[bufferSize/2];
        this.waitingRoomGet = new int[bufferSize/2];
        for(int i = 0; i < bufferSize/2; i++){
            this.waitingRoomGet[i] = this.waitingRoomPut[i] = -1;
        }
        this.numberOfConsumers = numberOfConsumers;
        this.numberOfProducers = numberOfProducers;
    }

    boolean put(String dataToPut, int id) throws InterruptedException {
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

    String get(int sizeToGet, int id) throws InterruptedException {
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

    synchronized void decrementNumberOfProducents(){
        this.numberOfProducers--;
    }

    synchronized void decrementNumberOfConsumers(){
        this.numberOfConsumers--;
    }
}
