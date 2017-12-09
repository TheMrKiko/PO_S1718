package mmt.app.itineraries;

import mmt.TicketOffice;
import mmt.app.exceptions.BadDateException;
import mmt.app.exceptions.BadTimeException;
//import mmt.app.exceptions.NoSuchItineraryException;
import mmt.app.exceptions.NoSuchPassengerException;
import mmt.app.exceptions.NoSuchStationException;
import mmt.exceptions.BadDateSpecificationException;
import mmt.exceptions.BadTimeSpecificationException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NoSuchStationNameException;
//import mmt.exceptions.NoSuchItineraryChoiceException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

//FIXME import other classes if necessary

/**
 * §3.4.3. Add new itinerary.
 */
public class DoRegisterItinerary extends Command<TicketOffice> {

	Input<Integer> _id;
	Input<String> _departureStation, _arrivalStation, _date, _time;

	/**
	 * @param receiver
	 */
	public DoRegisterItinerary(TicketOffice receiver) {
		super(Label.REGISTER_ITINERARY, receiver);
		// FIXME initialize input fields
		_display.popup(Message.requestPassengerId());
		_display.popup(Message.requestDepartureStationName());
		_display.popup(Message.requestArrivalStationName());
		_display.popup(Message.requestDepartureDate());
		_display.popup(Message.requestDepartureTime());
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		
		try { //FIXME implement command
			_form.parse();
			 _receiver.search(_id.value(), _departureStation.value(), _arrivalStation.value(), _date.value(), _time.value());
		} catch (NoSuchPassengerIdException e) {
			throw new NoSuchPassengerException(e.getId()); 
		}
		catch (NoSuchStationNameException e) { 
			 throw new NoSuchStationException(e.getName()); 
		}
		/*catch (NoSuchItineraryChoiceException e) { 
			 throw new NoSuchItineraryException(e.getPassengerId(), e.getItineraryId());
		}*/ catch (BadDateSpecificationException e) {
			throw new BadDateException(e.getDate());
		} catch (BadTimeSpecificationException e) {
			throw new BadTimeException(e.getTime());
		}
		 
	}
}
