package mmt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

//TODO station existente, stsation classe

//import mmt.exceptions.BadDateSpecificationException;
import mmt.exceptions.BadEntryException;
//import mmt.exceptions.BadTimeSpecificationException;
import mmt.exceptions.ImportFileException;
//import mmt.exceptions.InvalidPassengerNameException;
//import mmt.exceptions.NoSuchDepartureException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NoSuchServiceIdException;
import mmt.exceptions.NoSuchStationNameException;
//import mmt.exceptions.NoSuchItineraryChoiceException;
import mmt.exceptions.NonUniquePassengerNameException;

/**
 * A train company has schedules (services) for its trains and passengers that
 * acquire itineraries based on those schedules.
 */
public class TrainCompany implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 201708301010L;

	private TreeMap<Integer, Service> _services = new TreeMap<Integer, Service>();
	private TreeMap<String, Station> _stations = new TreeMap<String, Station>();
	private ArrayList<Passenger> _passengers = new ArrayList<Passenger>();
	private int _totalpassengers = 0;

	/*
	 * FIXME add methods for registerPassenger, changePassengerName
	 * searchItineraries, commitItinerary
	 */

	/*
	 *
	 * PASSENGERS
	 */

	/**
	 * @param name
	 *            of the new passenger
	 * @throws NonUniquePassengerNameException
	 */
	public void registerPassenger(String name) throws NonUniquePassengerNameException {
		checkDuplicatePassengerName(name);
		_passengers.add(new Passenger(name, _totalpassengers++));
	}

	/**
	 * @param name
	 *            to check if is already in use
	 * @throws NonUniquePassengerNameException
	 *             if name in use
	 */
	public void checkDuplicatePassengerName(String name) throws NonUniquePassengerNameException {
		for (Passenger p : _passengers) {
			if (p.getName().equals(name)) {
				throw new NonUniquePassengerNameException(name);
			}
		}
	}

	/**
	 * @param newName
	 *            for the passenger
	 * @param id
	 *            of the passenger
	 * @throws NonUniquePassengerNameException
	 *             if name in use
	 * @throws NoSuchPassengerIdException
	 */
	public void updatePassengerName(String newName, int id)
			throws NonUniquePassengerNameException, NoSuchPassengerIdException {
		checkDuplicatePassengerName(newName);
		getPassengerById(id).setName(newName);
	}

	/*
	 *
	 * IMPORT
	 */

	/**
	 *
	 * Imports objects from a formatted text file
	 *
	 * @param filename
	 *            to read from
	 * @throws ImportFileException
	 */
	public void importFile(String filename) throws ImportFileException {
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\\|");
				registerFromFields(fields);
			}
			reader.close();
		} catch (FileNotFoundException | BadEntryException e) {
			throw new ImportFileException(e);
		} catch (IOException e) {
			throw new ImportFileException(e);
		}
	}

	/**
	 *
	 * Matches a set of fields with a type of object and registers that object
	 *
	 * @param fields
	 *            to transform in each type
	 * @throws BadEntryException
	 */
	public void registerFromFields(String[] fields) throws BadEntryException {
		Pattern patService = Pattern.compile("^(SERVICE)");
		Pattern patPassenger = Pattern.compile("^(PASSENGER)");
		Pattern parItinerary = Pattern.compile("^(ITINERARY)");

		if (patService.matcher(fields[0]).matches()) {
			registerServiceFromFields(fields);
		} else if (patPassenger.matcher(fields[0]).matches()) {
			registerPassengerFromFields(fields);
		} else if (parItinerary.matcher(fields[0]).matches()) {
			registerItineraryFromFields(fields);
		} else {
			throw new BadEntryException(fields[0]);
		}
	}

	/*
	 *
	 * REGISTER OBJECTS FROM FIELDS
	 */

	/**
	 * @param fields
	 *            of object Passenger
	 */
	public void registerPassengerFromFields(String[] fields) {
		_passengers.add(new Passenger(fields[1], _totalpassengers++));
	}

	/**
	 * @param fields
	 *            of object Service
	 */
	public void registerServiceFromFields(String[] fields) {
		Service newService = new Service(Integer.parseInt(fields[1]), Double.parseDouble(fields[2]));
		for (int i = 3; i < fields.length; i += 2) {
			newService.addStation(LocalTime.parse(fields[i]), fields[i + 1]);
		}
		_services.put(newService.getServiceId(), newService);
	}

	/**
	 * @param fields
	 *            of object Itinerary
	 */
	public void registerItineraryFromFields(String[] fields) {
		Itinerary newItinerary = new Itinerary(LocalDate.parse(fields[2]));
		String[] segFields; Segment s;
		for (int i = 3; i < fields.length; i++) {
			segFields = fields[i].split("\\/");
			s = new Segment(_services.get(Integer.parseInt(segFields[0])), getStation(segFields[1]), getStation(segFields[1]));
			newItinerary.addSegment(s); 
		}
		Passenger p = _passengers.get(Integer.parseInt(fields[0]));
		p.addItinerary(newItinerary);
	}

	/*
	 *
	 * GETTERS
	 */

	/**
	 * @param id
	 *            of the passenger
	 * @return passenger with that id
	 * @throws NoSuchPassengerIdException
	 *             if id out of range
	 */
	public Passenger getPassengerById(int id) throws NoSuchPassengerIdException {
		if (id >= _totalpassengers || id < 0) {
			throw new NoSuchPassengerIdException(id);
		}
		return _passengers.get(id);
	}

	/**
	 * @param id
	 *            of service
	 * @return
	 * @throws NoSuchServiceIdException
	 *             if no service with that id
	 */
	public Service getServiceById(int id) throws NoSuchServiceIdException {
		Service serv = _services.get(id);
		if (serv == null) {
			throw new NoSuchServiceIdException(id);
		}
		return serv;
	}

	/**
	 *
	 * Uses a selector to filter all services by a station type
	 *
	 * @param selector
	 *            of type of station
	 * @param station
	 *            in specifically
	 * @return the filtred list
	 * @throws NoSuchStationNameException
	 *             if no station found
	 *
	 */
	ArrayList<Service> getServiceByStation(ServiceSeletor selector, String station) throws NoSuchStationNameException {
		ArrayList<Service> servicesFiltred = new ArrayList<Service>();
		List<Service> servicesList = new ArrayList<Service>(_services.values());
		if (getStation(station) == null) {
			throw new NoSuchStationNameException(station);
		}
		for (Service serv : servicesList)
			if (selector.matches(serv, station)) {
				servicesFiltred.add(serv);
			}
		return servicesFiltred;
	}

	/**
	 * @return all services
	 */
	
	public TreeMap<Integer, Service> getServices() {
		return _services;
	}
	
	public Station getStation(String name) {
		return _stations.get(name);
	}

	/*
	 *
	 * SETTERS
	 */

	/**
	 * @param services
	 *            to replace in TrainCompany
	 */
	public void setServices(TreeMap<Integer, Service> services) {
		_services = services;
	}

	/*
	 *
	 * TOSTRING
	 */

	/**
	 * @param id
	 *            of the passenger
	 * @return a string with the passenger information
	 * @throws NoSuchPassengerIdException
	 *             if no passenger with that id
	 */
	public String toStringPassengerById(int id) throws NoSuchPassengerIdException {
		return getPassengerById(id).toString();
	}

	/**
	 * @return a string with all passengers information
	 */
	public String toStringAllPassengers() {
		String text = "";
		for (Passenger p : _passengers) {
			text += p.toString() + "\n";
		}
		return text;
	}

	/**
	 * @param id
	 *            of the service
	 * @return a string with the service information
	 * @throws NoSuchServiceIdException
	 *             if no service with that id
	 */
	public String toStringServiceById(int id) throws NoSuchServiceIdException {
		return getServiceById(id).toString();
	}

	/**
	 * @param servicesList
	 *            with the services to convert to string
	 * @return of the services in string form
	 */
	public String toStringServices(ArrayList<Service> servicesList) {
		String text = "";
		for (Service s : servicesList) {
			text += s.toString() + "\n";
		}
		return text;
	}

	/**
	 * @return a string with all services information
	 */
	public String toStringAllServices() {
		ArrayList<Service> list = new ArrayList<Service>(_services.values());
		return toStringServices(list);
	}

	/**
	 * @param stationDeparture
	 *            of the service
	 * @return string with information of all services that departure from that
	 *         station
	 * @throws NoSuchStationNameException
	 *             if no station found
	 */
	public String toStringServicesFromDeparture(String stationDeparture) throws NoSuchStationNameException {
		return toStringServices(getServiceByStation(new ServiceFromDeparture(), stationDeparture));
	}

	/**
	 * @param stationArrival
	 *            of the service
	 * @return string with information of all services that arrive to that
	 *         station
	 * @throws NoSuchStationNameException
	 *             if no station found
	 */
	public String toStringServicesToArrival(String stationArrival) throws NoSuchStationNameException {
		return toStringServices(getServiceByStation(new ServiceToArrival(), stationArrival));
	}
}
