package mmt;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;

public class TotalTimeArrayListofSegmentsComparator implements Serializable, Comparator<ArrayList<Segment>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1753162820247144944L;

	@Override
	public int compare(ArrayList<Segment> listA, ArrayList<Segment> listB) {
		if (listA.isEmpty()) return 1;
		if (listB.isEmpty()) return -1;
		Duration duration1 = Duration.ZERO, duration2 = Duration.ZERO;
		
		for (Segment s : listA) {
			duration1 = duration1.plus(s.getDuration());
		}
		for (Segment s : listB) {
			duration2 = duration2.plus(s.getDuration());
		}
		return duration1.compareTo(duration2);
	}

}
