package mmt;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;

public class Passenger implements Serializable {
	private int _id;
	private String _name;
	private ArrayList<Itinerary> _itineraries;
	private ArrayList<Itinerary> _preCommITs;
	private PassengerCard _passCategory;
	
	private static final long serialVersionUID = -8327352051240741915L;

	public Passenger(String name, int id) { 
		_name = name;
		_id = id;
		_itineraries = new ArrayList<Itinerary>();
		_preCommITs = new ArrayList<Itinerary>();
		_passCategory = new Normal(this, 0, Duration.ZERO, 0, 0);
	}

	public void commitItinerary(Itinerary i) {
		_itineraries.add(i);
		_passCategory.addItinerary(i);
	}
	
	public int getId() {
		return _id;
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
	
	public Itinerary getItinerary(int id) {
		return _itineraries.get(id);

	}

	public ArrayList<Itinerary> getItineraries() {
		return _itineraries;

	}
	
	public ArrayList<Itinerary> getLast10Itineraries() {
		ArrayList<Itinerary> _last10 = new ArrayList<Itinerary>();
		ReverseIteratorTo10 it = new ReverseIteratorTo10();
		
		while (it.hasNext()) {
			_last10.add(it.next());
		}
		return _last10;
	}
	
	public int getSizeOfPreCommITs() {
		return _preCommITs.size();
	}
	
	public void setCategory(PassengerCard card) {
		_passCategory = card;
	}
	
	@Override
	public String toString() {
		return _id + "|" + _name + "|" + _passCategory.toString() + "|" + _passCategory.getTotalItineraries() + "|" + _passCategory.toStringTotalPaid() + "|" + _passCategory.toStringTimeSpent();
	}
	
	public String toStringItineraries() {
		String text = "";
		if (_passCategory.getTotalItineraries() > 0) {
			text += "== Passageiro " + _id + ": " + _name + " ==\n";
			text += toStringItineraries(_itineraries);
		}
		return text;
	}

	public String toStringItineraries(ArrayList<Itinerary> itineraries) {
		String text = "";
		if (!itineraries.isEmpty()) {
			itineraries.sort(new DateItineraryComparator());
			for (Itinerary i: itineraries) {
				i.setOrder(itineraries.indexOf(i)+1);
				text += "\n" + i.toString() + "\n";
			}
		}
		return text;
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
        public void remove() {
        	throw new UnsupportedOperationException();
        }
    }

	public void chooseItinerary(int itineraryNumber) {
		commitItinerary(_preCommITs.get(itineraryNumber - 1));
		resetPreCommITs();
	}

	public void addPreCommIT(Itinerary it) {
		_preCommITs.add(it);
	}

	public String toStringPreCommIT() {
		return toStringItineraries(_preCommITs);
	}

	public void resetPreCommITs() {
		_preCommITs = new ArrayList<Itinerary>();		
	}
	
}
