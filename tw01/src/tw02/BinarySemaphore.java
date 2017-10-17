package tw02;


public class BinarySemaphore {
	private boolean semaphoreTaken;

	public BinarySemaphore(){
		this.semaphoreTaken = false;
	}
	
	public synchronized void takeSemaphore() throws InterruptedException{
		while(semaphoreTaken){
				this.wait();
		}
		this.semaphoreTaken = true;
	}
	
	public synchronized void giveSemaphore() throws InterruptedException{
		this.semaphoreTaken = false;
		notify();
	}
}
