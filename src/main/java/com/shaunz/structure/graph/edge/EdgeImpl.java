package com.shaunz.structure.graph.edge;

import com.shaunz.structure.graph.vertex.Vertex;

import java.io.Serializable;

public class EdgeImpl<T> implements Edge<T>,Serializable {
    private static final long serialVersionUID = 9035922089842627123L;

    private Vertex<T> EndVertex;
    private Double weight;

    public EdgeImpl(Vertex<T> endVertex, Double weight) {
        EndVertex = endVertex;
        this.weight = weight;
    }

    public Vertex<T> getEndVertex() {
        return EndVertex;
    }

    public void setEndVertex(Vertex<T> endVertex) {
        EndVertex = endVertex;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
