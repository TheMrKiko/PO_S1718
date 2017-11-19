package mmt;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Service implements Serializable {
	private int _id;
    private double _price;
    private String _firstStation = "";
    private String _lastStation = "";
    
    private LinkedHashMap<String, LocalTime> _stations;
    
	private static final long serialVersionUID = 3979509502530686206L;
    
    public Service(int id, double price) {
        _stations = new LinkedHashMap<String, LocalTime>();
        _id = id;
        _price = price;
    }

    public void addStation(LocalTime time, String name) {
        _stations.put(name,time);
    }

    public LinkedHashMap<String, LocalTime> getStations() {
        return _stations;
    }    

    
	/*
	 * 
	 * SERVICE DEPARTURE
	 */
	
    public String getServiceDepartureStation() {
        if (_firstStation == "") {
            _firstStation = getServiceFirstStation();
        }
        return _firstStation;
    }

    public LocalTime getServiceDepartureTime() {
        if(_firstStation == "") {
            _firstStation = getServiceFirstStation();
        }
        return _stations.get(_firstStation);
    }

    public String getServiceFirstStation() {
        return  _stations.keySet().iterator().next();
    }
    

	/*
	 * 
	 * SERVICE ARRIVAL
	 */
    
    public String getServiceArrivalStation() {
        if(_lastStation  == "") {
            _lastStation = getServiceLastStation();
        }
        return _lastStation;
    }

    public LocalTime getServiceArrivalTime() {
        if (_lastStation == "") {
            _lastStation = getServiceLastStation();
        }
        return _stations.get(_lastStation);
    }

    public String getServiceLastStation() {
        final Iterator<String> itr = _stations.keySet().iterator();
        String stationName = itr.next();
        while (itr.hasNext()) {
            stationName = itr.next();
        }
        return stationName;
    }
    
    
	/*
	 * 
	 * GETTERS
	 */
    
	public int getServiceId() {
		return _id;
	}

	public double getServicePrice() {
		return _price;
	}
    
	@Override
	public String toString() {
		String text = "Serviço #" + _id + " @ " + String.format(Locale.UK, "%.2f", _price);
		Set<Map.Entry<String, LocalTime>> entries = _stations.entrySet();
		for(Map.Entry<String, LocalTime> e: entries ) {
			text += "\n" + e.getValue().toString() + " " + e.getKey();
		}
		return text;
	}
    
	/*
	 * 
	 * COMPARATORS
	 */
	
    public final  Comparator<Service> ARRIVAL_TIME_CMP = new ArrivalTimeComparator();

    public final Comparator<Service> DEPARTURE_TIME_CMP = new DepartureTimeComparator();

    /**
     * Compares the arrival time to the final station, regardless of that station 
     *
     */
    private class ArrivalTimeComparator implements Comparator<Service>, Serializable {
    	
		private static final long serialVersionUID = -3748406259119112704L;

		public int compare(Service service1, Service service2) {
            LocalTime arrivalTime1 = service1.getServiceArrivalTime();
            LocalTime arrivalTime2 = service2.getServiceArrivalTime();
            return arrivalTime1.compareTo(arrivalTime2);
        }
    }

    /**
     * Compares the departure time from the first station, regardless of that station
     *
     */
    private class DepartureTimeComparator implements Comparator<Service>, Serializable {
    	
		private static final long serialVersionUID = -4272639283789213796L;

		public int compare(Service service1, Service service2) {
            LocalTime departureTime1 = service1.getServiceDepartureTime();
            LocalTime departureTime2 = service2.getServiceDepartureTime();
            return departureTime1.compareTo(departureTime2);
        }
    }

}
