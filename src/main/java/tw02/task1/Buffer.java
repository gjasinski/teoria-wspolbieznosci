package tw02.task1;

class Buffer {
    private String buf;

    private BinarySemaphore semaphoreProduce;// = new BinarySemaphore();
    private BinarySemaphore semaphoreConsume;

    Buffer() throws InterruptedException {
        semaphoreProduce = new BinarySemaphore();
        semaphoreConsume = new BinarySemaphore();
        semaphoreProduce.giveSemaphore();
        semaphoreConsume.takeSemaphore();
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
