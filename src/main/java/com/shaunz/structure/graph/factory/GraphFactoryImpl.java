package com.shaunz.structure.graph.factory;

import com.shaunz.structure.graph.Graph;

public class GraphFactoryImpl<T> implements  GraphFactory{
    private static volatile GraphFactoryImpl ourInstance;

    public static GraphFactoryImpl getInstance() {
        if(ourInstance == null){
            synchronized (GraphFactoryImpl.class){
                if(ourInstance == null){
                    ourInstance = new GraphFactoryImpl();
                }
            }
        }

        return ourInstance;
    }

    private GraphFactoryImpl() {
        if(ourInstance != null){
            throw  new RuntimeException("This is a singleton");
        }
    }

    public <T> Graph<T> build(GraphType graphType) {
        String className = graphType.className;
        Object newInstance = null;
        try {
            newInstance = getClass(className).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (Graph<T>)newInstance;
    }

    public GraphType getGraphType(Class clazz) {
        GraphType graphType = null;
        String clazzName = clazz.getName();
        GraphType[] graphTypes = GraphFactory.GraphType.values();
        for (int i=0,size=graphTypes.length; i < size; i++){
            if(graphTypes[i].className.equals(clazzName)){
                graphType = graphTypes[i];
            }
        }
        return graphType;
    }

    private Class getClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
}
