package mmt;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
//import java.time.LocalTime;
import java.util.Locale;

public abstract class PassengerCard implements Serializable {
	
	private static final long serialVersionUID = -7014708191813229635L;
	
	protected Passenger _pass;
	private String _categoryName;
	protected int _totalItineraries;
	protected Duration _timeSpent;
	protected double _totalPaid;
	protected double _last10Paid;
	
	private final long MINUTES_PER_HOUR = 60;
    
    public PassengerCard(Passenger pass, int totalItineraries, Duration timeSpent, double totalPaid, double last10Paid) {
		_pass = pass;
		_totalItineraries = totalItineraries;
		_timeSpent = timeSpent;
		_totalPaid = totalPaid;
		_last10Paid = last10Paid;
	}
    
    public void addItinerary(Itinerary i) {
		i.setOrder(_totalItineraries++);
		_totalPaid += i.getPrice()*getDiscount();
		_timeSpent = _timeSpent.plus(i.getTotalTime());
		_last10Paid = updateLast10Paid();
		updateCategory();
	}
    
	public abstract void updateCategory();

	public double updateLast10Paid() {
		ArrayList<Itinerary> _last10 = _pass.getLast10Itineraries();
		
		double amount = 0;
		for (Itinerary i : _last10) {
			amount += i.getPrice();
		}
		return amount;
	}
    
    /**
     * GETTERS
     *
     */
    
	public abstract String status();
    
    public abstract double getDiscount();
	
    public double getTotalPaid() {
    	return _totalPaid;
    }

	public Duration getTimeSpent() {
		return _timeSpent;
	}

	public int getTotalItineraries() {
		return _totalItineraries;
	}
	
	public double getLast10Paid() {
		return _last10Paid;
	}
	
    /**
     * TOSTRING
     *
     */

	@Override
	public String toString() {
		return status();
	}
	
	public String toStringTimeSpent() {
		return String.format("%02d", _timeSpent.toHours()) + ":" + String.format("%02d", _timeSpent.toMinutes() % MINUTES_PER_HOUR);
	}

	public String toStringTotalPaid() {
		return String.format(Locale.UK, "%.2f", _totalPaid);
	}
	
  }
