package tw01;

public class Task02 {
	public static void main(String[] main){
		Buffer buf = new Buffer();
		Consumer c1 = new Consumer(buf, 1000);
		Producer p1 = new Producer(buf, 1000);
		
		Thread t1 = new Thread(c1);
		Thread t2 = new Thread(p1);

		t1.start();
		t2.start();
	}

}
