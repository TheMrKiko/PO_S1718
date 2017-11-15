package mmt;

public abstract class PassengerCard {
    protected Passenger _pass;
 
    public PassengerCard(Passenger pass) { _pass = pass; }
 
    public abstract void click();
 
    public void timeout()  { }
    public void complete() { }
 
    public String status() { return getClass().getName(); }
  }
