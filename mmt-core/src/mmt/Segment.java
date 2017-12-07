package mmt;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
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

	@Override
	public String toString() {
		String text = "Serviço #" + getService().getServiceId() + " @ " + String.format(Locale.UK, "%.2f", getPrice());
		TreeMap<LocalTime, Station> stationsBetween = getService().getStationsBetween(_depStation, _arrStation);
		
		/*for (Map.Entry<LocalTime, Station> s: stationsBetween.entrySet() ) {
			text += "\n" + s.getKey().toString() + " " + s.getValue().getName();
		}*/
		for (LocalTime t: stationsBetween.keySet() ) {
			text += "\n" + t.toString() + " " + getService().getServiceStationAtTime(t).getName();
		}
		return text;
	}
}