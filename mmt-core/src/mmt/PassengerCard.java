package mmt;

import java.io.Serializable;

public abstract class PassengerCard implements Serializable {
	private static final long serialVersionUID = -7014708191813229635L;
	protected Passenger _pass;
 
    public PassengerCard(Passenger pass) { _pass = pass; }
 
    //public abstract void updateCategory();
 
    public String status() {
    	return getClass().getName();
    }
  }
