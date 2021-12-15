import java.net.InetSocketAddress;

class Target {
	
	private InetSocketAddress addr;
	private int order;
	private double avg;
	private long cnt;

	Target(int order, InetSocketAddress addr) {
		this.order = order;
		this.addr = addr;
	}

	public InetSocketAddress getAddress() {
		return addr;
	}

	public int getOrder() {
		return order;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getAvg() {
		return avg;
	}

	public void setCnt(long cnt) {
		this.cnt = cnt;
	}

	public long getCnt(){
		return cnt;
	}

	@Override
	public String toString() {
		return "Target ["
		+ "#" + order + ", "
		+ "addr " + addr + ", "
		+ "cnt " + cnt + ", " 
		+ "avg " + avg + " ]";
	}

}
