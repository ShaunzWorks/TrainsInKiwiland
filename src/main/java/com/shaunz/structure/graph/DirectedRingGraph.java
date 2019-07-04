package com.shaunz.structure.graph;

import com.shaunz.structure.graph.vertex.Vertex;

import java.util.*;

public class DirectedRingGraph<T> extends AbstractDirectedGraph<T>{
    public DirectedRingGraph() {
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
        Graph<T> cloningGraph;
        try {
            cloningGraph = (Graph<T>)this.clone();
            cloningGraph.init();
        } catch (CloneNotSupportedException e){
            return Double.POSITIVE_INFINITY;
        }

        Queue<Vertex<T>> vertexQueue = new LinkedList<Vertex<T>>();
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);
        Set<T> avaliableVerte = new HashSet<>();

        resetVertices();
        beginVertex.visit();


        path.push(endVertex.getLabel());

        Vertex<T> vertex = endVertex;
        while (vertex.hasPredecessor()){
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }

        return endVertex.getCost();
    }
}
