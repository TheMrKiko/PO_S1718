package mmt;

import java.time.Duration;

public class Frequent extends PassengerCard {

	private static final long serialVersionUID = 7982706316042532172L;
	
	public String _categoryName = "FREQUENTE";

	public Frequent(Passenger pass, int totalItineraries, Duration timeSpent, double totalPaid, double last10Paid) {
		super(pass, totalItineraries, timeSpent, totalPaid, last10Paid);
	}
	
	@Override
    public String status() {
    	return _categoryName;
    }

	@Override
	public void updateCategory() {
		if (_last10Paid < 250) {
			_pass.setCategory(new Normal(_pass, _totalItineraries, _timeSpent, _totalPaid, _last10Paid)); 
		}
		else if (_last10Paid > 2500) {
			_pass.setCategory(new Special(_pass, _totalItineraries, _timeSpent, _totalPaid, _last10Paid)); 			
		}
	}

	@Override
	public double getDiscount() {
		return 1-0.15;
	}

}
