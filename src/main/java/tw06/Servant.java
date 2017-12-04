package tw06;

class Servant {
    private String buffer;
    private int bufferSize;

    Servant(int bufferSize) {
        this.bufferSize = bufferSize;
        this.buffer = "";
    }

    void putToBuffer(String data) {
        this.buffer += data;
    }

    String getFromBuffer(int size) {
        String result = this.buffer.substring(0, size);
        this.buffer = this.buffer.substring(size);
        return result;
    }

    boolean canITakeData(int sizeToTake) {
        return this.buffer.length() >= sizeToTake;
    }

    boolean canIPutData(int sizeToPut) {
        return this.buffer.length() + sizeToPut <= bufferSize;
    }
}
