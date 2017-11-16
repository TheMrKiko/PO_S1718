package mmt;
import java.time.LocalTime;
import java.util.TreeMap;

public class Station {
    private String _name;
    //mapa de horas em que a chave corresponde ao id do servico
    private TreeMap<Integer,LocalTime> _partidas;

    Station(String name, LocalTime time) {
        _name = name;
        _partidas = new TreeMap<Integer, LocalTime>();
    }

    //rever este metodo na class Service
    public LocalTime getLocalTimeInService(int serviceID) {
        return _partidas.get(serviceID);
    }

    //em principio nao vai ser usado?
    public void setLocalTimeInService(int serviceID,LocalTime horasPartida) {
        _partidas.set(serviceID, horasPartida);
    }

    //GETTERS
    public String getName() {return _name;}
}
