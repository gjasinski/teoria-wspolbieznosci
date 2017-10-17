package tw02;

public class NotBinarySemaphore {
	private int freeSmaphores;

	public NotBinarySemaphore(){
		this.freeSmaphores = 10;
	}
	
	public synchronized void takeSemaphore() throws InterruptedException{
		while(freeSmaphores == 0){
				this.wait();
		}
		this.freeSmaphores--;;
	}
	
	public synchronized void giveSemaphore() throws InterruptedException{
		this.freeSmaphores++;
		if(this.freeSmaphores > 10){
			throw new IllegalStateException("Not valid state");
		}
		notify();
	}

}
