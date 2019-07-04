package com.shaunz.structure.graph;

import com.shaunz.structure.graph.vertex.Vertex;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public interface Graph<T> {
    void addVertex(T vertxLabel);
    boolean addEdge(T begin, T end, Double edgeWeight);
    boolean addEdge(T begin, T end);
    void setVertices(Map<T, Vertex<T>> vertices);
    Map<T, Vertex<T>> getVertices();

    /**
     * BFS
     * @param origin vertex which start
     * @return
     */
    Queue<T> getBreadthFirstTraversal(T origin);

    /**
     * Get the shortest path from begin to end
     * @param begin
     * @param end
     * @param path
     * @return
     */
    Double getShortestPath(T begin, T end, Stack<T> path);

    /**
     * Get all the path from begin to end
     * @param begin
     * @param end
     * @return
     */
    List<Stack<T>>  getAllPath(T begin, T end);

    /**
     * DFS
     * @param origin
     * @return
     */
    Queue<T> getDepthFirstTraversal(T origin);

    /**
     * Get the topology sort for this graph
     * @return
     */
    Stack<T> getTopologySort();

    /**
     * Opposite direction for this graph
     * @return
     */
    Graph<T> getOppositeDirection();

    /**
     *
     * @return
     */
    Map<T,Integer> getStrongComponents();

    Double traverse(Vertex<T>... vertexes);

    /**
     * Reset some parameters in vertex list
     */
    void resetVertices();

    void init();
}
