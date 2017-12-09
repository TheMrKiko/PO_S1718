package mmt;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

public class Itinerary implements Serializable, Comparable<Itinerary> {
	
	private static final long serialVersionUID = -4604865736288785604L;
	
	private Double _price;
	private int _order;
	private LocalDate _date;
	private Duration _totalTime;
	private ArrayList<Segment> _segm;
	
	public Itinerary(LocalDate date) {
		_date = date;
		_segm = new ArrayList<Segment>();
	}

	public double calcPrice() {
		double price = 0; 
		for (Segment s : _segm) {
			price += s.getPrice();
		}
		return price;
	}
	
	public void addSegment(Segment s) {
		_segm.add(s);
	}
	
	public boolean isDirect() {
		return _segm.size() == 1;
	}
	
	public Duration getTotalTime() {
		if (_totalTime == null) {
			_totalTime = calcTime();
		}
		return _totalTime;
	}


	public Duration calcTime() {
		return Duration.between(getDepartureTime(), getArrivalTime());
	}

	public double getPrice() {
		if (_price == null) {
			_price = calcPrice();
		}
		return _price;
	}

	public LocalDate getDate() {
		return _date;
	}
	
	public LocalTime getDepartureTime() {
		return _segm.get(0).getDepartureStationTime();
	}
	
	public LocalTime getArrivalTime() {
		return _segm.get(_segm.size()-1).getArrivalStationTime();
	}
	
	public int getOrder() {
		return _order;
	}

	public void setOrder(int order) {
		_order = order;
	}
	
	@Override
	public String toString() {
		String text = "Itinerário " + _order + " para " + _date.toString() + " @ " + String.format(Locale.UK, "%.2f", getPrice());
		for(Segment s: _segm) {
			text += "\n" + s.toString();
		}
		return text;
	}

	@Override
	public int compareTo(Itinerary other) {
		if (other == null) {
			throw new NullPointerException();
		}
		return getDepartureTime().compareTo(other.getDepartureTime()) == 0 ?
				(getArrivalTime().compareTo(other.getArrivalTime()) == 0 ? 
						(_price.compareTo(other.getPrice()))
						: getArrivalTime().compareTo(other.getArrivalTime()))
				: getDepartureTime().compareTo(other.getDepartureTime());
	}
    
}
