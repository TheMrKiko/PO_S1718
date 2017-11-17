package mmt;

public class ServiceToArrival implements ServiceSeletor {
		public boolean matches(Service s, String station) {
			String arrivalStation = s.getServiceArrivalStation();
			return arrivalStation.equals(station);
		}
	}
