import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

//import static avi.quit;

class CheckHealth {

	static class Avi implements Runnable {
		private int quit = 0;
		public int getQuit(){
			return quit;
		}
		Scanner sc = new Scanner(System.in);

		@Override
		public void run() {
			String msg = "";
			while(!(msg.toLowerCase().equals("q"))) {
				try{
					msg = sc.nextLine();
				} catch(Exception e){
					//
				}
			}
			quit = 1;
		}
	}

	private static ArrayList<Target> servers = new ArrayList<>();

	private static Properties prop;
	private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
	private static int TIMEOUT = 5000;
	private static int DELAY_SESSION = 15;
	private static String DOMAINS_FILE_PATH = "domains.properties";

	public static void main(String[] args) throws InterruptedException, IOException {

		HealthConfig cfg = new HealthConfig();
		prop = cfg.getProperties();

		FORMATTER = DateTimeFormatter.ofPattern(prop.getProperty("formatter"));
		TIMEOUT = Integer.parseInt(prop.getProperty("timeout"));
		DELAY_SESSION = Integer.parseInt(prop.getProperty("delay"));
		DOMAINS_FILE_PATH = prop.getProperty("domains");

		FillDomains fill = new FillDomains();
		fill.fill(servers, DOMAINS_FILE_PATH);

		PingAction mainTask;
		int proc_cores = Runtime.getRuntime().availableProcessors();
                System.out.println("Available processors: " + proc_cores);
		ForkJoinPool pool = new ForkJoinPool(proc_cores);	// set values of parallelism

		Avi avi = new Avi();
		Thread mt = new Thread(avi);
		mt.start();

		while(true) {
			
			mainTask = new PingAction(servers, 0, servers.size(), TIMEOUT);
			System.out.printf( "%s - New session srarted (domains %d)%n", LocalDateTime.now().format(FORMATTER), servers.size() );
			pool.invoke(mainTask);
			System.out.printf(
				"%s - All servers checked. Will wait for %d seconds until next round%n",
				LocalDateTime.now().format(FORMATTER), DELAY_SESSION );
			Thread.sleep(DELAY_SESSION * 1000);

//			mt.sleep(1);
			if(avi.getQuit() == 1) {
				System.out.println("Quiting...");
				break;
			}

		}

	}
}
