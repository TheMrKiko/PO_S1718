package mmt;

public class ServiceFromDeparture implements ServiceSeletor {
		public boolean matches(Service s, String station) {
			String departureStation = s.getServiceDepartureStation();
			return departureStation.equals(station);
		}
}
