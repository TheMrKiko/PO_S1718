package mmt;

import java.time.LocalTime;
import java.util.Map;
import java.util.SortedMap;

public class Service {
	private int id;
	private double price;
	private SortedMap<String, Station> _stations;
	
	/*public Service(List<String> b) {
		
	}*/
	public Map<String,Station> getStations() {
		return _stations;
	}
	
	/*
	public Station getServiceDeparture() {
		return _stations.firstkey();
		
	}
	
	public final static Comparator<Service> ARRIVAL_TIME_CMP = new ArrivalTimeComparator();
	
	public final static Comparator<Service> DEPARTURE_TIME_CMP = new DepartureTimeComparator();*/
	
	
}
