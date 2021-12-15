import java.util.*;
import java.util.concurrent.*;

/**
 * This class illustrates how to create a ForkJoinTask that does not return
 * a result.
 * @author camel
 */
public class PingAction extends RecursiveAction {

	private ArrayList<Target> arrayList;
	private static final int THRESHOLD = 4;
	private int start;
	private int end;
	private int tm;

	public PingAction(ArrayList<Target> array, int start, int end, int timeout) {
		this.arrayList = array;
		this.start = start;
		this.end = end;
		this.tm = timeout;
	}

	protected void compute() {
		if (end - start < THRESHOLD) {
			computeDirectly();
		} else {
			int middle = (end + start) / 2;

			PingAction subTask1 = new PingAction(arrayList, start, middle, tm);
			PingAction subTask2 = new PingAction(arrayList, middle, end, tm);

			invokeAll(subTask1, subTask2);
		}
	}

	protected void computeDirectly() {
		for (int i = start; i < end; i++) {
			OneAction one = new OneAction(arrayList.get(i), tm);
			one.exec();
		}
	}

}
