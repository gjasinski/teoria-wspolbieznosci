package tw04.task1;

import java.util.Random;

public class Consument implements Runnable{
	private final Buffer buffer;
	private final int bufferSize;
	private Random generator = new Random();
	
	Consument(Buffer buffer, int bufferSize){
		this.buffer = buffer;
		this.bufferSize = bufferSize;
	}
	
	@Override
	public void run(){
		try {
		for(int resource = 0; resource < bufferSize; resource++){
			buffer.getResource(resource);
			Thread.sleep(Math.abs(generator.nextInt()) % 1000);
			System.out.println("Zjadlem " + resource);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
