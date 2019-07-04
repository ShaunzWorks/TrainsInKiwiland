package com.shaunz.structure.graph.common;

import com.shaunz.structure.graph.Graph;
import com.shaunz.structure.graph.vertex.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphUtil {
    public static <T> Graph<T> clearRing(Graph<T> graph, Vertex<T> begin, Vertex<T> end){
        Map<T,Vertex<T>> vertices = graph.getVertices();
        Map<T,Integer> strongComponents = graph.getStrongComponents();
        if(strongComponents == null){
            graph.init();
            strongComponents = graph.getStrongComponents();
        }
        Map<Integer, List<T>> ringGroups = new HashMap<>();
        strongComponents.entrySet().forEach(entry -> {
            T label = entry.getKey();
            Integer groupId = entry.getValue();
            List<T> group;
            if(ringGroups.containsKey(groupId)){
                group = ringGroups.get(groupId);
                group.add(label);
            } else {
                group = new ArrayList<>();
                group.add(label);
                ringGroups.put(groupId,group);
            }

        });


        return graph;
    }
}
