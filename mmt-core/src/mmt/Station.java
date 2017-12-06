package mmt;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Station implements Serializable {

	private static final long serialVersionUID = 950443075994161568L;

	private String _name;
	private TreeMap<LocalTime, Integer> _departures;

	Station(String name) {
		_name = name;
		_departures = new TreeMap<LocalTime, Integer>();
	}
	
	public void addService(LocalTime time, int serviceID) {
		_departures.put(time, serviceID);
	}

	public String getName() {
		return _name;
	}

	public LocalTime getTimeOfService(Service s) {
		Set<Map.Entry<LocalTime, Integer>> entries = _departures.entrySet();
		LocalTime l = null;
		for (Map.Entry<LocalTime, Integer> e: entries ) {
			if (e.getValue() == s.getServiceId()) {
				l = e.getKey();
				break;
			}
			
		}
		return l;
	}
}
