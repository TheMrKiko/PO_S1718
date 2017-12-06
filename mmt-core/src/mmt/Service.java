package mmt;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.util.TreeMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Service implements Serializable {
	private int _id;
    private double _price;
    private Duration _duration;

    private TreeMap<LocalTime, Station> _stations;

	private static final long serialVersionUID = 3979509502530686206L;

    public Service(int id, double price) {
        _stations = new TreeMap<LocalTime, Station>();
        _id = id;
        _price = price;
        _duration = Duration.between(this.getServiceArrivalTime(), this.getServiceDepartureTime());
    }

    public void addStationtoService(LocalTime time, Station s) {
        _stations.put(time, s);
    }

    public TreeMap<LocalTime, Station> getStations() {
        return _stations;
    }
    
    public boolean hasStation(Station s) {
    	return _stations.containsValue(s);
    }


	/*
	 *
	 * SERVICE DEPARTURE
	 */

    public Station getServiceDepartureStation() {
        return getServiceStationAtTime(getServiceDepartureTime());
    }

    public LocalTime getServiceDepartureTime() {
        return _stations.firstKey();
    }


	/*
	 *
	 * SERVICE ARRIVAL
	 */

    public Station getServiceArrivalStation() {
    	return getServiceStationAtTime(getServiceArrivalTime());
    }

    public LocalTime getServiceArrivalTime() {
        return _stations.lastKey();
    }


	/*
	 *
	 * GETTERS
	 */

    
	public int getServiceId() {
		return _id;
	}

	public Duration getDuration() {
		return _duration;
	}

	public double getServicePrice() {
		return _price;
	}
	
	public Station getServiceStationAtTime(LocalTime time) {
    	return _stations.get(time);
    }
	
	public LocalTime getServiceTimeAtStation(Station s) {
		return s.getTimeOfService(this);
	}

	@Override
	public String toString() {
		String text = "Serviço #" + _id + " @ " + String.format(Locale.UK, "%.2f", _price);
		Set<Map.Entry<LocalTime, Station>> entries = _stations.entrySet();
		for(Map.Entry<LocalTime, Station> e: entries ) {
			text += "\n" + e.getKey().toString() + " " + e.getValue().getName();
		}
		return text;
	}
    
}
