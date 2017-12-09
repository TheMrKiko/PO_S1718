package mmt.app.itineraries;

import mmt.TicketOffice;
import mmt.app.exceptions.BadDateException;
import mmt.app.exceptions.BadTimeException;
import mmt.app.exceptions.NoSuchItineraryException;
import mmt.app.exceptions.NoSuchPassengerException;
import mmt.app.exceptions.NoSuchStationException;
import mmt.exceptions.BadDateSpecificationException;
import mmt.exceptions.BadTimeSpecificationException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NoSuchStationNameException;
import mmt.exceptions.NoSuchItineraryChoiceException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * §3.4.3. Add new itinerary.
 */
public class DoRegisterItinerary extends Command<TicketOffice> {

	

	/**
	 * @param receiver
	 */
	public DoRegisterItinerary(TicketOffice receiver) {
		super(Label.REGISTER_ITINERARY, receiver);
		
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		
		Input<Integer> _id, _choice;
		Input<String> _departureStation, _arrivalStation, _date, _time;
		
		_id = _form.addIntegerInput(Message.requestPassengerId());
		_departureStation = _form.addStringInput(Message.requestDepartureStationName());
		_arrivalStation = _form.addStringInput(Message.requestArrivalStationName());
		_date = _form.addStringInput(Message.requestDepartureDate());
		_time = _form.addStringInput(Message.requestDepartureTime());
		String text = "";
		
		try {			
			_form.parse();
			_form.clear();
			text = _receiver.search(_id.value(), _departureStation.value(), _arrivalStation.value(), _date.value(), _time.value());
			if (text.length() > 0) {
				_display.popup(text);
				_choice = _form.addIntegerInput(Message.requestItineraryChoice());
				_form.parse();
				_form.clear();
				_receiver.commitItinerary(_id.value(), _choice.value());
			}
		} catch (NoSuchPassengerIdException e) {
			throw new NoSuchPassengerException(e.getId()); 
		} catch (NoSuchStationNameException e) { 
			 throw new NoSuchStationException(e.getName()); 
		} catch (NoSuchItineraryChoiceException e) { 
			 throw new NoSuchItineraryException(e.getPassengerId(), e.getItineraryId());
		} catch (BadDateSpecificationException e) {
			throw new BadDateException(e.getDate());
		} catch (BadTimeSpecificationException e) {
			throw new BadTimeException(e.getTime());
		}
		 
	}
}
