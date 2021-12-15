import java.net.*;

class OneAction {

	private Target server;
	private String hostStr;
	private int order;
	private boolean reachable = false;
	private Socket socket;
	private int timeout;
	private long start, duration;

	OneAction(Target target, int tm) {
		this.timeout = tm;
		this.server = target;
		this.socket = new Socket();
	}

	public void exec() {
//		socket = new Socket();
		hostStr = server.getAddress().getHostString();
		order = server.getOrder();
		start = System.currentTimeMillis();
		try {
			try {
				socket.connect(server.getAddress(), timeout);
			} finally {
				socket.close();
			}
			duration = (System.currentTimeMillis() - start);
			System.out.printf("%5d %5d ms %s%n", order, duration, hostStr );
			reachable = true;
		} catch (Exception e) {
			reachable = false;
		}

		if (!reachable) {
			System.out.println(String.format("\t%s was UNREACHABLE", hostStr));
                }

	}
}
