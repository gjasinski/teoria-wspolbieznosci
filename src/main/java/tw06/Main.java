package tw06;

public class Main {
    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        for(int i = 0; i < 100; i++){
            Consumer consumer = new Consumer(proxy);
            Producer producer = new Producer(proxy);
            Thread t1 = new Thread(consumer);
            Thread t2 = new Thread(producer);
            t1.start();
            t2.start();
        }
        while (true);
    }
}
