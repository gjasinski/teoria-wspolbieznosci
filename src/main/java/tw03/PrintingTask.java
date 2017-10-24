package tw03;

import java.util.Random;

public class PrintingTask implements Runnable {
	PrinterMonitor printerMonitor;
	 Random generator = new Random();
	 
	PrintingTask(PrinterMonitor monitor){
		this.printerMonitor = monitor;
	}
	
	@Override
	public void run(){
		while(true){
			try {
				createTask();
				int printerId = this.printerMonitor.reservePrinter();
				this.printerMonitor.print(printerId);
				this.printerMonitor.releasePrinter(printerId);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	void createTask() throws InterruptedException{
		Thread.sleep(Math.abs(generator.nextInt())%1000);
	}
}
