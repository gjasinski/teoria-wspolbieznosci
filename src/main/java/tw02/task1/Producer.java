package tw02.task1;

class Producer implements Runnable {
    private Buffer buffer;
    private int max;

    Producer(Buffer buffer, int max) {
        this.buffer = buffer;
        this.max = max;
    }

    public void run() {
        for (int i = 0; i < this.max; i++) {
            try {
                buffer.put("message " + i);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}