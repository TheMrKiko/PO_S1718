package mmt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

import mmt.exceptions.BadDateSpecificationException;
import mmt.exceptions.BadEntryException;
import mmt.exceptions.BadTimeSpecificationException;
import mmt.exceptions.ImportFileException;
import mmt.exceptions.NoSuchItineraryChoiceException;
//import mmt.exceptions.InvalidPassengerNameException;
//import mmt.exceptions.NoSuchDepartureException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NoSuchServiceIdException;
import mmt.exceptions.NoSuchStationNameException;
import mmt.exceptions.NonUniquePassengerNameException;

/**
 * A train company has schedules (services) for its trains and passengers that
 * acquire itineraries based on those schedules.
 */
public class TrainCompany implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 201708301010L;

	private TreeMap<Integer, Service> _services;
	private TreeMap<String, Station> _stations;
	private ArrayList<Passenger> _passengers;
	private int _totalpassengers;

	public TrainCompany() {
		_stations = new TreeMap<String, Station>();
		_passengers = new ArrayList<Passenger>();
		_totalpassengers = 0;
	}

	public TrainCompany(TreeMap<Integer, Service> services) {
		this();
		_services = (services == null) ? new TreeMap<Integer, Service>()
				: services;
	}

	/*
	 *
	 * PASSENGERS
	 */

	/**
	 * @param name
	 *            of the new passenger
	 * @throws NonUniquePassengerNameException
	 *             if repeated passenger name
	 */
	public void registerPassenger(String name)
			throws NonUniquePassengerNameException {
		checkDuplicatePassengerName(name);
		_passengers.add(new Passenger(name, _totalpassengers++));
	}

	/**
	 * @param name
	 *            to check if is already in use
	 * @throws NonUniquePassengerNameException
	 *             if name in use
	 */
	public void checkDuplicatePassengerName(String name)
			throws NonUniquePassengerNameException {
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
	 * SEARCH
	 */

	/**
	 * 
	 * Parses arguments and searches all possible itineraries with a different
	 * departing service from to at time and date for that passenger
	 * 
	 * @param passengerId
	 *            of the passenger to regist
	 * @param departureStationString
	 *            of the itinerary
	 * @param arrivalStationString
	 *            of the itinerary
	 * @param departureDateString
	 *            of the itinerary
	 * @param departureTimeString
	 *            minimum time of the itinerary
	 * @return string with the possible itineraries
	 * @throws BadTimeSpecificationException
	 *             if timestring mal formated
	 * @throws BadDateSpecificationException
	 *             if datestring mal formated
	 * @throws NoSuchPassengerIdException
	 *             if no passenger with that id
	 * @throws NoSuchStationNameException
	 *             if no valid station given
	 */
	public String search(int passengerId, String departureStationString,
			String arrivalStationString, String departureDateString,
			String departureTimeString)
			throws BadTimeSpecificationException, BadDateSpecificationException,
			NoSuchPassengerIdException, NoSuchStationNameException {
		LocalDate departureDate;
		LocalTime departureTime;
		Passenger pass = getPassengerById(passengerId);
		Station departureStation = getStation(departureStationString);
		Station arrivalStation = getStation(arrivalStationString);

		try {
			departureDate = LocalDate.parse(departureDateString);
		} catch (DateTimeParseException e) {
			throw new BadTimeSpecificationException(departureDateString);
		}

		try {
			departureTime = LocalTime.parse(departureTimeString);
		} catch (DateTimeParseException e) {
			throw new BadDateSpecificationException(departureTimeString);
		}
		return searchItineraries(pass, departureStation, arrivalStation,
				departureDate, departureTime);
	}

	/**
	 * 
	 * Searches all possible itineraries with a different departing service from
	 * to at time and date for that passenger
	 * 
	 * @param passenger
	 *            to regist
	 * @param departureStation
	 *            of the itinerary
	 * @param arrivalStation
	 *            of the itinerary
	 * @param departureDate
	 *            of the itinerary
	 * @param departureTime
	 *            minimum of the itinerary
	 * @return string with the possible itineraries
	 */
	public String searchItineraries(Passenger passenger,
			Station departureStation, Station arrivalStation,
			LocalDate departureDate, LocalTime departureTime) {
		ArrayList<Service> existentServices = new ArrayList<Service>();

		for (Service serv : departureStation
				.getServicesAfterTime(departureTime)) {
			Itinerary it;

			if (serv.goesDirectToAfter(arrivalStation,
					serv.getServiceTimeAtStation(departureStation))) {
				it = new Itinerary(departureDate);
				it.addSegment(
						new Segment(serv, departureStation, arrivalStation));
				passenger.addPreCommIT(it);

			} else if (serv.hasStationAfter(departureStation)) {
				Station nextStation = serv.getStationAfter(departureStation);
				existentServices.add(serv);
				ArrayList<Segment> results = searchItinerariesRecursive(
						departureStation, nextStation, arrivalStation,
						serv.getServiceTimeAtStation(nextStation),
						existentServices);

				if (!results.isEmpty()) {
					it = new Itinerary(departureDate, results);
					passenger.addPreCommIT(it);
				}
			}
		}
		return passenger.toStringPreCommIT();
	}

	/**
	 * 
	 * Auxiliar function to use in recursive to search itineraries
	 * 
	 * @param firstStationOfSegm
	 *            in current service
	 * @param currentStation
	 *            in calculation
	 * @param arrivalStation
	 *            last station
	 * @param minimumTime
	 *            of departure
	 * @param existentServices
	 *            already used
	 * @return list of segments to use
	 */
	public ArrayList<Segment> searchItinerariesRecursive(Station firstStationOfSegm, Station currentStation, Station arrivalStation, LocalTime minimumTime, ArrayList<Service> existentServices) {
		ArrayList<Segment> resultSegms = new ArrayList<Segment>();
		Service currentService = existentServices.get(existentServices.size() - 1);
		boolean alreadyHaveADirect = false;

		for (Service service : currentStation.getServicesAfterTime(minimumTime)) {

			// Lista de segmentos a retornar
			ArrayList<Segment> resultsOfRecursive;

			// Avançar no mesmo serviço
			if (service.getServiceId() == currentService.getServiceId() && currentService.hasStationAfter(currentStation)) {
				Station nextStation = currentService.getStationAfter(currentStation);

				resultsOfRecursive = searchItinerariesRecursive(
						firstStationOfSegm, nextStation, arrivalStation, service.getServiceTimeAtStation(nextStation), existentServices);
				if (!resultsOfRecursive.isEmpty() && !alreadyHaveADirect) {
					resultSegms = compareAndChooseALS(resultSegms, resultsOfRecursive);
				}

				// Mudar de serviço
			} else if (service.hasStationAfter(currentStation)
					&& !existentServices.contains(service)) {
				ArrayList<Segment> existentSegms = new ArrayList<Segment>();

				// Guardar o segmento anterior
				existentSegms.add(new Segment(currentService, firstStationOfSegm, currentStation));

				if (service.goesDirectToAfter(arrivalStation, service.getServiceTimeAtStation(currentStation))) {

					// Encontrado um serviço directo. Retorna este sem mais
					// procura
					existentSegms.add(new Segment(service, currentStation, arrivalStation));

					if (!alreadyHaveADirect) {
						alreadyHaveADirect = true;
						resultSegms = existentSegms;
					} else {
						resultSegms = compareAndChooseALS(resultSegms, existentSegms);
					}
				} else {
					Station nextStation = service.getStationAfter(currentStation);
					ArrayList<Service> newExistentServices = new ArrayList<Service>(existentServices);

					newExistentServices.add(service);
					resultsOfRecursive = searchItinerariesRecursive(firstStationOfSegm, nextStation, arrivalStation, service.getServiceTimeAtStation(nextStation), newExistentServices);

					if (!resultsOfRecursive.isEmpty() && !alreadyHaveADirect) {
						existentSegms.addAll(resultsOfRecursive);
						resultSegms = compareAndChooseALS(resultSegms, existentSegms);
					}
				}
			}
		}
		return resultSegms;
	}

	/**
	 * @param passengerId
	 *            to commit itinerary with
	 * @param itineraryNumber
	 *            of list given with search
	 * @throws NoSuchPassengerIdException
	 *             if invalid id
	 * @throws NoSuchItineraryChoiceException
	 *             if invalid number, except 0
	 */
	public void commitItinerary(int passengerId, int itineraryNumber)
			throws NoSuchPassengerIdException, NoSuchItineraryChoiceException {
		Passenger pass = getPassengerById(passengerId);
		if (itineraryNumber < 0
				|| itineraryNumber > pass.getSizeOfPreCommITs()) {
			pass.resetPreCommITs();
			throw new NoSuchItineraryChoiceException(passengerId,
					itineraryNumber);
		} else if (itineraryNumber == 0) {
			pass.resetPreCommITs();
		} else {
			pass.chooseItinerary(itineraryNumber);
		}

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
			BufferedReader reader = new BufferedReader(
					new FileReader(filename));
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

		try {
			if (patService.matcher(fields[0]).matches()) {
				registerServiceFromFields(fields);
			} else if (patPassenger.matcher(fields[0]).matches()) {
				registerPassengerFromFields(fields);
			} else if (parItinerary.matcher(fields[0]).matches()) {
				registerItineraryFromFields(fields);
			} else {
				throw new BadEntryException(fields[0]);
			}
		} catch (NonUniquePassengerNameException | NoSuchStationNameException
				| NoSuchPassengerIdException | NoSuchServiceIdException e) {
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
	 * @throws NonUniquePassengerNameException
	 *             if repeated name
	 */
	public void registerPassengerFromFields(String[] fields)
			throws NonUniquePassengerNameException {
		registerPassenger(fields[1]);
	}

	/**
	 * @param fields
	 *            of object Service
	 * @throws NoSuchServiceIdException
	 */
	public void registerServiceFromFields(String[] fields)
			throws NoSuchServiceIdException {
		Service newService = new Service(Integer.parseInt(fields[1]),
				Double.parseDouble(fields[2]));
		Station station;
		for (int i = 3; i < fields.length; i += 2) {
			try {
				station = getStation(fields[i + 1]);
			} catch (NoSuchStationNameException e) {
				station = new Station(fields[i + 1]);
				createStation(station);
			}
			newService.addStationtoService(LocalTime.parse(fields[i]), station);
			station.addService(LocalTime.parse(fields[i]), newService);
		}
		createService(newService);
	}

	/**
	 * @param service
	 *            to create
	 */
	public void createService(Service service) {
		_services.put(service.getServiceId(), service);
	}

	/**
	 * @param station
	 *            to create
	 */
	public void createStation(Station station) {
		_stations.put(station.getName(), station);

	}

	/**
	 * @param fields
	 *            of object Itinerary
	 * @throws NoSuchStationNameException
	 * @throws NoSuchPassengerIdException
	 * @throws NoSuchServiceIdException
	 */
	public void registerItineraryFromFields(String[] fields)
			throws NoSuchStationNameException, NoSuchPassengerIdException,
			NoSuchServiceIdException {
		Itinerary newItinerary = new Itinerary(LocalDate.parse(fields[2]));
		String[] segFields;
		Segment s;
		for (int i = 3; i < fields.length; i++) {

			segFields = fields[i].split("\\/");
			s = new Segment(getServiceById(Integer.parseInt(segFields[0])),
					getStation(segFields[1]), getStation(segFields[2]));
			newItinerary.addSegment(s);

		}

		Passenger p = getPassengerById(Integer.parseInt(fields[1]));
		p.commitItinerary(newItinerary);
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
	public Passenger getPassengerById(int id)
			throws NoSuchPassengerIdException {
		if (id >= _totalpassengers || id < 0) {
			throw new NoSuchPassengerIdException(id);
		}
		return _passengers.get(id);
	}

	/**
	 * @return
	 */
	public ArrayList<Passenger> getPassengers() {
		return _passengers;
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
	public ArrayList<Service> getServiceByStation(ServiceSeletor selector,
			String stationName) throws NoSuchStationNameException {
		ArrayList<Service> servicesFiltred = new ArrayList<Service>();
		List<Service> servicesList = new ArrayList<Service>(_services.values());

		Station station = getStation(stationName);
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

	/**
	 * @param name
	 *            of the station
	 * @return object station with that name
	 * @throws NoSuchStationNameException
	 */
	public Station getStation(String name) throws NoSuchStationNameException {
		Station station = _stations.get(name);
		if (station == null) {
			throw new NoSuchStationNameException(name);
		}
		return station;
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
	public String toStringPassengerById(int id)
			throws NoSuchPassengerIdException {
		return getPassengerById(id).toString();
	}

	/**
	 * @return a string with all passengers information
	 */
	public String toStringAllPassengers() {
		String text = "";
		for (Passenger p : getPassengers()) {
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
	public String toStringServicesFromDeparture(String stationDeparture)
			throws NoSuchStationNameException {
		ArrayList<Service> _listFiltred = getServiceByStation(
				new ServiceFromDeparture(), stationDeparture);
		_listFiltred.sort(new DepartureTimeServiceComparator());
		return toStringServices(_listFiltred);
	}

	/**
	 * @param stationArrival
	 *            of the service
	 * @return string with information of all services that arrive to that
	 *         station
	 * @throws NoSuchStationNameException
	 *             if no station found
	 */
	public String toStringServicesToArrival(String stationArrival)
			throws NoSuchStationNameException {
		ArrayList<Service> _listFiltred = getServiceByStation(
				new ServiceToArrival(), stationArrival);
		_listFiltred.sort(new ArrivalTimeServiceComparator());
		return toStringServices(_listFiltred);
	}

	/**
	 * @return all itineraries in string
	 */
	public String toStringAllItineraries() {
		String text = "";
		for (Passenger p : getPassengers()) {
			text += toStringItinerariesFromPassenger(p);
		}
		return text;
	}

	/**
	 * @param passenger
	 *            to get itineraries
	 * @return string with all itineraries from passenger
	 */
	public String toStringItinerariesFromPassenger(Passenger passenger) {
		return passenger.toStringItineraries();
	}

	/**
	 * @param id
	 *            of the passenger
	 * @return string of itineraries of the passenger
	 * @throws NoSuchPassengerIdException
	 *             if invalid id
	 */
	public String toStringItinerariesByPassengerId(int id)
			throws NoSuchPassengerIdException {
		return toStringItinerariesFromPassenger(getPassengerById(id));
	}

	/**
	 * @param listA
	 *            of segments
	 * @param listB
	 *            of segments
	 * @return the list with the fastest segments
	 */
	public ArrayList<Segment> compareAndChooseALS(ArrayList<Segment> listA,
			ArrayList<Segment> listB) {
		TotalTimeArrayListofSegmentsComparator comparator = new TotalTimeArrayListofSegmentsComparator();
		if (comparator.compare(listA, listB) > 0) {
			return listB;
		}
		return listA;
	}

}
