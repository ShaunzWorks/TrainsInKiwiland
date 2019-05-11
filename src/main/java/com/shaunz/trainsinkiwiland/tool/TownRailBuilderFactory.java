package com.shaunz.trainsinkiwiland.tool;

public class TownRailBuilderFactory {
    private static TownRailBuilderFactory instance = new TownRailBuilderFactory();
    private TownRailBuilderFactory(){}
    public static TownRailBuilderFactory getInstace(){
        return instance;
    }
    public TownRailBuilder getTownRailBuilder(){
        return new TownRailBuilder();
    }
}
