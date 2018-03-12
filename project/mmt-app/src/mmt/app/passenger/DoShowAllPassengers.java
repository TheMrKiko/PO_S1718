package mmt.app.passenger;

import mmt.TicketOffice;
import pt.tecnico.po.ui.Command;

/**
 * §3.3.1. Show all passengers.
 */
public class DoShowAllPassengers extends Command<TicketOffice> {

  /**
   * @param receiver
   */
  public DoShowAllPassengers(TicketOffice receiver) {
    super(Label.SHOW_ALL_PASSENGERS, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
	  _display.popup(_receiver.toStringAllPassengers());
  }

}
