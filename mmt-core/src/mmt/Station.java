package mmt;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public class Station implements Serializable {

	private static final long serialVersionUID = 950443075994161568L;

	private String _name;
	private TreeMap<LocalTime, Integer> _departures;

	public Station(String name) {
		_name = name;
		_departures = new TreeMap<LocalTime, Integer>();
	}
	
	public void addService(LocalTime time, int serviceID) {
		_departures.put(time, serviceID);
	}

	public String getName() {
		return _name;
	}

	public TreeMap<LocalTime, Integer> getDepartures() {
		return _departures;
	}

	public LocalTime getTimeOfService(Service s) {
		LocalTime l = null;
		for (Map.Entry<LocalTime, Integer> e: getDepartures().entrySet() ) {
			
			if (e.getValue().equals(s.getServiceId())) {
				l = e.getKey();
				break;
			}
			
		}
		return l;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
