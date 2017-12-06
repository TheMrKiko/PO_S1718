package mmt;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
	
	@Override
	public String toString() {
		String text = "Serviço #" + _service.getServiceId() + " @ " + String.format(Locale.UK, "%.2f", getPrice());
		Set<Map.Entry<LocalTime, Station>> entries = _service.getStationsBetween(_depStation, _arrStation).entrySet();
		for(Map.Entry<LocalTime, Station> e: entries ) {
			text += "\n" + e.getKey().toString() + " " + e.getValue().getName();
		}
		return text;
	}
}