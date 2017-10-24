package tw03;

public class Task2 {
	public static void main(String[] main){
		PrinterMonitor printerMonitor = new PrinterMonitor(10);
		int users = 30;
		
		for(int i = 0; i < users; i++){
			PrintingTask task = new PrintingTask(printerMonitor);
			Thread t = new Thread(task);
            t.start();
		}
	}

}
