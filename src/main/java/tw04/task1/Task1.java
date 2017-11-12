package tw04.task1;

public class Task1 {
    public static void main(String[] main) {
        int id = 0;
        int bufferSize = 100;
        int processorsNumber = 5;
        Thread[] threads = new Thread[processorsNumber + 2];
        Buffer buffer = new Buffer(bufferSize);
        Producer producer = new Producer(id, buffer, bufferSize);
        threads[0] = new Thread(producer);
        id++;
        for(int i = 0; i < processorsNumber; i++, id++){
            ComputingProcessor cp = new ComputingProcessor(id, buffer, bufferSize);
            threads[i+1] = new Thread(cp);
        }
        Consumer consumer = new Consumer(id, buffer, bufferSize);
        threads[processorsNumber + 1] = new Thread(consumer);
        for (int i = 0; i < processorsNumber + 2; i++){
            threads[i].start();
        }
    }

    private static void startThread(BufferOperator threadToStart){
        Thread t = new Thread(threadToStart);
        t.run();
    }
}
