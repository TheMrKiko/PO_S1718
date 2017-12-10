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
	public int compare(ArrayList<Segment> al1, ArrayList<Segment> al2) {
		
		if (al1.isEmpty()) return 1;
		if (al2.isEmpty()) return -1;
		
		Duration duration1 = Duration.ZERO;
		Duration duration2 = Duration.ZERO;
		for (Segment s : al1) {
			duration1 = duration1.plus(s.getDuration());
		}
		for (Segment s : al2) {
			duration2 = duration2.plus(s.getDuration());
		}
		return duration1.compareTo(duration2);
	}

}
