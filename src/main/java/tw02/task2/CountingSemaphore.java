package tw02.task2;

class CountingSemaphore {
    private int freeSemaphores;
    private int numberOfSemaphores;

    CountingSemaphore(int numberOfSemaphores) {
        this.freeSemaphores = numberOfSemaphores;
        this.numberOfSemaphores = numberOfSemaphores;
    }

    synchronized int takeSemaphore() throws InterruptedException {
        while (freeSemaphores == 0) {
            this.wait();
        }
        this.freeSemaphores--;
        return this.freeSemaphores;
    }

    synchronized int giveSemaphore() throws InterruptedException {
        this.freeSemaphores++;
        if (this.freeSemaphores > this.numberOfSemaphores) {
            throw new IllegalStateException("Not valid state");
        }
        notify();
        return this.freeSemaphores;
    }

}
