package mmt;

import java.time.Duration;

public class Special extends PassengerCard {

	private static final long serialVersionUID = 4612236146457242552L;
	
	public String _categoryName = "SPECIAL";

	public Special(Passenger pass, int totalItineraries, Duration timeSpent, double totalPaid, double last10Paid) {
		super(pass, totalItineraries, timeSpent, totalPaid, last10Paid);
	}
	
	
    public String status() {
    	return _categoryName;
    }

	@Override
	public void updateCategory() {
		if (_last10Paid<250) {
			_pass.setCategory(new Normal(_pass, _totalItineraries, _timeSpent, _totalPaid, _last10Paid)); 
		}
		else if (_last10Paid < 2500) {
			_pass.setCategory(new Frequent(_pass, _totalItineraries, _timeSpent, _totalPaid, _last10Paid)); 			
		}
		
	}


	@Override
	public double getDiscount() {
		return 1-0.5;
	}

}
