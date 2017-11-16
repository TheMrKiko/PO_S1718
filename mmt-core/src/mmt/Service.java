package mmt;

import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public class Service {
    private int _id;
    private double _price;
    private TreeMap<String, Station> _stations;
	
    public Service(int id, double price, List<Station> ls) {
        _stations = new TreeMap<String, Stations>(new COMPARE_STATION_BY_HOUR() );
        _id = id;
        _price = price;
        for(station s: ls) {
            _stations.put(s.getName(), s);
        }
    }
    
    public Map<String,Station> getStations() {
        return _stations;
    }
    
    //GETTERS
	public int getServiceID() {return _id;}
	
	public double getServicePrice() {return price;}
	
    public Station getServiceDeparture() {
        return _stations.firstKey();
		
    }

    public Station getServiceArrival(){
        return _stations.lastKey();
    }
	
	
	//COMPARATORS
    public final  Comparator<Service> ARRIVAL_TIME_CMP = new ArrivalTimeComparator();
    
    public final Comparator<Service> DEPARTURE_TIME_CMP = new DepartureTimeComparator();
    
    public final Comparator<Station> COMPARE_STATION_BY_HOUR = new CompareStationByHour();
    
    public class CompareStationByHour implements Comparator<Station> {
        public int compare(Station station1, Station station2, int ServiceID) {
            return station1.getLocalTimeInService(ServiceID).compareTo(station2.getLocalTimeInService(ServiceID));
        }
    }
    
    private class ArrivalTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            return service1.getServiceArrival() - service2.getServiceArrivl();
        }
    }

    private class DepartureTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            return service1.getServiceArrivaDeparture() - service2.getServiceDeparture();
        }
    }
    
}
