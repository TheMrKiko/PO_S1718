package mmt.app.service;

import mmt.TicketOffice;
import mmt.exceptions.NoSuchStationNameException;
import mmt.app.exceptions.NoSuchStationException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 3.2.3 Show services departing from station.
 */
public class DoShowServicesDepartingFromStation extends Command<TicketOffice> {

	Input<String> _departingStation;
  /**
   * @param receiver
   */
  public DoShowServicesDepartingFromStation(TicketOffice receiver) {
    super(Label.SHOW_SERVICES_DEPARTING_FROM_STATION, receiver);
    _departingStation = _form.addStringInput(Message.requestStationName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
	  _form.parse();
	  try {
		  _display.popup(_receiver.toStringServicesFromDeparture(_departingStation.value()));
	  }
	  catch(NoSuchStationNameException e) {
		  throw new NoSuchStationException(_departingStation.value());
	  }
  }

}
