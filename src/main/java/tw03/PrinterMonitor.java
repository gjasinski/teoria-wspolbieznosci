package tw03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {
	Printer[] printers;
	Queue<Printer> printerQueue;
	final Lock lock = new ReentrantLock();
	final Condition notFull  = lock.newCondition(); 
	final Condition empty = lock.newCondition(); 
	
	PrinterMonitor(int numberOfPrinters){
		this.printerQueue = new LinkedList<Printer>();
		this.printers = new Printer[numberOfPrinters]; 
		for(int i = 0; i < numberOfPrinters; i++){
			Printer printer = new Printer(i);
			printers[i] = printer;
			this.printerQueue.add(printer);
		}
		
	}
	
	int reservePrinter() throws InterruptedException{
		lock.lock();
		try{
			while(printerQueue.size() == 0){
				empty.await();
			}
			Printer p = printerQueue.poll();
			return p.getId();
		}finally{
			lock.unlock();
		}
	}
	
	void releasePrinter(int printer){
		lock.lock();
		Printer p = printers[printer];
		printerQueue.add(p);
		lock.unlock();
	}
	
	void print(int id) throws InterruptedException{
		printers[id].print();
	}
}
