package mmt;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Station implements Serializable {

	private static final long serialVersionUID = 950443075994161568L;

	private String _name;
	private TreeMap<LocalTime, Service> _departures;

	public Station(String name) {
		_name = name;
		_departures = new TreeMap<LocalTime, Service>();
	}
	
	public void addService(LocalTime time, Service service) {
		_departures.put(time, service);
	}

	public String getName() {
		return _name;
	}

	public TreeMap<LocalTime, Service> getDepartures() {
		return _departures;
	}
	
	public String toStringStationDeps() {
		String text = _name;
		for (LocalTime t: _departures.keySet()) {
			text += "-" + _departures.get(t).getServiceId();
		}
		return text;
	}
	
	public ArrayList<Service> getServicesAfterTime(LocalTime tFirst) {
		ArrayList<Service> servicesAfter = new ArrayList<Service>();
		for (LocalTime t : _departures.keySet()) {
			if (tFirst.compareTo(t) <= 0 ) {
				servicesAfter.add(_departures.get(t));
			}
		}
		return servicesAfter;
	}

	public LocalTime getTimeOfService(Service s) {
		LocalTime l = null;
		for (Map.Entry<LocalTime, Service> e: _departures.entrySet() ) {
			if (e.getValue().getServiceId() == s.getServiceId()) {
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
