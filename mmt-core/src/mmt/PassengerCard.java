package mmt;

import java.io.Serializable;
import java.time.Duration;

public abstract class PassengerCard implements Serializable {
	
	private static final long serialVersionUID = -7014708191813229635L;
	
	protected Passenger _pass;
	
	private int _totalItineraries = 0;
	private Duration _timeSpent;
	private double _last10Paid = 0;
 
    public PassengerCard(Passenger pass) {
    	_pass = pass;
    }
    
    //public abstract void updateCategory();
 
    public String status() {
    	return getClass().getName();
    }
  }
