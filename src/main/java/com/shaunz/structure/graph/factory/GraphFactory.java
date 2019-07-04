package com.shaunz.structure.graph.factory;

import com.shaunz.structure.graph.Graph;

public interface GraphFactory {
    static enum GraphType{
        DIRECTED_GRAPH("com.shaunz.structure.graph.DirectedGraph"),
        DIRECTED_RING_GRAPH("com.shaunz.structure.graph.DirectedRingGraph"),
        DIRECTED_WEIGHT_GRAPH("com.shaunz.structure.graph.DirectedWeightGraph"),
        DIRECTED_AROUND_WEIGHT_GRAPH("com.shaunz.structure.graph.DirectedRingWeightGraph");

        public final String className;
        private GraphType(String className){
            this.className = className;
        }
    }

    <T> Graph<T> build(GraphType graphType);

    GraphType getGraphType(Class clazz);
}
