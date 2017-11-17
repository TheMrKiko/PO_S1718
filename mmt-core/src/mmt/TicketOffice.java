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
import mmt.exceptions.InvalidPassengerNameException;
import mmt.exceptions.MissingFileAssociationException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NoSuchServiceIdException;
import mmt.exceptions.NoSuchStationNameException;
import mmt.exceptions.NoSuchItineraryChoiceException;
import mmt.exceptions.NonUniquePassengerNameException;

//FIXME import other classes if necessary

/**
 * Façade for handling persistence and other functions.
 */
public class TicketOffice {

  /** The object doing most of the actual work. */
  private TrainCompany _trains = new TrainCompany();
  private String _filename = "";


  //FIXME define other fields

  public void reset() {
	  _filename = "";
	  TrainCompany newTrains = new TrainCompany();
	  newTrains.setServices(_trains.getServices());
	  _trains = newTrains;
  }

  public void save(String filename) throws IOException {
	ObjectOutputStream outos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
	outos.writeObject(_trains);
	outos.close();
  }

  public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
	  ObjectInputStream inpos = new ObjectInputStream( new BufferedInputStream(new FileInputStream(filename)));
	  _trains = (TrainCompany) inpos.readObject();
	  inpos.close();
  }

  public void importFile(String datafile) throws ImportFileException {
    _trains.importFile(datafile);
  }

  //FIXME complete and implement the itinerary search (and pre-commit store) method
  public void search(int passengerId, String departureStation, String arrivalStation, String departureDate, String departureTime) /*FIXME define thrown exceptions */ {
    //FIXME implement method
  }

  //FIXME complete and implement the itinerary commit method
  public void commitItinerary(int passengerId, int itineraryNumber) /*FIXME define thrown exceptions */ {
    //FIXME implement method
  }

  public void registerPassenger(String name) throws NonUniquePassengerNameException {
      _trains.addPassenger(name);
  }

  public void changePassengerName(String newName, int id) throws NonUniquePassengerNameException, NoSuchPassengerIdException {
      _trains.updatePassengerName(newName, id);
  }

  public void getServiceFromDeparture(String stationDeparture)  {
      _trains.getServiceByStation(new ServiceFromDeparture(), stationDeparture);
  }

  public void getServiceToArrival(String stationArrival) {
      _trains.getServiceByStation(new ServiceToArrival(), stationArrival);
  }

  public String getFilename(){
	  return _filename;
  }
  
  public void setFilename(String filename) {
	  _filename = filename;
  }

}
