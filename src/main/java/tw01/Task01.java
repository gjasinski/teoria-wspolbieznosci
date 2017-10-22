package tw01;

public class Task01 {
		public static void main(String[] args){
		MyThreadInc mythreadInc = new MyThreadInc();
		MyThreadDec mythreadDec = new MyThreadDec();
		Thread t1 = new Thread(mythreadInc);
		Thread t2 = new Thread(mythreadDec);
		t1.start();
		t2.start();
	try{	
		t1.join();
		t2.join();
	}
	catch(Exception ex){
		
	}
		System.out.println(NotAtomicInteger.i);
	}
}

class NotAtomicInteger{
	static volatile Object lock = new Object();
	static volatile int i = 0;
	
	static public void incI(){
		synchronized(lock){
			i++;
		}
	}
	
	static public void decI(){
		synchronized(lock){
			i--;
		}
	}
}

class MyThreadInc extends Thread {
	public void run() {
         for(int i = 0; i < 100000000; i++){
        	 NotAtomicInteger.incI();
         }
    }
}

class MyThreadDec extends Thread {
	public void run() {
         for(int i = 0; i < 100000000; i++){
        	 NotAtomicInteger.decI();
         }
    }
}