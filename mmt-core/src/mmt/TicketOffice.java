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
  private TrainCompany _trains;

  //FIXME define other fields

  public void reset() {
    //FIXME implement this function
  }

  public void save(String filename) /*FIXME add thrown exceptions*/ {
    //FIXME implement this function
  }

  public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
    //FIXME implement this function
  }

  public void importFile(String datafile) throws ImportFileException {
    _trains.importFile(datafile);
  }

  //FIXME complete and implement the itinerary search (and pre-commit store) method
  public /*FIXME choose return type */ search(int passengerId, String departureStation, String arrivalStation, String departureDate,
                                              String departureTime) /*FIXME define thrown exceptions */ {
    //FIXME implement method
  }

  //FIXME complete and implement the itinerary commit method
  public /*FIXME choose return type */ commitItinerary(int passengerId, int itineraryNumber) /*FIXME define thrown exceptions */ {
    //FIXME implement method
  }

  //FIXME add methods for passenger registration and passenger name update

  //FIXME add other functions if necessary

}
