package mmt;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.util.TreeMap;

public class Segment implements Serializable {

	private static final long serialVersionUID = 1421996333699727168L;
	private Service _service;
	private Station _depStation;
	private Station _arrStation;
	
	public Segment(Service service, Station depStation, Station arrStation) {
		_service = service;
		_depStation = depStation;
		_arrStation = arrStation;
	}

	public double getPrice() {
		Duration dur = getDuration();
		return _service.getServicePrice() * dur.toMinutes() / _service.getDuration().toMinutes();
	}
	
	public Duration getDuration() {
		return Duration.between(_service.getServiceTimeAtStation(_depStation), _service.getServiceTimeAtStation(_arrStation));
	}
	
	public Service getService() {
		return _service;
	}
	
	public Station getDepartureStation() {
		return _depStation;
	}
	
	public LocalTime getDepartureStationTime() {
		return _depStation.getTimeOfService(_service);
	}

	public Station getArrivalStation() {
		return _arrStation;
	}
	
	public LocalTime getArrivalStationTime() {
		return _arrStation.getTimeOfService(_service);
	}

	@Override
	public String toString() {
		String text = "Serviço #" + _service.getServiceId() + " @ " + String.format("%.2f", getPrice());
		TreeMap<LocalTime, Station> stationsBetween = _service.getStationsBetween(_depStation, _arrStation);
		
		for (LocalTime t: stationsBetween.keySet() ) {
			text += "\n" + t.toString() + " " + _service.getServiceStationAtTime(t).toString();
		}
		return text;
	}
}