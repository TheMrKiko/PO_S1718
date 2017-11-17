package mmt.app.passenger;

import mmt.TicketOffice;
import mmt.app.exceptions.BadPassengerNameException;
import mmt.app.exceptions.DuplicatePassengerNameException;
import mmt.app.exceptions.NoSuchPassengerException;
import mmt.exceptions.InvalidPassengerNameException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NonUniquePassengerNameException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

//FIXME import other classes if necessary

/**
 * §3.3.4. Change passenger name.
 */
public class DoChangerPassengerName extends Command<TicketOffice> {

  //FIXME define input fields
  Input<String> _newName;
  Input<Integer> _id;
  /**
   * @param receiver
   */
  public DoChangerPassengerName(TicketOffice receiver) {
    super(Label.CHANGE_PASSENGER_NAME, receiver);
    //FIXME initilize input fields
    _id = _form.addIntegerInput(Message.requestPassengerId());
    _newName = _form.addStringInput(Message.requestPassengerName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    //FIXME implement command
    _form.parse();
    try {
        Passenger p = _receiver.getPassengerById(_id.value());
        p.changePassengerName(_newName.value());
    }
    catch (NonUniquePassengerNameException e) {
    	throw new DuplicatePassengerNameException(_newName.value());
    }
    catch (NoSuchPassengerIdException e) {
        throw new NoSuchPassengerException(_newName.value());
    }
    catch (InvalidPassengerNameException e) {
        throw new BadPassengerNameException(_newName.value());
    }
  }
}
