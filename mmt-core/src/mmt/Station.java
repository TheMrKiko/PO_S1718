package mmt;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public class Station {
    private String _name;
    //uma estacao tem um conjunto de partidas a uma dada hora
    //cada partida corresponde a parte de um servico
    private TreeMap<Integer, LocalTime> _partidas;
    
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
        _partidas.put(serviceID, horasPartida);
    }
}
