package com.shaunz.structure.graph.vertex;

import com.shaunz.structure.graph.edge.Edge;

import java.util.Iterator;
import java.util.List;

/**
 *  _____   _   _       ___   _   _   __   _   ______  _          __  _____   _____    _   _
 * /  ___/ | | | |     /   | | | | | |  \ | | |___  / | |        / / /  _  \ |  _  \  | | / /
 * | |___  | |_| |    / /| | | | | | |   \| |    / /  | |  __   / /  | | | | | |_| |  | |/ /
 * \___  \ |  _  |   / / | | | | | | | |\   |   / /   | | /  | / /   | | | | |  _  /  | |\ \
 *  ___| | | | | |  / /  | | | |_| | | | \  |  / /__  | |/   |/ /    | |_| | | | \ \  | | \ \
 * /_____/ |_| |_| /_/   |_| \_____/ |_|  \_| /_____| |___/|___/     \_____/ |_|  \_\ |_|  \_\
 * @author dengxiong90@foxmail.com
 * @param <T>
 * @since 2019.Q1
 *
 *Vertex is one of the basic part of the graph, and has some necessary method which service for the graph operation, such as DFS,BFS,Topology Sort...<br/>
 *Variable Label is provided for some customized data structure which means it's able to include any POJO class into a graph
 */
public interface Vertex<T> {
    /**
     *Label is used to store the meta data for a vertex, any type is available which means you can customize the data for vertex
     * @return
     */
    T getLabel();

    Double getWeight();

    Integer getNeighborsCnt();

    boolean isSingleVertex();

    void clearVisitedNeighbors();

    void addVisitedNeighbor(Vertex<T> neighbor);

    List<Vertex<T>> getVisitedNeighbors();

    void unVisiteAllNeighbor();

    /**
     * Visit this vertex
     */
    void visit();

    /**
     * Reset the visit status for this vertex
     */
    void unVisit();

    /**
     * Check if this vertex is visited
     * @return
     */
    boolean isVisited();


    /**
     * Get the weight between this vertex and specified vertex, will return positive infinity if the two vertexes not connected
     * @param neighborVertex
     * @return
     */
    public double getWeigh2Neighbor(Vertex<T> neighborVertex);

    /**
     * Connect specified vertex to this vertex with provided weight, this method for weighted graph
     * @param endVertex
     * @param edgeWeight
     * @return
     */
    boolean connect(Vertex<T> endVertex, Double edgeWeight);

    /**
     * Connected specified vertex to this vertex, this methor for none-weighted graph
     * @param endVertex
     * @return
     */
    boolean connect(Vertex<T> endVertex);

    /**
     * Get the Iterator of the neighbors for this vertex
     * @return
     */
    Iterator<Vertex<T>> getNeighborIterator();

    /**
     * Get the Iterator for the weight of all neighbors for this vertex
     * @return
     */
    Iterator getWeightIterator();

    /**
     * Check if this vertex has at least one neighbor
     * @return
     */
    boolean hasNeighbor();

    /**
     * Get an unvisited neighbor of this vertex
     * Wil return null if there's no neighbor is unvisited
     * @return
     */
    Vertex<T> getUnvisitedNeighbor();

    /**
     *
     * @return
     */
    boolean hasPredecessor();
    void setPredecessor(Vertex<T> predecessor);
    Vertex<T> getPredecessor();

    void setCost(Double newCost);
    Double getCost();

    boolean addEdge(Edge<T> edge);

}
