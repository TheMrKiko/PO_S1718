package mmt;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Station implements Serializable {

	private static final long serialVersionUID = 950443075994161568L;

	private String _name;
	private TreeMap<LocalTime, ArrayList<Service>> _departures;

	public Station(String name) {
		_name = name;
		_departures = new TreeMap<LocalTime, ArrayList<Service>>();
	}
	
	public void addService(LocalTime time, Service service) {
		if(_departures.get(time) != null) {
			ArrayList<Service> listToPut = _departures.get(time);
			listToPut.add(service);
			_departures.put(time, listToPut);	
		}
		else {
			ArrayList<Service> listToPut = new ArrayList<Service>();
			listToPut.add(service);
			_departures.put(time, listToPut);
		}
	}

	public String getName() {
		return _name;
	}

	public TreeMap<LocalTime, ArrayList<Service>> getDepartures() {
		return _departures;
	}
	
	public ArrayList<Service> getServicesAfterTime(LocalTime tFirst) {
		ArrayList<Service> servicesAfter = new ArrayList<Service>();
		for (LocalTime t : _departures.keySet()) {
			if (tFirst.compareTo(t) <= 0 ) {
				servicesAfter.addAll(_departures.get(t));
			}
		}
		return servicesAfter;
	}

	public LocalTime getTimeOfService(Service s) {
		LocalTime l = null;
		for (Entry<LocalTime, ArrayList<Service>> e: _departures.entrySet() ) {
			if (e.getValue().contains(s)) {
				l = e.getKey();
				break;
			}
		}
		return l;
	}
	
	@Override
	public String toString() {
		return _name;
	}
}
