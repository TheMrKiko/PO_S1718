package mmt;

public class Passenger {
	private int _id;
	private String _name;
	private PassengerCard passCategory = new Normal(this);
}
