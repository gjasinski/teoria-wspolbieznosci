package tw04.task1;

import java.util.Random;

public class ComputingProcessor implements BufferOperator {
    private final Buffer buffer;
    private Random generator = new Random();
    private final int id;
    private final int bufferSize;

    ComputingProcessor(int id, Buffer buffer, int bufferSize) {
        this.id = id;
        this.buffer = buffer;
        this.bufferSize = bufferSize;
    }

    @Override
    public void run() {
        try {
            for (int resource = 0; resource < bufferSize; resource++) {
                int resourceVal = buffer.getResource(resource, id);
                Thread.sleep(Math.abs(generator.nextInt()) % 100);
                buffer.setResource(resource, resourceVal + 1);
                buffer.releaseResource(resource, id);
                System.out.println("Przetwozylem zasob " + resource + toString());
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ComputingProcessor{" +
                "id=" + id +
                '}';
    }
}
