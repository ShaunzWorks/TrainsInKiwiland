package com.shaunz.trainsinkiwiland.tools;

import com.shaunz.structure.graph.Graph;
import com.shaunz.structure.graph.factory.GraphFactory;
import com.shaunz.structure.graph.factory.GraphFactoryImpl;
import com.shaunz.trainsinkiwiland.tool.InputUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class InpututilTest {
    Graph<String> graph;

    @Before
    public void prepare(){
        GraphFactory factory = GraphFactoryImpl.getInstance();
        graph = factory.build(GraphFactory.GraphType.DIRECTED_RING_WEIGHT_GRAPH);
    }
    @Test
    public void readFromFile(){
        graph = InputUtil.readFromFile(graph);
    }
}
