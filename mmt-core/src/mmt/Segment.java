package mmt;

import java.io.Serializable;
import java.time.Duration;

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
		Duration dur = Duration.between(_service.getTimeAtStation(_depStation), _service.getTimeAtStation(_arrStation));
		return _service.getServicePrice() * dur.toMinutes() / _service.getDuration().toMinutes();
	}
}
