package mmt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mmt.exceptions.BadDateSpecificationException;
import mmt.exceptions.BadTimeSpecificationException;
import mmt.exceptions.ImportFileException;
//import mmt.exceptions.InvalidPassengerNameException;
//import mmt.exceptions.MissingFileAssociationException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NoSuchServiceIdException;
import mmt.exceptions.NoSuchStationNameException;
import mmt.exceptions.NoSuchItineraryChoiceException;
import mmt.exceptions.NonUniquePassengerNameException;

/**
 * Façade for handling persistence and other functions.
 */
public class TicketOffice {

	/** The object doing most of the actual work. */
	private TrainCompany _trains = new TrainCompany(null);
	private String _filename = "";
	private boolean _filechanged = true;

	public void reset() {
		_trains = new TrainCompany(_trains.getServices());
		_filename = "";
		_filechanged = true;
	}

	public void save(String filename) throws IOException {
		_filechanged = false;
		if (filename != null) {
			_filename = filename;
		}
		ObjectOutputStream outos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
		outos.writeObject(_trains);
		outos.close();
	}

	public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		_filechanged = true;
		_filename = filename;
		ObjectInputStream inpos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(_filename)));
		_trains = (TrainCompany) inpos.readObject();
		inpos.close();
	}

	public void importFile(String datafile) throws ImportFileException {
		_trains.importFile(datafile);
	}

	// FIXME complete and implement the itinerary search (and pre-commit store)
	// method
	public String search(int passengerId, String departureStation, String arrivalStation, String departureDate,
			String departureTime) throws BadTimeSpecificationException, BadDateSpecificationException, NoSuchPassengerIdException, NoSuchStationNameException {
		return _trains.search(passengerId, departureStation, arrivalStation, departureDate, departureTime);
	}

	// FIXME complete and implement the itinerary commit method
	public void commitItinerary(int passengerId, int itineraryNumber) throws NoSuchPassengerIdException, NoSuchItineraryChoiceException {
		_filechanged = true;
		_trains.commitItinerary(passengerId, itineraryNumber);
	}

	public void registerPassenger(String name) throws NonUniquePassengerNameException {
		_filechanged = true;
		_trains.registerPassenger(name);
	}

	public void changePassengerName(String newName, int id) throws NonUniquePassengerNameException, NoSuchPassengerIdException {
		_filechanged = true;
		_trains.updatePassengerName(newName, id);
	}
	
	public String toStringServicesFromDeparture(String stationDeparture) throws NoSuchStationNameException  {
		return _trains.toStringServicesFromDeparture(stationDeparture);
	}

	public String toStringServicesToArrival(String stationArrival) throws NoSuchStationNameException {
		return _trains.toStringServicesToArrival(stationArrival);
	}
	
	public String toStringPassengerById(int id) throws NoSuchPassengerIdException {
		return _trains.toStringPassengerById(id);
	}
	
	public boolean hasFilename() {
		return _filename != "";
	}

	public boolean hasFileChanged() {
		return _filechanged;
	}

	public String toStringServiceById(int id) throws NoSuchServiceIdException {
		return _trains.toStringServiceById(id);
	}

	public String toStringAllPassengers() {
		return _trains.toStringAllPassengers();
	}

	public String toStringAllServices() {
		return _trains.toStringAllServices();
	}

	public String toStringAllItineraries() {
		return _trains.toStringAllItineraries();
	}

	public String toStringItinerariesByPassengerId(int id) throws NoSuchPassengerIdException {
		return _trains.toStringItinerariesByPassengerId(id);		
	}

}
