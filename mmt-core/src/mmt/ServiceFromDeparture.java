package mmt;

import java.io.Serializable;

public class ServiceFromDeparture implements ServiceSeletor, Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = -9072117171408386995L;

		public boolean matches(Service s, String station) {
			String departureStation = s.getServiceDepartureStation();
			return departureStation.equals(station);
		}
}
