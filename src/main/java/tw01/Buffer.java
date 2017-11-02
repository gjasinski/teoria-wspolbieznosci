package tw01;

public class Buffer {
    String buf;

    boolean emptyBuffer = true;

    public synchronized void put(String message) {
        while (!emptyBuffer) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.buf = message;
        emptyBuffer = false;
        notify();
    }

    public synchronized String take() {
        while (emptyBuffer) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        String result = this.buf;
        this.buf = null;
        emptyBuffer = true;
        notify();
        return result;
    }


}
