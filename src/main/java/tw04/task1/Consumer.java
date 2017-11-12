package tw04.task1;

import java.util.Random;

public class Consumer implements BufferOperator {
    private final Buffer buffer;
    private Random generator = new Random();
    private final int id;
    private final int bufferSize;

    Consumer(int id, Buffer buffer, int bufferSize) {
        this.id = id;
        this.buffer = buffer;
        this.bufferSize = bufferSize;
        System.out.println(toString());
    }

    @Override
    public void run() {
        try {
            for (int resource = 0; resource < bufferSize; resource++) {
                int resourceVal = buffer.getResource(resource, id);
                Thread.sleep(Math.abs(generator.nextInt()) % 100);
                System.out.println(String.format("Resource[%s] = %s", resource, resourceVal));
                buffer.releaseResource(resource, id);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Consumer{" +
                "id=" + id +
                '}';
    }
}
