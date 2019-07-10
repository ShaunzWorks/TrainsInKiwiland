package com.shaunz.structure.graph;

import com.shaunz.structure.graph.vertex.Vertex;
import com.shaunz.structure.graph.vertex.VertexImpl;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DirectedGraph<T> extends AbstractDirectedGraph<T> {
    private static final long serialVersionUID = -1190160436962485575L;

    public DirectedGraph(){
        vertices = new LinkedHashMap<T, Vertex<T>>();
    }

    @Deprecated
    public boolean add(T from, T to, Double weight) {
        return false;
    }

    public boolean add(T from, T to) {
        if(!this.vertices.containsKey(from)){
            this.vertices.put(from,new VertexImpl<>(from));
        }
        if(!this.vertices.containsKey(to)){
            this.vertices.put(to,new VertexImpl<>(to));
        }
        return addEdge(from,to);
    }

    @Override
    public boolean addEdge(T begin, T end) {
        boolean result = false;
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        if(beginVertex != null && endVertex != null);
            result = beginVertex.connect(endVertex,1.0);
        if(result)
            edgeCount.getAndIncrement();
        return result;
    }

    @Override
    @Deprecated
    public boolean addEdge(T begin, T end, Double edgeWeight) {
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
        resetVertices();
        boolean searchDone = false;
        Queue<Vertex<T>> vertexQueue = new LinkedList<Vertex<T>>();
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        beginVertex.visit();
        vertexQueue.offer(beginVertex);

        while (!searchDone && !vertexQueue.isEmpty()){
            Vertex<T> frontVertex = vertexQueue.poll();
            Iterator<Vertex<T>> neighbors = frontVertex.getNeighborIterator();
            while (!searchDone && neighbors.hasNext()){
                Vertex<T> nextNeighbor = neighbors.next();
                if(!nextNeighbor.isVisited()){
                    nextNeighbor.visit();
                    nextNeighbor.setPredecessor(frontVertex);
                    nextNeighbor.setCost(frontVertex.getCost() + 1);
                    vertexQueue.offer(nextNeighbor);
                }
                if(nextNeighbor.equals(endVertex))
                    searchDone = true;
            }
        }

        double pathLengh = endVertex.getCost();
        path.push(endVertex.getLabel());

        Vertex<T> vertex = endVertex;
        while (vertex.hasPredecessor()){
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }

        return pathLengh;
    }

}
