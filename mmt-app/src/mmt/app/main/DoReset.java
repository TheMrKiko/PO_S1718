package mmt.app.main;

import mmt.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

//FIXME import other classes if necessary

/**
 * §3.1.1. Reset the ticket office.
 */
public class DoReset extends Command<TicketOffice> {
  
  //FIXME define input fields

  /**
   * @param receiver
   */
  public DoReset(TicketOffice receiver) {
    super(Label.RESET, receiver);
    //FIXME initialize input fields
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    //FIXME implement command
  }

}
