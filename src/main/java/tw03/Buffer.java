/*package tw03;

public class Buffer {
	private String buf;

    Buffer() throws InterruptedException {
    }

    void put(String message) throws InterruptedException {
        semaphoreProduce.takeSemaphore();
        this.buf = message;
        semaphoreConsume.giveSemaphore();
    }

    String take() throws InterruptedException {
        semaphoreConsume.takeSemaphore();
        String result = this.buf;
        this.buf = null;
        semaphoreProduce.giveSemaphore();
        return result;
    }
}
*/