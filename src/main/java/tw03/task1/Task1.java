package tw03.task1;

class Task1 {
    private final static int NUMBER_OF_CONSUMERS = 10;
    private final static int PRODUCTS_PER_CONSUMER = 100;

    public static void main(String[] main) {
        BoundedBuffer buf;
        try {
            buf = new BoundedBuffer();

            for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
                Consumer c = new Consumer(buf, PRODUCTS_PER_CONSUMER);
                Thread t = new Thread(c);
                t.start();
            }
            Producer p1 = new Producer(buf, NUMBER_OF_CONSUMERS * PRODUCTS_PER_CONSUMER);

            Thread t2 = new Thread(p1);

            t2.start();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

}
