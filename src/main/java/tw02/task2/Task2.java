package tw02.task2;

class Task2 {
    private static final int HYPERMARKET_NUMBER_OF_TROLLEYS = 100;
    private static final int NUMBER_OF_CUSTOMERS = 1000;

    public static void main(String[] args) {
        HypermarketSimulator hypermarketSimulator = new HypermarketSimulator(HYPERMARKET_NUMBER_OF_TROLLEYS);
        for(int i = 0; i < NUMBER_OF_CUSTOMERS; i++){
            Customer customer = new Customer(i, hypermarketSimulator);
            Thread thread = new Thread(customer);
            thread.start();
        }
    }
}
