package mmt;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class Service {
    private int _id;
    private double _price;
    private HashMap<String, Station> _stations;


    public Service(int id, double price, List<Station> ls) {
        /*_stations = new TreeMap<String, Station>();

        _id = id;
        _price = price;
        for(Station s: ls) {
            _stations.put(s.getName(), s);
        }*/

    }
}
