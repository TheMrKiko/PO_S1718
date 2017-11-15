package mmt.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;

import mmt.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

//FIXME import other classes if necessary

/**
 * §3.1.1. Open existing document.
 */
public class DoOpen extends Command<TicketOffice> {
  /**
   * @param receiver
   */
  public DoOpen(TicketOffice receiver) {
    super(Label.OPEN, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    /*try {
      //FIXME implement command
    } catch (FileNotFoundException fnfe) {
      _display.popup(Message.fileNotFound());
    } catch (ClassNotFoundException | IOException e) {
      // shouldn't happen in a controlled test setup
      e.printStackTrace();
    }*/
  }

}
