package com.shaunz.trainsinkiwiland.tool;

import com.shaunz.trainsinkiwiland.entity.Route;
import com.shaunz.trainsinkiwiland.entity.Station;

import java.util.*;

/**
 * I'm a railway runner, I will run the whole railway in this town and provide some information you need<br/>
 *
 * @author dengxiong90@foxmail.com
 */
public class RailWayRunner {

    /**
     * I will find out if two stations connected
     * @param from The station where you departure
     * @param destination The station where's your destination
     * @param startName Please also provide the station name of where you departure
     * @param selfReachCnt Please input 0 here
     * @return
     */
    public static boolean isAble2Reach(Station from,Station destination,String startName, int selfReachCnt){
        boolean reachable = false;
        if(from.getName().equals(startName)){
            selfReachCnt ++;
        }
        if(selfReachCnt > 1){
            return false;
        }
        List<Station> nearByStations = from.getNearByStations();

        if(nearByStations != null && nearByStations.size() > 0) {
            if(from.equals(destination)){
                reachable = true;
            } else {
                for(int i = 0,size = nearByStations.size(); i< size; i++){
                    reachable = isAble2Reach(nearByStations.get(i),destination,startName,selfReachCnt);
                    if(reachable){
                        break;
                    }
                }
            }
        }
        return reachable;
    }

    /**
     * I will record all routes which would connect the tw0 stations you provided
     * @param from The station where you departure
     * @param destination The station where's your destination
     * @param startName Please also provide the station name of where you departure
     * @param selfReachCnt Please input 0 here
     * @param reachableRoutes Please give me a paper({@link List<String>}) to me to note the result
     * @param tmpRoute I also need a draft pager({@link StringBuffer})
     */
    public static void recordRoute(Station from, Station destination, String startName,
                                   int selfReachCnt, List<Route> reachableRoutes, StringBuffer tmpRoute){
        tmpRoute.append(from.getName());
        if(from.getName().equals(startName)){
            selfReachCnt ++;
        }
        if(selfReachCnt > 1){
            return;
        }
        List<Station> nearByStations = from.getNearByStations();

        if(nearByStations != null && nearByStations.size() > 0) {
            if(from.equals(destination)){
                reachableRoutes.add(new Route(tmpRoute.toString()));
            } else {
                for(int i = 0,size = nearByStations.size(); i< size; i++){
                    recordRoute(nearByStations.get(i),destination,startName,selfReachCnt,reachableRoutes,tmpRoute);
                }
            }
        }
        return;
    }

    /**
     * I will calculate the distance for the given route
     * @param reachableRoute A given route
     * @param allStations The station map
     */
    public static void countDistance(Route reachableRoute, Map<String,Station> allStations){
        int result = 0;
        List<String> stationLst = new ArrayList<>();
        Station var_from;
        Station var_to;
        if(reachableRoute.getRoute().length() % Constants.stationNmLength == 0){
            int stopSize = reachableRoute.getRoute().length()/Constants.stationNmLength - 1;
            for (int i = 0; i <= stopSize; i++){
                stationLst.add(reachableRoute.getRoute().substring(i*Constants.stationNmLength,i+Constants.stationNmLength));
            }
            for (int j = 0, size = stationLst.size(); j < size -1 ; j++){
                var_from = allStations.get(stationLst.get(j));
                var_to = var_from.findNearBy(stationLst.get(j+1));
                result += var_from.getDistances().get(var_to.getName());
            }
        }
        reachableRoute.setTotalDistance(result);
    }

    /**
     * I will find out the min-distance route of the given routes
     * @param reachableRoutes All reachableRoutes
     * @param allStations The station map
     * @return
     */
    public static Route minDistanceRoute(List<Route> reachableRoutes, Map<String,Station> allStations){
        Map<String,Integer> map = new HashMap<>();
        reachableRoutes.stream().sorted((o1,o2) -> o1.getTotalDistance() - o2.getTotalDistance());
        return reachableRoutes.get(0);
    }
}
