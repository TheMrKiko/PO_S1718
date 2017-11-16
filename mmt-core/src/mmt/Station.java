package mmt;
import java.time.LocalTime;
import java.util.ArrayList;

public class Station {
    private String _name;
    //aray de horas em que o index de cada posicao corresponde a um servico
    private ArrayList<LocalTime> _partidas;

    Station(String name, LocalTime time) {
        _name = name;
        _partidas = new ArrayList<Integer, LocalTime>();
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
