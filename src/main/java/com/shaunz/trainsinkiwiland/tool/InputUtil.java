package com.shaunz.trainsinkiwiland.tool;

import com.shaunz.trainsinkiwiland.entity.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputUtil {
    public static List<Graph> readFromArgs(String[] args){
        List<Graph> result = new ArrayList<>();
        if(args != null && args.length > 0){
            Arrays.asList(args).stream().forEach(arg -> {
                if(arg.indexOf(",") >= 0){
                    String[] subArr = arg.split(",");
                    Arrays.asList(subArr).stream().forEach(sub -> {
                        result.add(convert2Route(sub));
                    });
                } else {
                    result.add(convert2Route(arg));
                }
            });
        }
        return result;
    }

    public static Graph convert2Route(String route){
        String from,to;
        int weight;
        from = route.substring(0,Constants.stationNmLength);
        to = route.substring(Constants.stationNmLength,2*Constants.stationNmLength);
        try{
            weight = Integer.valueOf(route.substring(3*Constants.stationNmLength)).intValue();
        } catch (Exception e){
            weight = 0;
        }

        return new Graph(from,to,weight);
    }
}
