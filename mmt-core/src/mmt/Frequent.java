package mmt;

public class Frequent extends PassengerCard {

	private static final long serialVersionUID = 7982706316042532172L;
	
	public String _categoryName = "FREQUENT";

	public Frequent(Passenger pass) {
		super(pass);
	}
	
    public String status() {
    	return _categoryName;
    }

}
