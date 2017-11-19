package mmt.app.main;

import java.io.IOException;

import mmt.TicketOffice;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

/**
 * §3.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<TicketOffice> {

	Input<String> _filename;
	private String _filenameValue;

	/**
	 * @param receiver
	 */
	public DoSave(TicketOffice receiver) {
		super(Label.SAVE, receiver);

		// if (_receiver.getFilename() == ""){
		_filename = _form.addStringInput(Message.newSaveAs());
		// }
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() {
		if (_receiver.getFileChanged()) {
			if (_receiver.getFilename() == "") {
				_form.parse();
				_filenameValue = _filename.value();
				_receiver.setFilename(_filenameValue);
			} else {
				_filenameValue = _receiver.getFilename();
				//_display.popup(Message.saveAs() + _filenameValue);
			}
			try {
				_receiver.save(_filenameValue);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
