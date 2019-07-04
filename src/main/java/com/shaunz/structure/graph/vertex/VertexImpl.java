package com.shaunz.structure.graph.vertex;

import com.shaunz.structure.graph.edge.Edge;
import com.shaunz.structure.graph.edge.EdgeImpl;

import java.io.Serializable;
import java.util.*;

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
public class VertexImpl<T> implements Vertex<T>, Serializable {
    private static final long serialVersionUID = -1021945695353862003L;
    private static final int HASH_CONSTANT = 5;
    private static final int HASH_INDEX = 3;

    private T label;
    private List<Edge<T>> edgeImplList;
    private Double weight = 0.0;
    private boolean singleVertex = true;

    /*****************/
    private boolean visited;
    private Vertex<T> previousVertex;
    private Double cost;
    private List<Vertex<T>> visitedNeighbors = new ArrayList<>();
    /*****************/

    public VertexImpl(T vertexLabel){
        this(vertexLabel,0.0);
    }

    public VertexImpl(T vertexLabel, Double vertexWeight){
        this.weight = vertexWeight;
        this.label = vertexLabel;
        this.edgeImplList = new LinkedList<>();
        this.visited = false;
        this.previousVertex = null;
        this.cost = 0.0;
    }

    public VertexImpl(Vertex<T> vertex){
        this.weight = vertex.getWeight();
        this.label = vertex.getLabel();
        this.edgeImplList = new LinkedList<>();

        this.visited = false;
        this.previousVertex = null;
        this.cost = 0.0;
    }

    public void clearVisitedNeighbors(){
        this.visitedNeighbors.clear();
    }

    public void addVisitedNeighbor(Vertex<T> neighbor){
        if(!visitedNeighbors.contains(neighbor)){
            this.visitedNeighbors.add(neighbor);
        }
    }

    public List<Vertex<T>> getVisitedNeighbors(){
        return this.visitedNeighbors;
    }

    public boolean addEdge(Edge<T> edge) {
        if(edgeImplList == null){
            edgeImplList = new LinkedList<>();
        }
        return edgeImplList.add(edge);
    }

    public void unVisiteAllNeighbor(){
        Iterator<Vertex<T>> neighborIterator = getNeighborIterator();
        while (neighborIterator.hasNext()){
            neighborIterator.next().unVisit();
        }
    }

    private class NeighborIterator implements  Iterator<Vertex<T>>{
        Iterator<Edge<T>> edgesIterable;
        private NeighborIterator(){
            this.edgesIterable = edgeImplList.iterator();
        }

        public boolean hasNext() {
            return edgesIterable.hasNext();
        }
        public Vertex<T> next() {
            Vertex<T> nextNeighbor = null;
            if(edgesIterable.hasNext()){
                Edge<T> edgeImpl2NextNeighbor = edgesIterable.next();
                nextNeighbor = edgeImpl2NextNeighbor.getEndVertex();
            } else
                throw new NoSuchElementException();
            return nextNeighbor;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class EdgeWeightIterator implements  Iterator{

        private Iterator<Edge<T>> edgesIterable;
        private EdgeWeightIterator(){
            this.edgesIterable = edgeImplList.iterator();
        }
        public boolean hasNext() {
            return edgesIterable.hasNext();
        }

        public Object next() {
            Double result;
            if(edgesIterable.hasNext()){
                Edge<T> edgeImpl = edgesIterable.next();
                result = edgeImpl.getWeight();
            } else
                throw new NoSuchElementException();

            return (Object) result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public double getWeigh2Neighbor(Vertex<T> neighborVertex){
        double result = Double.POSITIVE_INFINITY;
        Edge<T> tmpEdge;
        for (int i=0,size=edgeImplList.size();i<size;i++){
            tmpEdge = edgeImplList.get(i);
            if(neighborVertex.equals(tmpEdge.getEndVertex())){
                result = tmpEdge.getWeight();
                break;
            }
        }
        return result;
    }

    public boolean hasPredecessor(){
        return this.previousVertex != null;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public T getLabel() {
        return label;
    }

    public void setLabel(T label) {
        this.label = label;
    }

    public List<Edge<T>> getEdgeImplList() {
        return edgeImplList;
    }

    public void setEdgeImplList(List<Edge<T>> edgeImplList) {
        this.edgeImplList = edgeImplList;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Vertex<T> getPreviousVertex() {
        return previousVertex;
    }

    public void setPreviousVertex(Vertex<T> previousVertex) {
        this.previousVertex = previousVertex;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setSingleVertex(boolean flag){
        this.singleVertex = flag;
    }

    @Override
    public boolean isSingleVertex() {
        return singleVertex;
    }

    @Override
    public Integer getNeighborsCnt() {
        return this.edgeImplList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexImpl<T> vertexImpl = (VertexImpl<T>) o;
        return label.equals(vertexImpl.label);
    }

    @Override
    public int hashCode() {
        int hashCode;
        hashCode = label.hashCode()*HASH_INDEX+HASH_CONSTANT;
        return hashCode;
    }

    @Override
    public String toString() {
        return "VertexImpl{" +
                "label=" + label.toString() +
                '}';
    }

    public void visit() {
        this.visited = true;
    }

    public void unVisit() {
        this.visited = false;
    }

    public boolean connect(Vertex<T> endVertex, Double edgeWeight) {
        boolean result = false;
        if(!this.equals(endVertex)){
            Iterator<Vertex<T>> neighbors = this.getNeighborIterator();
            boolean duplicateEdge = false;
            while(!duplicateEdge && neighbors.hasNext()){
                Vertex<T> nextNeighbor = neighbors.next();
                if(endVertex.equals(nextNeighbor)){
                    duplicateEdge = true;
                    break;
                }
            }
            if(!duplicateEdge){
                edgeImplList.add(new EdgeImpl<T>(endVertex,edgeWeight));
                result = true;
            }
        }
        return result;
    }

    public boolean connect(Vertex<T> endVertex) {
        return connect(endVertex,0.0);
    }

    public Iterator<Vertex<T>> getNeighborIterator() {
        return new NeighborIterator();
    }

    public Iterator getWeightIterator() {
        return new EdgeWeightIterator();
    }

    public boolean hasNeighbor() {
        return !edgeImplList.isEmpty();
    }

    public Vertex<T> getUnvisitedNeighbor() {
        Vertex<T> result = null;
        Iterator<Vertex<T>> neighbors = getNeighborIterator();
        while (neighbors.hasNext() && result == null){
            Vertex<T> nextNeighbor = neighbors.next();
            if(!nextNeighbor.isVisited())
                result = nextNeighbor;
        }
        return result;
    }

    public void setPredecessor(Vertex<T> predecessor) {
        setPreviousVertex(predecessor);
    }

    public Vertex<T> getPredecessor() {
        return getPreviousVertex();
    }
}
