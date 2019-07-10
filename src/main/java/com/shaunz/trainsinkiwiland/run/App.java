package com.shaunz.trainsinkiwiland.run;

import com.shaunz.structure.graph.Graph;
import com.shaunz.structure.graph.factory.GraphFactory;
import com.shaunz.structure.graph.factory.GraphFactoryImpl;
import com.shaunz.trainsinkiwiland.tool.InputUtil;

import java.util.List;

public class App {
    public static void main(String[] args){

        GraphFactory factory = GraphFactoryImpl.getInstance();
        Graph<String> graph = factory.build(GraphFactory.GraphType.DIRECTED_RING_WEIGHT_GRAPH);
        //Retrieve input
        if(args != null && args.length > 0){
            graph = InputUtil.readFromArgs(args,graph);
        } else {
            graph = InputUtil.readFromFile(graph);
        }

        Town kiwiland = new Town(graph);
        kiwiland.execute();




    }
}
