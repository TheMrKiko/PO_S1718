package mmt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

public class DateItineraryComparator implements Comparator<Itinerary>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8134199415336132244L;

	@Override
	public int compare(Itinerary itinerary1, Itinerary itinerary2) {
		LocalDate date1 = itinerary1.getDate();
		LocalDate date2 = itinerary2.getDate();
		return date1.compareTo(date2) == 0 ? itinerary1.compareTo(itinerary2) : date1.compareTo(date2);
	}

}
