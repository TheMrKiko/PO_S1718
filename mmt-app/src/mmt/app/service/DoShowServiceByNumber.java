package mmt.app.service;

import mmt.TicketOffice;
import mmt.exceptions.NoSuchServiceIdException;
import mmt.app.exceptions.NoSuchServiceException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;


/**
 * 3.2.2 Show service by number.
 */
public class DoShowServiceByNumber extends Command<TicketOffice> {

	Input<Integer> _id;

	/**
	 * @param receiver
	 */
	public DoShowServiceByNumber(TicketOffice receiver) {
		super(Label.SHOW_SERVICE_BY_NUMBER, receiver);
		_id = _form.addIntegerInput(Message.requestServiceId());
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		_form.parse();
		try {
			_display.popup(_receiver.toStringServiceById(_id.value()));
		}
		catch (NoSuchServiceIdException e) {
			throw new NoSuchServiceException(_id.value());
		}
	}

}
