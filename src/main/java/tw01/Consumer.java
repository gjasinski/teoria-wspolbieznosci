package tw01;

public class Consumer implements Runnable {
    private Buffer buffer;
    private int max;

    public Consumer(Buffer buffer, int max) {
        this.buffer = buffer;
        this.max = max;
    }

    public void run() {
        for (int i = 0; i < this.max; i++) {
            String message;
            message = buffer.take();
            System.out.println(message);

        }

    }
}