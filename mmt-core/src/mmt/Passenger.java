package mmt;

import java.io.Serializable;
import java.util.ArrayList;

public class Passenger implements Serializable {
	private int _id;
	private String _name;
	private PassengerCard _passCategory = new Normal(this);

	private ArrayList<Itinerary> _itineraries;
	
	private static final long serialVersionUID = -8327352051240741915L;

	public Passenger(String name, int id) {
		_name = name;
		_id = id;
		_itineraries = new ArrayList<Itinerary>();
	}

	public void commitItinerary(Itinerary i) {
		addItinerary(i);
	}
	
	public void addItinerary(Itinerary i) {
		_itineraries.add(i);
		getPassCategory().addItinerary(i);
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
	
	public PassengerCard getPassCategory() {
		return _passCategory;
	}

	public void setName(String newName) {
		_name = newName;
	}
	
	@Override
	public String toString() {
		return getId() + "|" + getName() + "|" + getPassCategory().toString() + "|" + getPassCategory().getTotalItineraries() + "|" + getPassCategory().toStringTotalPaid() + "|" + getPassCategory().toStringTimeSpent();
	}

	public int getId() {
		return _id;
	}
	
}
