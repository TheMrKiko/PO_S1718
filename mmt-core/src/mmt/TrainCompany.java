package mmt;

import java.io.Serializable;

/*import mmt.exceptions.BadDateSpecificationException;
import mmt.exceptions.BadEntryException;
import mmt.exceptions.BadTimeSpecificationException;
import mmt.exceptions.InvalidPassengerNameException;
import mmt.exceptions.NoSuchDepartureException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NoSuchServiceIdException;
import mmt.exceptions.NoSuchStationNameException;
import mmt.exceptions.NoSuchItineraryChoiceException;
import mmt.exceptions.NonUniquePassengerNameException;*/

//FIXME import other classes if necessary

/**
 * A train company has schedules (services) for its trains and passengers that
 * acquire itineraries based on those schedules.
 */
public class TrainCompany implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201708301010L;

  //FIXME define fields

  void importFile(String filename) {
    //FIXME implement function
  }


void readImportFile(String name) { //throws IOException
    BufferedReader reader = new BufferedReader(new FileReader(name));
    String line;
    while ((line = reader.readLine()) != null) {
        String[] fields = line.split("\\|");
        try {
            registerFromFields(fields);

        } /*catch (UnknownDataException e) {
            System.err.printf("WARNING: unknown data %s\n", e.getMessage());
            e.printStackTrace();
        } catch (PublicationExistsException e) {
            e.printStackTrace();
        } catch (UnknownAgentException e) {
            e.printStackTrace();
        } catch (ClientExistsException e) {
            e.printStackTrace();
        } catch (InvalidIdentifierException e) {
            e.printStackTrace();
        }
        */
        reader.close();
    }

    void registerFromFields(String[] fields) { /* throws UnknownDataException,
                            PublicationExistsException, UnknownAgentException, ClientExistsException,
                            InvalidIdentifierException*/
                    // Regular expression pattern to match a service.
        Pattern patService = Pattern.compile("^(SERVICE)");
                    // Regular expression pattern to match a passenger.
        Pattern patPassenger = Pattern.compile("^(PASSENGER)");
                    // Regular expression pattern to match a itinerary.
        Pattern parPublication = Pattern.compile("^(ITINERARY)");

        if (patAgent.matcher(fields[0]).matches()) {
            registerService(fields);
        } else if (patMessage.matcher(fields[0]).matches()) {
            registerPassenger(fields);
        } else if (parPublication.matcher(fields[0]).matches()) {
            registerItinerary(fields);
        } /*else {
                            throw new UnknownDataException(fields[0]);
        }*/
    }


    void registerService(String... fields)  { /*throws PublicationExistsException,
                        UnknownAgentException, UnknownDataException*/
        int i;
        int servId = (fields[1] != null) ? Integer.parseInt(fields[1]) : getUUID();
        int servPrice = (fields[2] != null) ? Integer.parseInt(fields[2]) : getUUID();
        while(fields[i]!='\n') {

        }
    }
  /*FIXME
   * add methods for
   *   registerPassenger, changePassengerName
   *   searchItineraries, commitItinerary
   */

  //FIXME implement other functions if necessary

}
