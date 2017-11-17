package mmt;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class Service {
    private int _id;
    private double _price;
    //private HashMap<String, Station> _stations;
    private HashMap<String,LocalTime> _stations;
    public Service(int id, double price) {
        _stations = new HashMap<String, LocalTime>;
        _id = id;
        _price = price;
    }

    public void addStation(String name, LocalTime time) {
        _stations.put(name,time);
    }

    public HashMap<String,Station> getStations() {
        return _stations;
    }

    //GETTERS
	public int getServiceID() {return _id;}

	public double getServicePrice() {return _price;}

    public Station getServiceDeparture() {
        return _stations.get(_stations.firstKey());
    }

    public Station getServiceArrival(){
        return _stations.get(_stations.lastKey());
    }

	//COMPARATORS
    public final  Comparator<Service> ARRIVAL_TIME_CMP = new ArrivalTimeComparator();

    public final Comparator<Service> DEPARTURE_TIME_CMP = new DepartureTimeComparator();

    /*** NAO HAVENDO CLASS ESTACAO*/
    //Compara o tempo de chegada a estacao de termino, independepndentemente da estacao de cada servico
    private class ArrivalTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            String stationArrival1 = service1.getServiceArrival(); //estacao de onde termina o servico
            String stationArrival2 = service2.getServiceArrival();
            LocalTime arrivalTime1 = _stations.get(stationArrival1); //a que horas chega a estacao de termino
            LocalTime arrivalTime2 = _stations.get(stationArrival2);
            return arrivalTime1.compareTo(arrivalTime2);
        }
    }

    //Compara o tempo de chegada a estacao de termino,sendo a mesma estacao para os dois servicos
    private class SameStationArrivalTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            String stationArrival1 = service1.getServiceArrival(); //estacao de onde termina o servico
            String stationArrival2 = stationArrival1;
            LocalTime arrivalTime1 = _stations.get(stationArrival1); //a que horas chega a estacao de termino
            LocalTime arrivalTime2 = _stations.get(stationArrival2);
            return arrivalTime1.compareTo(arrivalTime2);
        }
    }

    //Compara o tempo de partida da estacao de inicio, independepndentemente da estacao de cada servico
    private class DepartureTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            String stationDeparture1 = service1.getServiceDeparture();
            String stationDeparture2 = service2.getServiceDeparture();
            LocalTime departureTime1 = _stations.get(stationDeparture1); //a que horas parte da estacao de inicio
            LocalTime departureTime2 = _stations.get(stationDeparture2);
            return departureTime1.compareTo(departureTime2);
        }
    }

    //Compara o tempo de chegada a estacao de termino,sendo a mesma estacao para os dois servicos
    private class SameStationDepartureTimeComparator implements Comparator<Service> {
        public int compare(Service service1, Service service2) {
            String stationDeparture1 = service1.getServiceDeparture();
            String stationDeparture2 = stationDeparture1;
            LocalTime departureTime1 = _stations.get(stationDeparture1); //a que horas parte da estacao de inicio
            LocalTime departureTime2 = _stations.get(stationDeparture2);
            return departureTime1.compareTo(departureTime2);
        }
    }

    /*** SE HOVER CLASS ESTACAO NAO LEIAS ENTAO ISTO FRANCISCO
    Construtor
    public Service(int id, double price, ArrayList<Station> ls) {
        _stations = new HashMap<String, Station>;
        _id = id;
        _price = price;
        for(Station s: ls) {
            _stations.put(s.getName(), s);
        }
    }

    Comparators
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

    ****/
}
