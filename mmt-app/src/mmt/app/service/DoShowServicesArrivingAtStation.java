package mmt.app.service;

import mmt.TicketOffice;
import mmt.exceptions.NoSuchStationNameException;
import mmt.app.exceptions.NoSuchStationException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;


/**
 * 3.2.4 Show services arriving at station.
 */
public class DoShowServicesArrivingAtStation extends Command<TicketOffice> {

	Input<String> _arrivalStation;
  /**
   * @param receiver
   */
  public DoShowServicesArrivingAtStation(TicketOffice receiver) {
    super(Label.SHOW_SERVICES_ARRIVING_AT_STATION, receiver);
    _arrivalStation = _form.addStringInput(Message.requestStationName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
	  _form.parse();
	  try {
		  _display.popup(_receiver.toStringServicesToArrival(_arrivalStation.value()));
	  }
	  catch(NoSuchStationNameException e) {
		  throw new NoSuchStationException(_arrivalStation.value());
	  }
  }

}
