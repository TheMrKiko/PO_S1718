package mmt;

import java.io.Serializable;

public class ServiceToArrival implements ServiceSeletor, Serializable {
	
	private static final long serialVersionUID = -8141324637176646777L;

		@Override
		public boolean matches(Service s, Station station) {
			Station arrivalStation = s.getServiceArrivalStation();
			return arrivalStation.equals(station);
		}
	}
