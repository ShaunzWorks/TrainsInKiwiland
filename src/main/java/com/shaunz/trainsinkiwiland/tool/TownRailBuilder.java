package com.shaunz.trainsinkiwiland.tool;

import com.shaunz.trainsinkiwiland.entity.Graph;
import com.shaunz.trainsinkiwiland.entity.Station;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TownRailBuilder {
    private Map<String,Station> allStations;

    public Map<String, Station> getAllStations() {
        return allStations;
    }

    public void setAllStations(Map<String, Station> allStations) {
        this.allStations = allStations;
    }

    public void buildRailWayRoute(List<Graph> graphs){
        allStations = new HashMap<String,Station>();
        graphs.stream().forEach(graph ->{
            if(allStations.containsKey(graph.getFrom())){
                allStations.put(graph.getFrom(),new Station(graph.getFrom()));
            }
            if(allStations.containsKey(graph.getTo())){
                allStations.put(graph.getTo(),new Station(graph.getTo()));
            }
        });
        graphs.stream().forEach(graph -> {
                allStations.get(graph.getFrom()).addNearByStation(allStations.get(graph.getTo()), graph.getWeight());
            }
        );
    }

}
