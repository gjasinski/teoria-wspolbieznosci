package tw04.task1;

import java.util.Random;

public class Producer implements BufferOperator {
    private final Buffer buffer;
    private Random generator = new Random();
    private final int id;
    private final int bufferSize;

    Producer(int id, Buffer buffer, int bufferSize) {
        this.id = id;
        this.buffer = buffer;
        this.bufferSize = bufferSize;
    }

    @Override
    public void run() {
        try {
            for (int resource = 0; resource < bufferSize; resource++) {
                buffer.getResource(resource, id);
                Thread.sleep(Math.abs(generator.nextInt()) % 100);
                buffer.setResource(resource, 0);
                buffer.releaseResource(resource, id);
                System.out.println("Wyprodukowalem: " + resource + toString());
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                '}';
    }
}
