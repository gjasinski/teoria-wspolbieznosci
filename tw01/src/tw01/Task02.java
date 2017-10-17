package tw01;

public class Task02 {
	public static void main(String[] main){
		Buffer buf;
		try {
			buf = new Buffer();
		
		Consumer c1 = new Consumer(buf, 500);
		Consumer c2 = new Consumer(buf, 500);
		Producer p1 = new Producer(buf, 1000);
		
		Thread t1 = new Thread(c1);
		Thread t3 = new Thread(c2);
		Thread t2 = new Thread(p1);

		t1.start();
		t2.start();
		t3.start();
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		
		try{
		
			}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
	}

}
