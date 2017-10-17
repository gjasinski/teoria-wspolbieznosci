package tw01;

import tw02.BinarySemaphore;

public class Buffer {
	String buf;
	
	private BinarySemaphore semaphoreProduce;// = new BinarySemaphore();
	private BinarySemaphore semaphoreConsume;
	boolean emptyBuffer = true;
	
	Buffer() throws InterruptedException{
		semaphoreProduce = new BinarySemaphore();
		semaphoreConsume = new BinarySemaphore();
		semaphoreProduce.giveSemaphore();
		semaphoreConsume.takeSemaphore();
	}
	public void put(String message) throws InterruptedException{
		semaphoreProduce.takeSemaphore();
		this.buf = message;
		semaphoreConsume.giveSemaphore();
	}
	
	public String take() throws InterruptedException{
		semaphoreConsume.takeSemaphore();
		System.out.println("take");
		String result = this.buf;
		this.buf = null;
		semaphoreProduce.giveSemaphore();
		return result;
	}


}
