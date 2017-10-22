package tw02.task2;

class HypermarketSimulator {
    private CountingSemaphore countingSemaphore;

    HypermarketSimulator(int numberOfTrolleys) {
        this.countingSemaphore = new CountingSemaphore(numberOfTrolleys);
    }

    int takeTrolley() throws InterruptedException {
        return this.countingSemaphore.takeSemaphore();
    }

    int giveBackTrolley() throws InterruptedException {
        return this.countingSemaphore.giveSemaphore();
    }
}
