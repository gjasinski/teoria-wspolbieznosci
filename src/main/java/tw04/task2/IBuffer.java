package tw04.task2;

public interface IBuffer {
    void put(String dataToPut) throws InterruptedException;
    String get(int sizeToGet) throws InterruptedException;
}
