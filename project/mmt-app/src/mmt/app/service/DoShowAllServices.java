package mmt.app.service;

import mmt.TicketOffice;
import pt.tecnico.po.ui.Command;

/**
 * 3.2.1 Show all services.
 */
public class DoShowAllServices extends Command<TicketOffice> {

	/**
	 * @param receiver
	 */
	public DoShowAllServices(TicketOffice receiver) {
		super(Label.SHOW_ALL_SERVICES, receiver);
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() {
		_display.popup(_receiver.toStringAllServices());
	}

}
