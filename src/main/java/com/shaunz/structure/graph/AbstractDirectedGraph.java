package com.shaunz.structure.graph;

import com.shaunz.structure.graph.factory.GraphFactory;
import com.shaunz.structure.graph.factory.GraphFactoryImpl;
import com.shaunz.structure.graph.edge.EdgeImpl;
import com.shaunz.structure.graph.vertex.Vertex;
import com.shaunz.structure.graph.vertex.VertexImpl;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractDirectedGraph<T> implements Graph<T>, Serializable,Cloneable {
    private static final long serialVersionUID = -1190160436962485575L;

    protected Map<T, Vertex<T>> vertices;
    protected AtomicInteger edgeCount = new AtomicInteger(0);

    protected Map<T,Integer> strongComponent;
    protected Graph<T> oppositedDirectionGraph;

    public void addVertex(T vertxLabel){
        vertices.put(vertxLabel, new VertexImpl<>(vertxLabel));
    }

    public abstract boolean addEdge(T begin, T end, Double edgeWeight);

    public abstract boolean addEdge(T begin, T end);

    public void init(){
        if(oppositedDirectionGraph == null){
            oppositedDirectionGraph = genOppositeDirection();
        }
        if(strongComponent == null){
            strongComponent = genStrongComponents(oppositedDirectionGraph);
        }
    }

    /**
     * Opposite direction for this graph
     *
     * @return
     */
    @Override
    public Graph<T> getOppositeDirection() {
        return oppositedDirectionGraph;
    }

    /**
     * @return
     */
    @Override
    public Map<T, Integer> getStrongComponents() {
        return strongComponent;
    }

    /**
     * Get the shortest path from begin to end
     *
     * @param begin
     * @param end
     * @param path
     * @return
     */
    public abstract Double getShortestPath(T begin, T end, Stack<T> path);

    public void setVertices(Map<T, Vertex<T>> vertices) {
        this.vertices = vertices;
        vertices.forEach((t,vertex) -> {
            edgeCount.getAndAdd(vertex.getNeighborsCnt());
        });
    }



    /**
     * Get all the path from begin to end
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public List<Stack<T>> getAllPath(T begin, T end) {
        resetVertices();

        Stack<Vertex<T>> vertexStack = new Stack<>();
        List<Stack<T>> paths = new ArrayList<>();
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        List<Stack<Vertex<T>>> vertexPaths = new ArrayList<>();
        Stack<Vertex<T>> visitedVertex = new Stack<>();

        Stack<Vertex<T>> vertexPath;
        vertexStack.push(beginVertex);
        beginVertex.visit();
        while (!vertexStack.isEmpty()){
            Vertex<T> topVertex = vertexStack.peek();
            visitedVertex.push(topVertex);
            Vertex<T> neighbor = getAdjUnvisitedNeighbor(vertexStack,beginVertex);

            if(neighbor != null){
                topVertex.addVisitedNeighbor(neighbor);
                neighbor.visit();
                vertexStack.push(neighbor);
            } else {
                vertexStack.pop();
                topVertex.clearVisitedNeighbors();
            }

            if(!vertexStack.isEmpty() && endVertex.equals(neighbor)){
                vertexPath = (Stack<Vertex<T>>)vertexStack.clone();
                vertexPaths.add(vertexPath);
                neighbor.unVisit();
                vertexStack.pop();
            }
        }

        Stack<T> path;
        for (int i = 0,size = vertexPaths.size();i<size;i++){
            Stack<Vertex<T>> vPath = vertexPaths.get(i);
            path = new Stack<>();
            while (!vPath.isEmpty()){
                path.push(vPath.pop().getLabel());
            }
            paths.add(path);
        }

        return paths;
    }

    private Vertex<T> getAdjUnvisitedNeighbor(Stack<Vertex<T>> vertexStack,Vertex<T> beginVertex){
        Vertex<T> topVertex = vertexStack.peek();
        Iterator<Vertex<T>> neighborIterator = topVertex.getNeighborIterator();
        while (neighborIterator.hasNext()){
            Vertex<T> neighbor = neighborIterator.next();
            if((!vertexStack.contains(neighbor) || beginVertex.equals(neighbor)) && !topVertex.getVisitedNeighbors().contains(neighbor)){
                return neighbor;
            }
        }

        return null;
    }

    /**
     * BFS
     *
     * @param origin vertex which start
     * @return
     */
    public Queue<T> getBreadthFirstTraversal(T origin) {
        resetVertices();
        Queue<Vertex<T>> vertexQueue = new LinkedList<Vertex<T>>();
        Queue<T> traversalOrder = new LinkedList<T>();
        Vertex<T> originVertex = this.vertices.get(origin);
        originVertex.visit();
        traversalOrder.offer(originVertex.getLabel());
        vertexQueue.offer(originVertex);

        while (!vertexQueue.isEmpty()){
            Vertex<T> frontVertex = vertexQueue.poll();
            Iterator<Vertex<T>> neighbors = frontVertex.getNeighborIterator();
            neighbors.forEachRemaining(neighbor -> {
                if(!neighbor.isVisited()){
                    neighbor.visit();
                    traversalOrder.offer(neighbor.getLabel());
                    vertexQueue.offer(neighbor);
                }
            });
        }
        return traversalOrder;
    }

    /**
     * DFS
     *
     * @param origin
     * @return
     */
    public Queue<T> getDepthFirstTraversal(T origin) {
        resetVertices();
        LinkedList<Vertex<T>> vertexStack = new LinkedList<>();
        Queue<T> traversalOrder = new LinkedList<>();

        Vertex<T> originVertex = vertices.get(origin);
        originVertex.visit();
        vertexStack.push(originVertex);
        traversalOrder.offer(originVertex.getLabel());

        while (!vertexStack.isEmpty()){
            Vertex<T> topVertex = vertexStack.peek();
            Vertex<T> nextNeighbor = topVertex.getUnvisitedNeighbor();

            if(nextNeighbor != null){
                nextNeighbor.visit();
                vertexStack.push(nextNeighbor);
                traversalOrder.offer(nextNeighbor.getLabel());
            } else {
                vertexStack.pop();
            }
        }
        return traversalOrder;
    }

    public Stack<T> getTopologySort() {
        resetVertices();

        Stack<T> result = new Stack<>();
        Stack<Vertex<T>> vertexStack = new Stack<>();

        Iterator<Vertex<T>> vertexIterator = vertices.values().iterator();
        while (vertexIterator.hasNext()){
            Vertex<T> originVertex = vertexIterator.next();
            if(!originVertex.isVisited()){
                originVertex.visit();
                vertexStack.push(originVertex);
                while (!vertexStack.isEmpty()){
                    Vertex<T> frontVertex = vertexStack.peek();
                    Vertex<T> neighbor = frontVertex.getUnvisitedNeighbor();
                    if(neighbor != null){
                        neighbor.visit();
                        vertexStack.push(neighbor);
                    } else {
                        result.push(frontVertex.getLabel());
                        vertexStack.pop();
                    }
                }
            }
        }

        return result;
    }

    /**
     * Opposite direction for this graph
     *
     * @return
     */
    public Graph<T> genOppositeDirection() {
        Map<T,Vertex<T>> oppositedVertices = new LinkedHashMap<>();

        vertices.entrySet().stream().forEach(t ->{
            Vertex<T> frontVertex = t.getValue();
            if(!oppositedVertices.containsKey(frontVertex.getLabel())){
                oppositedVertices.put(frontVertex.getLabel(),new VertexImpl<>(frontVertex.getLabel()));
            }
            frontVertex.getNeighborIterator().forEachRemaining(backVertex -> {
                Vertex<T> newFrontVertex;
                Double weight;

                weight = frontVertex.getWeigh2Neighbor(backVertex);
                if(oppositedVertices.containsKey(backVertex.getLabel())){
                    newFrontVertex = oppositedVertices.get(backVertex.getLabel());
                    newFrontVertex.addEdge(new EdgeImpl<>(oppositedVertices.get(frontVertex.getLabel()),weight));
                } else {
                    newFrontVertex = new VertexImpl<>(backVertex.getLabel());
                    newFrontVertex.addEdge(new EdgeImpl<>(oppositedVertices.get(frontVertex.getLabel()),weight));
                    oppositedVertices.put(newFrontVertex.getLabel(),newFrontVertex);
                }
            });
        });

        Graph<T> oppositeGraph = createNewGraph(oppositedVertices);
        return oppositeGraph;
    }

    private Graph<T> createNewGraph(Map<T,Vertex<T>> vertices){
        GraphFactory graphFactory = GraphFactoryImpl.getInstance();
        Graph<T> graph = graphFactory.build(graphFactory.getGraphType(this.getClass()));
        graph.setVertices(vertices);
        return graph;
    }

    public Map<T,Integer> genStrongComponents(Graph<T> oppositeGraph) {
        resetVertices();

        Map<T,Integer> strongComponents = new HashMap<>();
        Stack<T> oppositeTypologyOrder = oppositeGraph.getTopologySort();

        LinkedList<Vertex<T>> vertexStack = new LinkedList<Vertex<T>>();
        Integer componentIndex = 0;

        while (!oppositeTypologyOrder.isEmpty()){
            Vertex<T> vertex = vertices.get(oppositeTypologyOrder.pop());
            if(!vertex.isVisited()){
                vertexStack.push(vertex);
                componentIndex ++;
            }
            while (!vertexStack.isEmpty()){
                Vertex<T> topVertex = vertexStack.peek();
                Vertex<T> nextNeighbor = topVertex.getUnvisitedNeighbor();

                topVertex.visit();
                strongComponents.put(topVertex.getLabel(),componentIndex);

                if(nextNeighbor == null){
                    vertexStack.pop();
                } else {
                    vertexStack.push(nextNeighbor);
                }
            }

        }
        return strongComponents;
    }


    public Double traverse(Vertex<T>... vertexes) {
        Double result = 0.0;
        Vertex<T> vertex;
        if (vertexes.length > 1) {
            for (int i=1,size=vertexes.length;i<size;i++){
                vertex = vertexes[i];
                double weight = vertexes[i-1].getWeigh2Neighbor(vertexes[i]);
                result += weight;
            }
        }
        return result;
    }

    /**
     * Reset some parameters in vertex list
     */
    public void resetVertices() {
        this.vertices.keySet().stream().forEach(key -> {
            Vertex<T> vertex = this.vertices.get(key);
            vertex.unVisit();
            vertex.setCost(0.0);
            vertex.setPredecessor(null);
        });
    }

    public Map<T, Vertex<T>> getVertices() {
        return vertices;
    }

    public AtomicInteger getEdgeCount() {
        return edgeCount;
    }

    public int getVertexCount(){
        return vertices == null?0:vertices.size();
    }

    private Vertex<T> getNextTopologyOrder(){
        Vertex<T> nextVertex = null;
        Iterator<Vertex<T>> iterator = vertices.values().iterator();
        boolean found = false;
        while (!found && iterator.hasNext()){
            nextVertex = iterator.next();

            if(!nextVertex.isVisited() && nextVertex.getUnvisitedNeighbor() == null){
                found = true;
            }
        }
        return nextVertex;
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     * <p>
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     * <p>
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     * <p>
     * The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     * <p>
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not
     *                                    support the {@code Cloneable} interface. Subclasses
     *                                    that override the {@code clone} method can also
     *                                    throw this exception to indicate that an instance cannot
     *                                    be cloned.
     * @see Cloneable
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        GraphFactory factory = GraphFactoryImpl.getInstance();
        Graph<T> newGraph = factory.build(factory.getGraphType(this.getClass()));
        Map<T, Vertex<T>> newVertices = new LinkedHashMap<T, Vertex<T>>();
        this.vertices.entrySet().forEach(entry ->{
            T key = entry.getKey();
            Vertex<T> value = entry.getValue();

            Vertex<T> newVertex;
            if(newVertices.containsKey(key)){
                newVertex = newVertices.get(key);
            } else {
                newVertex = new VertexImpl<T>(key);
            }
            value.getNeighborIterator().forEachRemaining(vertex -> {
                Vertex<T> newNeighbor;
                if(newVertices.containsKey(vertex.getLabel())){
                    newNeighbor = newVertices.get(vertex.getLabel());
                } else {
                    newNeighbor = new VertexImpl<>(vertex.getLabel());
                }
                newVertex.addEdge(new EdgeImpl<>(newNeighbor,value.getWeigh2Neighbor(vertex)));
            });
        });
        newGraph.setVertices(newVertices);
        return newGraph;
    }
}