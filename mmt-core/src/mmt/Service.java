package mmt;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class Service {
    private int _id;
    private double _price;
    private String _firstStation = "";
    private String _lastStation = "";
    //private HashMap<String, Station> _stations;
    private LinkedHashMap<String,LocalTime> _stations;
    public Service(int id, double price) {
        _stations = new LinkedHashMap<String,LocalTime>();
        _id = id;
        _price = price;
    }

    public void addStation(String name, LocalTime time) {
        _stations.put(name,time);
    }

    public LinkedHashMap<String,LocalTime> getStations() {
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

    // NAO HAVENDO CLASS ESTACAO
    //Compara o tempo de chegada a estacao de termino, independepndentemente da estacao de cada servico
    private class ArrivalTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            LocalTime arrivalTime1 = service1.getServiceArrivalTime();//a que horas chega a estacao de termino
            LocalTime arrivalTime2 = service2.getServiceArrivalTime();
            return arrivalTime1.compareTo(arrivalTime2);
        }
    }

    //Compara o tempo de partida da estacao de inicio, independepndentemente da estacao de cada servico
    private class DepartureTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            LocalTime departureTime1 = service1.getServiceDepartureTime();//a que horas parte da estacao de inicio
            LocalTime departureTime2 = service2.getServiceDepartureTime();
            return departureTime1.compareTo(departureTime2);
        }
    }

    /*** COMPARATORS JA DESNECESSARIOS?*/
    //Compara o tempo de chegada a estacao de termino,sendo a mesma estacao para os dois servicos
    /*private class SameStationArrivalTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            String stationArrival1 = service1.getServiceArrival();
            String stationArrival2 = stationArrival1;
            LocalTime arrivalTime1 = _stations.get(stationArrival1);
            LocalTime arrivalTime2 = _stations.get(stationArrival2);
            return arrivalTime1.compareTo(arrivalTime2);
        }
    }

    //Compara o tempo de chegada a estacao de termino,sendo a mesma estacao para os dois servicos
    /*private class SameStationDepartureTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            String stationDeparture1 = service1.getServiceDeparture();
            String stationDeparture2 = stationDeparture1;
            LocalTime departureTime1 = _stations.get(stationDeparture1);
            LocalTime departureTime2 = _stations.get(stationDeparture2);
            return departureTime1.compareTo(departureTime2);
        }
    }
    ****/


    //SE HOVER CLASS ESTACAO NAO LEIAS ENTAO ISTO FRANCISCO
    /*Construtor
    public Service(int id, double price, ArrayList<Station> ls) {
        _stations = new HashMap<String, Station>;
        _id = id;
        _price = price;
        for(Station s: ls) {
            _stations.put(s.getName(), s);
        }
    }
    public Station getServiceDeparture() {
        return _stations.get(_stations.firstKey());
    }

    public Station getServiceArrival(){
        return _stations.get(_stations.lastKey());
    }

    //Comparators
    private class ArrivalTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            return ((service1.getServiceArrival()).getLocalTimeInService(service1.getServiceID)).compareTo((service2.getServiceArrival()).getLocalTimeInService(service2.getServiceID));
        }
    }


    private class DepartureTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            return ((service1.getServiceDeparture()).getLocalTimeInService(service1.getServiceID)).compareTo((service2.getServiceDeparture()).getLocalTimeInService(service2.getServiceID));
        }
    }

    */
}
