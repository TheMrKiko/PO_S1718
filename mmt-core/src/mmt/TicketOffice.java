package mmt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//import mmt.exceptions.BadDateSpecificationException;
//import mmt.exceptions.BadTimeSpecificationException;
import mmt.exceptions.ImportFileException;
//import mmt.exceptions.InvalidPassengerNameException;
//import mmt.exceptions.MissingFileAssociationException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NoSuchServiceIdException;
//import mmt.exceptions.NoSuchServiceIdException;
//import mmt.exceptions.NoSuchStationNameException;
//import mmt.exceptions.NoSuchItineraryChoiceException;
import mmt.exceptions.NonUniquePassengerNameException;

/**
 * Façade for handling persistence and other functions.
 */
public class TicketOffice {

	/** The object doing most of the actual work. */
	private TrainCompany _trains = new TrainCompany();
	private String _filename = "";
	private boolean _filechanged = true;

	public void reset() {
		TrainCompany newTrains = new TrainCompany();
		_filename = "";
		_filechanged = true;
		newTrains.setServices(_trains.getServices());
		_trains = newTrains;
	}

	public void save(String filename) throws IOException {
		_filechanged = false;
		ObjectOutputStream outos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
		outos.writeObject(_trains);
		outos.close();
	}

	public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		_filechanged = true;
		ObjectInputStream inpos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
		_trains = (TrainCompany) inpos.readObject();
		inpos.close();
	}

	public void importFile(String datafile) throws ImportFileException {
		_trains.importFile(datafile);
	}

	// FIXME complete and implement the itinerary search (and pre-commit store)
	// method
	public void search(int passengerId, String departureStation, String arrivalStation, String departureDate,
			String departureTime) /* FIXME define thrown exceptions */ {
		// FIXME implement method
	}

	// FIXME complete and implement the itinerary commit method
	public void commitItinerary(int passengerId, int itineraryNumber) /* FIXME define thrown exceptions */ {
		_filechanged = true;
		// FIXME implement method
	}

	public void registerPassenger(String name) throws NonUniquePassengerNameException {
		_filechanged = true;
		_trains.registerPassenger(name);
	}

	public void changePassengerName(String newName, int id) throws NonUniquePassengerNameException, NoSuchPassengerIdException {
		_filechanged = true;
		_trains.updatePassengerName(newName, id);
	}

	public ArrayList<Service> getServiceFromDeparture(String stationDeparture) {
		return _trains.getServiceByStation(new ServiceFromDeparture(), stationDeparture);
	}

	public ArrayList<Service> getServiceToArrival(String stationArrival) {
		return _trains.getServiceByStation(new ServiceToArrival(), stationArrival);
	}
	
	public String toStringPassengerById(int id) throws NoSuchPassengerIdException {
		return _trains.toStringPassengerById(id);
	}
	

	public String getFilename() {
		return _filename;
	}

	public void setFilename(String filename) {
		_filename = filename;
	}

	public boolean getFileChanged() {
		return _filechanged;
	}

	public String toStringServiceById(int id) throws NoSuchServiceIdException {
		return _trains.toStringServiceById(id);
		
	}

}
