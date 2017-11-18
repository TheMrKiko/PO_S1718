package mmt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

import mmt.exceptions.BadDateSpecificationException;
import mmt.exceptions.BadEntryException;//
import mmt.exceptions.BadTimeSpecificationException;
import mmt.exceptions.ImportFileException;
//import mmt.exceptions.InvalidPassengerNameException;
import mmt.exceptions.NoSuchDepartureException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NoSuchServiceIdException;
import mmt.exceptions.NoSuchStationNameException;
import mmt.exceptions.NoSuchItineraryChoiceException;
import mmt.exceptions.NonUniquePassengerNameException;

/**
 * A train company has schedules (services) for its trains and passengers that
 * acquire itineraries based on those schedules.
 */
public class TrainCompany implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 201708301010L;
	private TreeMap<Integer, Service> _services = new TreeMap<Integer, Service>();
	private ArrayList<Passenger> _passengers = new ArrayList<Passenger>();
	private int _totalpassengers = 0;
	
	public void registerPassenger(String name) throws NonUniquePassengerNameException {
		checkDuplicatePassengerName(name);
		_passengers.add(new Passenger(name, _totalpassengers++));
	}

	public void checkDuplicatePassengerName(String name) throws NonUniquePassengerNameException {
		for (Passenger p : _passengers) {
			if (p.getName().equals(name)) {
				throw new NonUniquePassengerNameException(name);
			}
		}
	}
	
	public void updatePassengerName(String newName, int id) throws NonUniquePassengerNameException, NoSuchPassengerIdException {
		checkDuplicatePassengerName(newName);
		getPassengerById(id).setName(newName);
	}

	public Passenger getPassengerById(int id) throws NoSuchPassengerIdException {
		if (id >= _totalpassengers || id < 0) {
			throw new NoSuchPassengerIdException(id);
		}
		return _passengers.get(id);
	}

	// Seleciona Servicos por determinada estacao
	ArrayList<Service> getServiceByStation(ServiceSeletor ss, String station) {
		ArrayList<Service> stationList = new ArrayList<Service>();
		List<Service> servicesList = new ArrayList<Service>(_services.values());
		for (Service s : servicesList)
			if (ss.matches(s, station)) {
				stationList.add(s);
			}
		return stationList;
	}

	/*
	 * FIXME add methods for registerPassenger, changePassengerName
	 * searchItineraries, commitItinerary
	 */

	//IMPORT FILE
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

		public void registerFromFields(String[] fields) throws BadEntryException {
			Pattern patService = Pattern.compile("^(SERVICE)");
			Pattern patPassenger = Pattern.compile("^(PASSENGER)");
			Pattern parItinerary = Pattern.compile("^(ITINERARY)");

			if (patService.matcher(fields[0]).matches()) {
				registerServiceFromFields(fields);

			} else if (patPassenger.matcher(fields[0]).matches()) {
				registerPassengerFromFields(fields);

			} else if (parItinerary.matcher(fields[0]).matches()) {
				// registerItineraryFromFields(fields);

			} else {
				throw new BadEntryException(fields[0]);
			}
		}
		
		//FromFields
		public void registerPassengerFromFields(String[] fields) {
			_passengers.add(new Passenger(fields[1], _totalpassengers++));

		}

		public void registerServiceFromFields(String[] fields) {
			// System.out.println(fields[1] + " "+fields[2]);
			Service newService = new Service(Integer.parseInt(fields[1]), Double.parseDouble(fields[2]));

			for (int i = 3; i < fields.length; i += 2) {
				newService.addStation(LocalTime.parse(fields[i]), fields[i + 1]);
			}

			_services.put(newService.getServiceID(), newService);
		}
	

	public Service getServiceById(int id) {
		return _services.get(id);
	}

	public TreeMap<Integer, Service> getServices() {
		return _services;
	}

	public void setServices(TreeMap<Integer, Service> services) {
		_services = services;
	}
}
