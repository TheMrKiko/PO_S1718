package mmt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class Itinerary implements Serializable {
	
	private static final long serialVersionUID = -4604865736288785604L;
	
	private Double _price;
	private int _order;
	private LocalDate _date;
	private ArrayList<Segment> _segm;
	
	public Itinerary(LocalDate date) {
		_date = date;
		_segm = new ArrayList<Segment>();
	}
	
	/*public Itinerary(LocalDate date, ArrayList<Segment> segm) {
		this(date);
		_segm = segm;
		_price = calcPrice();
	}*/

	public double calcPrice() {
		double price = 0; 
		for (Segment s : _segm) {
			price += s.getPrice();
		}
		return price;
	}
	
	public void addSegment(Segment s) {
		_segm.add(s);
	}
	
	public boolean isDirect() {
		return _segm.size() == 1;
	}

	public double getPrice() {
		if (_price == null) {
			_price = calcPrice();
		}
		return _price;
	}

	public LocalDate getDate() {
		return _date;
	}
	
	public int getOrder() {
		return _order;
	}

	public void setOrder(int order) {
		_order = order;
	}
	
	@Override
	public String toString() {
		String text = "Itinerário " + getOrder() + " para " + getDate().toString() + " @ " + String.format(Locale.UK, "%.2f", getPrice());
		for(Segment s: _segm) {
			text += "\n" + s.toString();
		}
		return text;
	}
    
}
