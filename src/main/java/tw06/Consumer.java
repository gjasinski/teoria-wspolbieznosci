package tw06;

import java.util.Random;
import java.util.concurrent.Future;

public class Consumer implements Runnable{
    private Proxy proxy;

    public Consumer(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public void run() {
        int dataToGet = (new Random()).nextInt(Proxy.BUFFER_SIZE);
        Future<String> result = proxy.getData(dataToGet);
        while (!result.isDone()){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println(dataToGet + " " +result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
