package mmt;

import java.io.Serializable;

public class ServiceToArrival implements ServiceSeletor, Serializable {
	
	private static final long serialVersionUID = -8141324637176646777L;

		public boolean matches(Service s, String station) {
			String arrivalStation = s.getServiceArrivalStation();
			return arrivalStation.equals(station);
		}
	}
