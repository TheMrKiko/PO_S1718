package mmt;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Service implements Serializable {
	
	private int _id;
    private double _price;
    private String _firstStation = "";
    private String _lastStation = "";
        
    private LinkedHashMap<String, LocalTime> _stations;
	private static final long serialVersionUID = 3979509502530686206L;
    
    public Service(int id, double price) {
        _stations = new LinkedHashMap<String, LocalTime>();
        _id = id;
        _price = price;
    }

    public void addStation(LocalTime time, String name) {
        _stations.put(name,time);
    }

    public LinkedHashMap<String, LocalTime> getStations() {
        return _stations;
    }

    //GETTERS
	public int getServiceID() {
		return _id;
	}

	public double getServicePrice() {
		return _price;
	}


    //SERVICE DEPARTURE/PARTIDA
    public String getServiceDepartureStation() {
        if (_firstStation == "") {
            _firstStation = getServiceFirstStation(); //guarda o valor para nao ter de iterar de novo depois
        }
        return _firstStation;
    }

    public LocalTime getServiceDepartureTime() {
        if(_firstStation == "") {
            _firstStation = getServiceFirstStation(); //guarda o valor para nao ter de iterar de novo depois
        }
        return _stations.get(_firstStation);
    }

    public String getServiceFirstStation() { //metodo para nao haver repeticao de codigo na procura da primeira estacao
        return  _stations.keySet().iterator().next();
    }

    //SERVICE ARRIVAL//TERMINO
    public String getServiceArrivalStation() {
        if(_lastStation  == "") {
            _lastStation = getServiceLastStation(); //guarda o valor para nao ter de iterar de novo depois
        }
        return _lastStation;
    }

    public LocalTime getServiceArrivalTime() {
        if(_lastStation == "") {
            _lastStation = getServiceLastStation(); //guarda o valor para nao ter de iterar de novo depois
        }
        return _stations.get(_lastStation);

    }

    public String getServiceLastStation() { //metodo para nao haver repeticao de codigo na procura da ultima estacao
        final Iterator<String> itr = _stations.keySet().iterator();
        String stationName = itr.next();
        while (itr.hasNext()) {
            stationName = itr.next();
        }
        return stationName;
    }
	//COMPARATORS
    public final  Comparator<Service> ARRIVAL_TIME_CMP = new ArrivalTimeComparator();

    public final Comparator<Service> DEPARTURE_TIME_CMP = new DepartureTimeComparator();

    //Compara o tempo de chegada a estacao de termino, independepndentemente da estacao de cada servico
    private class ArrivalTimeComparator implements Comparator<Service>, Serializable {
    	
		private static final long serialVersionUID = -3748406259119112704L;

		public int compare(Service service1, Service service2) {
            LocalTime arrivalTime1 = service1.getServiceArrivalTime();//a que horas chega a estacao de termino
            LocalTime arrivalTime2 = service2.getServiceArrivalTime();
            return arrivalTime1.compareTo(arrivalTime2);
        }
    }

    //Compara o tempo de partida da estacao de inicio, independepndentemente da estacao de cada servico
    private class DepartureTimeComparator implements Comparator<Service>, Serializable {
    	
		private static final long serialVersionUID = -4272639283789213796L;

		public int compare(Service service1, Service service2) {
            LocalTime departureTime1 = service1.getServiceDepartureTime();//a que horas parte da estacao de inicio
            LocalTime departureTime2 = service2.getServiceDepartureTime();
            return departureTime1.compareTo(departureTime2);
        }
    }

}
