package tw06;

import java.util.Random;
import java.util.concurrent.Future;

public class Producer implements Runnable {
    private Proxy proxy;
    private Random random;

    public Producer(Proxy proxy) {
        this.proxy = proxy;
        this.random = new Random();
    }

    @Override
    public void run() {
        int size = random.nextInt(Proxy.BUFFER_SIZE);
        String dataToPut = "";
        for (int i = 0; i < size; i++){
            dataToPut += (char)(65 + random.nextInt(32));
        }
        Future<Integer> result = proxy.putData(dataToPut);
        while (!result.isDone()){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println(result.get() + dataToPut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
