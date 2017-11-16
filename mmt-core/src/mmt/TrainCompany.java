package mmt;

import java.io.Serializable;

import mmt.exceptions.BadDateSpecificationException;
import mmt.exceptions.BadEntryException;
import mmt.exceptions.BadTimeSpecificationException;
import mmt.exceptions.InvalidPassengerNameException;
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
  private Service _services[];

  //FIXME define fields

	/*void importFile(String filename) {
		String line;
    	BufferedReader reader = new BufferedReader(new FileReader(filename));
    	while ((line = reader.readLine()) != null) {
        	String[] fields = line.split("\\|");

			registerFromFields(fields);

        	reader.close();
    	}




	void registerFromFields(String[] fields) {



		Class<?> type = Class.forName(fields[0]);
		Constructor ctor = type.getContructor(fields[1:]);
		Class<?> obj = ctor.newInstance();
			_services.put(obj);
    }


    void registerService(String... fields)  { throws PublicationExistsException,
                        UnknownAgentException, UnknownDataException
        int i;
        int servId = (fields[1] != null) ? Integer.parseInt(fields[1]) : getUUID();
        int servPrice = (fields[2] != null) ? Integer.parseInt(fields[2]) : getUUID();
        while(fields[i]!='\n') {

        }
    }*/
  /*FIXME
   * add methods for
   *   registerPassenger, changePassengerName
   *   searchItineraries, commitItinerary
   */

  //FIXME implement other functions if necessary

  public Service getService(int id) {
	  return _services[id];
    }

}
