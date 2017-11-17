package mmt;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;

public class Passenger implements Serializable {
	private int _id;
	private String _name;
	private PassengerCard passCategory = new Normal(this);
	private int _totalItineraries = 0;
	private Duration _timeSpent;
	private double _last10Paid = 0;
	private ArrayList<Itinerary> _itineraries;
	
	private static final long serialVersionUID = -8327352051240741915L;


	public Passenger(String name, int id) {
		_name = name;
		_id = id;
	}

	public void commitItinerary(int itineraryId) {

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

}
