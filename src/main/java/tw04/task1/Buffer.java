package tw04.task1;

public class Buffer {
    private final BufferCell[] bufferCell;
    private final int bufferSize;

    Buffer(int bufferSize) {
        this.bufferSize = bufferSize;
        this.bufferCell = new BufferCell[bufferSize];
        for(int i = 0; i < bufferSize; i++){
            this.bufferCell[i] = new BufferCell();
        }
    }

    int getResource(int resourceId, int id) throws InterruptedException {
        return this.bufferCell[resourceId].getResource(id);
    }

    void setResource(int resourceId, int newValue){
        this.bufferCell[resourceId].setResource(newValue);
    }

    void releaseResource(int resourceId, int id){
        this.bufferCell[resourceId].releaseResource(id);
    }
}
