package com.shaunz.structure.graph;

import com.shaunz.structure.graph.vertex.Vertex;

import java.util.*;

public class DirectedRingWeightGraph<T> extends AbstractDirectedGraph<T> {

    public DirectedRingWeightGraph() {
        vertices = new LinkedHashMap<T, Vertex<T>>();
    }

    @Override
    public boolean addEdge(T begin, T end, Double edgeWeight) {
        boolean result = false;
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        if(beginVertex != null && endVertex != null);
        result = beginVertex.connect(endVertex,edgeWeight);
        if(result)
            edgeCount.getAndIncrement();
        return result;
    }

    @Override
    @Deprecated
    public boolean addEdge(T begin, T end) {
        return false;
    }

    /**
     * Get the shortest path from begin to end
     *
     * @param begin
     * @param end
     * @param path
     * @return
     */
    @Override
    public Double getShortestPath(T begin, T end, Stack<T> path) {
        Vertex<T> endVertex = vertices.get(end);

        List<Stack<T>> paths = getAllPath(begin,end);
        Double shortestWight = Double.POSITIVE_INFINITY;
        Stack<T> tmpPath;
        Stack<T> shortestPath = new Stack<>();
        for (int i = 0,size = paths.size(); i < size; i++){
            resetVertices();
            tmpPath = (Stack<T>)paths.get(i).clone();
            List<Vertex<T>> vertexArr = new ArrayList<>();
            while (!tmpPath.isEmpty()){
                vertexArr.add(vertices.get(tmpPath.pop()));
            }
            for (int j = 1, s = vertexArr.size(); j < s; j++){
                Double cost = vertexArr.get(j-1).getCost();
                cost += vertexArr.get(j-1).getWeigh2Neighbor(vertexArr.get(j));
                vertexArr.get(j).setCost(cost);
            }
            shortestWight = shortestWight < endVertex.getCost()?shortestWight:endVertex.getCost();
            if(shortestWight == endVertex.getCost()){
                shortestPath = paths.get(i);
            }

        }

        tmpPath = new Stack<>();
        while (!shortestPath.isEmpty()){
            tmpPath.push(shortestPath.pop());
        }

        while (!tmpPath.isEmpty()){
            path.push(tmpPath.pop());
        }

        return shortestWight;
    }

}
