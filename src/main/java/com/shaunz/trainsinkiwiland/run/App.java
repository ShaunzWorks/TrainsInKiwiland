package com.shaunz.trainsinkiwiland.run;

import com.shaunz.trainsinkiwiland.tool.InputUtil;

import java.util.List;

public class App {
    public static void main(String[] args){
        List<Graph> graphs = null;
        //Retrieve input
        if(args != null && args.length > 0){
            graphs = InputUtil.readFromArgs(args);
        } else {
            //Syso
        }

        //Build the route map for whole town
        TownRailBuilder townRailBuilder = TownRailBuilderFactory.getInstace()
                .getTownRailBuilder();
        townRailBuilder.buildRailWayRoute(graphs);



    }
}
