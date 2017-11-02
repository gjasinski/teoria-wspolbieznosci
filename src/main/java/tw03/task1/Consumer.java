package tw03.task1;


class Consumer implements Runnable {
    private BoundedBuffer buffer;
    private int max;

    Consumer(BoundedBuffer buffer, int max) {
        this.buffer = buffer;
        this.max = max;
    }

    public void run() {

        for (int i = 0; i < this.max; i++) {
            String message;
            try {
                message = (String) buffer.take();
                System.out.println(message);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}