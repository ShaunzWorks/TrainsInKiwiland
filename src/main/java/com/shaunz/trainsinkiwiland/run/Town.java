package com.shaunz.trainsinkiwiland.run;

import com.shaunz.structure.graph.Graph;
import com.shaunz.structure.graph.vertex.Vertex;
import com.shaunz.trainsinkiwiland.log.Log;
import com.shaunz.trainsinkiwiland.log.Logger;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Town {
    private Graph<String> graph;
    Map<String, Vertex<String>> vertexMap;
    private Logger logger = new Log();

    public Town(Graph<String> graph) {
        this.graph = graph;
    }

    public void execute(){
        logger.info("########################################################");
        logger.info("###################TrainsInKiwiland#####################");
        question1();
        logger.info("--------------------------------------------------------");
        question2();
        logger.info("--------------------------------------------------------");
        question3();
        logger.info("--------------------------------------------------------");
        question4();
        logger.info("--------------------------------------------------------");
        question5();
        logger.info("--------------------------------------------------------");
        question6();
        logger.info("--------------------------------------------------------");
        question7();
        logger.info("--------------------------------------------------------");
        question8();
        logger.info("--------------------------------------------------------");
        question9();
    }

    public Vertex<String> getVertex(String label){
        if(vertexMap == null){
            vertexMap = graph.getVertices();
        }
        return vertexMap.get(label);

    }

    public void question1(){
        logger.info("1. The distance of the route A->B->C is ");
        Vertex<String> A = getVertex("A");
        Vertex<String> B = getVertex("B");
        Vertex<String> C = getVertex("C");
        Double distance = graph.traverse(A,B,C);
        logger.info("Output #1: " + (distance.isInfinite()?"NO SUCH ROUTE":distance));
    }

    public void question2(){
        logger.info("2. The distance of the route A->D is ");
        Vertex<String> A = getVertex("A");
        Vertex<String> D = getVertex("D");
        Double distance = graph.traverse(A,D);
        logger.info("Output #2: " + (distance.isInfinite()?"NO SUCH ROUTE":distance));
    }

    public void question3(){
        logger.info("3. The distance of the route A->D->C is ");
        Vertex<String> A = getVertex("A");
        Vertex<String> D = getVertex("D");
        Vertex<String> C = getVertex("C");
        Double distance = graph.traverse(A,D,C);
        logger.info("Output #3: " + (distance.isInfinite()?"NO SUCH ROUTE":distance));
    }

    public void question4(){
        logger.info("4. The distance of the route A->E->B->C->D is ");
        Vertex<String> A = getVertex("A");
        Vertex<String> E = getVertex("E");
        Vertex<String> B = getVertex("B");
        Vertex<String> C = getVertex("C");
        Vertex<String> D = getVertex("D");
        Double distance = graph.traverse(A,E,B,C,D);
        logger.info("Output #4: " + (distance.isInfinite()?"NO SUCH ROUTE":distance));
    }

    public void question5(){
        logger.info("5. The distance of the route A->E->D is ");
        Vertex<String> A = getVertex("A");
        Vertex<String> E = getVertex("E");
        Vertex<String> D = getVertex("D");
        Double distance = graph.traverse(A,E,D);
        logger.info("Output #5: " + (distance.isInfinite()?"NO SUCH ROUTE":distance));
    }

    public void question6(){
        logger.info("6. The number of trips starting at C and ending at C with a maximum of 3 stops ");
        List<Stack<String>> allPaths = graph.getAllPath("C","C");
        int result = 0;
        for (int i = 0,size = allPaths.size();i < size;i ++){
            Stack<String> path = allPaths.get(i);
            if(path.size() <= 3){
                result ++;
            }
        }
        logger.info("Output #6: " + result);
    }

    public void question7(){
        logger.info("7. The number of trips starting at A and ending at C with exactly 4 stops");
        List<Stack<String>> allPaths = graph.getAllPath("A","C");
        int result = 0;
        for (int i = 0,size = allPaths.size();i < size;i ++){
            Stack<String> path = allPaths.get(i);
            if(path.size() == 3){
                result ++;
            }
        }
        logger.info("Output #7: " + result);
    }

    public void question8(){
        logger.info("8. The lengh of the shortest route(in terms of distance to travel) from A to C");
        Stack<String> path = new Stack<>();
        Double distance = graph.getShortestPath("A","C", path);
        logger.info("Output #8: " + distance);
    }

    public void question9(){
        logger.info("9. The lengh of the shortest route(in terms of distance to travel) from B to B");
        Stack<String> path = new Stack<>();
        Double distance = graph.getShortestPath("B","B", path);
        logger.info("Output #9: " + distance);
    }

}
