package mmt;

import java.io.Serializable;
import java.util.ArrayList;

public class Passenger implements Serializable {
	private int _id;
	private String _name;
	private PassengerCard passCategory = new Normal(this);

	private ArrayList<Itinerary> _itineraries;
	
	private static final long serialVersionUID = -8327352051240741915L;

	public Passenger(String name, int id) {
		_name = name;
		_id = id;
	}

	public void commitItinerary(int itineraryId) {
		// TODO commitItinerary 
	}

	public Itinerary getItinerary(int id) {
		return _itineraries.get(id);

	}

	public ArrayList<Itinerary> getItineraries() {
		return _itineraries;

	}
	public String getName() {
		return _name;
	}

	public void setName(String newName) {
		_name = newName;
	}

	@Override
	public String toString() {
		return _id + "|" + _name + "|" + passCategory.toString() + "|" + passCategory.getTotalItineraries() + "|" + String.format("%.2f", passCategory.getTotalPaid()) + "|" + passCategory.toStringTimeSpent();
	}

	public int getId() {
		return _id;
	}
	
	

}
