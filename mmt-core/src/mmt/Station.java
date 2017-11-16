package mmt;
import java.time.LocalTime;

public class Station {
    private string _name;
    //uma estacao tem um conjunto de partidas a uma dada hora
    //cada partida corresponde a parte de um servico
    private Map<int, LocalTime> _partidas;
    
    Station(String name, LocalTime time) {
        _name = name;
        _partidas = new TreeMap<int, _partidas>();
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
