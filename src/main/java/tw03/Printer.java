package tw03;

public class Printer {
	private int id;
	
	Printer(int id){
		this.id = id;
	}
	
	void print() throws InterruptedException{
		System.out.println("Drukuje " + id);
		Thread.sleep(1000);
	}
	
	int getId(){
		return id;
	}

}
