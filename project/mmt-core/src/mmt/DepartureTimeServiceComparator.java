package mmt;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Comparator;

/**
 * Compares the departure time from the first station, regardless of that
 * station
 *
 */
public class DepartureTimeServiceComparator implements Comparator<Service>, Serializable {

	private static final long serialVersionUID = -4272639283789213796L;

	@Override
	public int compare(Service service1, Service service2) {
		LocalTime departureTime1 = service1.getServiceDepartureTime();
		LocalTime departureTime2 = service2.getServiceDepartureTime();
		return departureTime1.compareTo(departureTime2);
	}
}
