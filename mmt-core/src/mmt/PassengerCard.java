package mmt;

import java.io.Serializable;
import java.time.Duration;
//import java.time.LocalTime;
import java.util.Locale;

public abstract class PassengerCard implements Serializable {
	
	private static final long serialVersionUID = -7014708191813229635L;
	
	protected Passenger _pass;
	private String _categoryName;
	private int _totalItineraries = 0;
	private Duration _timeSpent = Duration.ZERO;
	private double _totalPaid = 0;
	private double _last10Paid = 0;
	
	private final long MINUTES_PER_HOUR = 60;
 
    public PassengerCard(Passenger pass) {
    	_pass = pass;
    }
    
    //public abstract void updateCategory();
 
    public String status() {
    	return _categoryName;
    }
    
    /**
     * GETTERS
     *
     */
    
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

	public void addItinerary() {
		_totalItineraries++;
		
	}
	
  }
