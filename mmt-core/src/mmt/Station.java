package mmt;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.TreeMap;

/*import java.time.LocalTime;
import java.util.TreeMap;*/

public class Station implements Serializable {
	  
	private static final long serialVersionUID = 950443075994161568L;
	private String _name;
    //mapa de horas em que a chave corresponde ao id do servico
	public String getName() {
		return _name;
	}


	
    private TreeMap<LocalTime, Integer> _partidas;
    
    public LocalTime getTimeAtStationAtService(Service s) {
    	return _partidas.get(s);
    }
    

    Station(String name, LocalTime time, int serviceID) {
        _name = name;
        _partidas = new TreeMap<LocalTime, Integer>();
        //_partidas.put(serviceID,time);
    }

    //rever este metodo na class Service
    /*****public LocalTime getLocalTimeInService(int serviceID) {
        return _partidas.get(serviceID);
    }

    public void setLocalTimeInService(int serviceID,LocalTime horasPartida) {
        _partidas.put(serviceID, horasPartida);
    }*****/
}
