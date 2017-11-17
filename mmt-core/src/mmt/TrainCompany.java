package mmt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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

//FIXME import other classes if necessary

/**
 * A train company has schedules (services) for its trains and passengers that
 * acquire itineraries based on those schedules.
 */
public class TrainCompany implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 201708301010L;
	private TreeMap<Integer, Service> _services;
	private ArrayList<Passenger> _passengers = new ArrayList<Passenger>();
	private int _totalpassengers = 0;

	// FIXME define fields

	public void importFile(String filename) throws ImportFileException {
		String line;
    	try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));

			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\\|");

				registerFromFields(fields);

				reader.close();
			}
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
			registerService(fields);

		} else if (patPassenger.matcher(fields[0]).matches()) {
			registerPassenger(fields);


		} else if (parItinerary.matcher(fields[0]).matches()) {
			//registerItinerary(fields);

		} else {
			throw new BadEntryException(fields[0]);
		}
	}


	public void registerPassenger(String[] fields) {
		addPassenger(fields[1]);

	}

	public void registerService(String... fields) {

    }

	public void addPassenger(String name) {
		_passengers.add(new Passenger(name, _totalpassengers++));
	}

	public void updatePassengerName(String newName, int id) throws NonUniquePassengerNameException, NoSuchPassengerIdException {
		for (Passenger p: _passengers) {
			if (p.getName().equals(newName)) {
				throw new NonUniquePassengerNameException(newName);
			}
			getPassengerById(id).setName(newName);
		}
	}

	public Passenger getPassengerById(int id) throws NoSuchPassengerIdException {
		if (id >= _totalpassengers) {
			throw new NoSuchPassengerIdException(id);
		}
		return _passengers.get(id);
	}


//Seleciona Servicos por determinada estacao
	ArrayList<Service> getServiceByStation(ServiceSeletor ss, String station) {
		ArrayList<String> stationList = new ArrayList<String>();
		for(String s: _services)
			if(ss.matches(s,station)) stationList.add(s);
		return stationList;
	}

	/*
	 * FIXME add methods for registerPassenger, changePassengerName
	 * searchItineraries, commitItinerary
	 */

	// FIXME implement other functions if necessary

	public Service getServiceById(int id) {
		return _services.get(id);
	}

}
