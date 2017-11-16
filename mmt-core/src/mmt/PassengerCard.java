package mmt;

public abstract class PassengerCard {
    protected Passenger _pass;
 
    public PassengerCard(Passenger pass) { _pass = pass; }
 
    //public abstract void updateCategory();
 
    public String status() {
    	return getClass().getName();
    }
  }
