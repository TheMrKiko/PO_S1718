package mmt;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Comparator;

/**
 * Compares the arrival time to the final station, regardless of that station
 *
 */
public class ArrivalTimeServiceComparator implements Comparator<Service>, Serializable {

	private static final long serialVersionUID = -3748406259119112704L;

	@Override
	public int compare(Service service1, Service service2) {
		LocalTime arrivalTime1 = service1.getServiceArrivalTime();
		LocalTime arrivalTime2 = service2.getServiceArrivalTime();
		return arrivalTime1.compareTo(arrivalTime2);
	}
}
