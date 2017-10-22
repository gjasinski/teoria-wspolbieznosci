package tw02.task1;


class BinarySemaphore {
    private boolean semaphoreTaken;

    BinarySemaphore() {
        this.semaphoreTaken = false;
    }

    synchronized void takeSemaphore() throws InterruptedException {
        while (semaphoreTaken) {
            this.wait();
        }
        this.semaphoreTaken = true;
    }

    synchronized void giveSemaphore() throws InterruptedException {
        this.semaphoreTaken = false;
        notify();
    }
}
