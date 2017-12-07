package mmt;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;

public class Passenger implements Serializable {
	private int _id;
	private String _name;
	private PassengerCard _passCategory = new Normal(this, 0, Duration.ZERO, 0, 0);

	private ArrayList<Itinerary> _itineraries;
	
	private static final long serialVersionUID = -8327352051240741915L;

	public Passenger(String name, int id) {
		_name = name;
		_id = id;
		_itineraries = new ArrayList<Itinerary>();
	}

	public void commitItinerary(Itinerary i) {
		_itineraries.add(i);
		getPassCategory().addItinerary(i);
	}

	public Itinerary getItinerary(int id) {
		return _itineraries.get(id);

	}

	public ArrayList<Itinerary> getItineraries() {
		return _itineraries;

	}
	
	public ArrayList<Itinerary> getLast10Itineraries() {
		ArrayList<Itinerary> _last10 = new ArrayList<Itinerary>();
		ReverseIteratorTo10 it = new ReverseIteratorTo10();
		while(it.hasNext()) {
			_last10.add(it.next());
		}
		return _last10;
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
	
	public void setCategory(PassengerCard card) {
		_passCategory = card;
	}
	
	@Override
	public String toString() {
		return getId() + "|" + getName() + "|" + getPassCategory().toString() + "|" + getPassCategory().getTotalItineraries() + "|" + getPassCategory().toStringTotalPaid() + "|" + getPassCategory().toStringTimeSpent();
	}

	public int getId() {
		return _id;
	}
	
	//ITERATOR
	public class ReverseIteratorTo10 implements Iterator<Itinerary> {
        private int _index = _itineraries.size() - 1;
        private int _count = 10;
        public boolean hasNext() {
        	return _index >= 0 && _count > 0;
        }
        public Itinerary next() { 
        	_count --; 
        	return _itineraries.get(_index--);
        }
        public void remove() {};
    }
	
}
