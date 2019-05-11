package com.shaunz.trainsinkiwiland.entity;

import java.util.*;

public class Station implements Comparable<Station>{
    private String name;

    private List<Station> nearByStations;

    private Map<String,Integer> distances;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getNearByStations() {
        return nearByStations;
    }

    public void setNearByStations(List<Station> nearByStations) {
        this.nearByStations = nearByStations;
    }

    public Map<String, Integer> getDistances() {
        return distances;
    }

    public void setDistances(Map<String, Integer> distances) {
        this.distances = distances;
    }

    public int compareTo(Station o) {
        return this.name.compareTo(o.getName());
    }

    public void addNearByStation(Station nearBy, int weight){
        if(nearByStations == null){
            nearByStations = new ArrayList<>();
        }
        if(!nearByStations.contains(nearBy)){
            nearByStations.add(nearBy);
        }
        if(distances == null){
            distances = new HashMap<>();
        }
        if(!distances.containsKey(nearBy.getName())){
            distances.put(nearBy.getName(),weight);
        }
    }

    public Station findNearBy(String name){
        Station nearBy = null;
        for (int i = 0, size = nearByStations.size(); i < size; i++){
            if(nearByStations.get(i).getName().equals(name)){
                nearBy = nearByStations.get(i);
            }
        }
        return nearBy;
    }



}
