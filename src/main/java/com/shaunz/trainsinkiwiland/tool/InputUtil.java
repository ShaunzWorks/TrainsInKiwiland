package com.shaunz.trainsinkiwiland.tool;

import com.shaunz.structure.graph.Graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputUtil {
    public static Graph<String> readFromArgs(String[] args,Graph<String> graph){
        if(args != null && args.length > 0){
            Arrays.asList(args).stream().forEach(arg -> {
                if(arg.indexOf(",") >= 0){
                    String[] subArr = arg.split(",");
                    Arrays.asList(subArr).stream().forEach(sub -> {
                        String[] routeInfo = convert2Route(sub);
                        graph.add(routeInfo[0],routeInfo[1],Double.valueOf(routeInfo[2]));
                    });
                } else {
                    String[] routeInfo = convert2Route(arg);
                    graph.add(routeInfo[0],routeInfo[1],Double.valueOf(routeInfo[2]));
                }
            });
        }
        return graph;
    }

    public static Graph<String> readFromFile(Graph<String> graph){
        File file = findGraphFile();
        FileInputStream inputStream;
        Scanner scanner;
        try {
            inputStream = new FileInputStream(file);
            scanner = new Scanner(inputStream,"UTF-8");
            StringBuffer lineBuffer = new StringBuffer("");
            while (scanner.hasNext()){
                lineBuffer.append(scanner.nextLine());
                String line = lineBuffer.toString();
                lineBuffer.delete(0,lineBuffer.length());

                lineBuffer.append(lineStr2Graph(line,graph));
            }
            if(scanner.ioException() != null){
                throw scanner.ioException();
            }
        }catch (FileNotFoundException ex){

        }catch (IOException ioEx){

        }finally {

        }
        return graph;
    }

    public static File findGraphFile(){
        File file = new File(System.getProperty("user.dir")+Constants.FILE_PATH);
        if(file.exists() && file.isFile()){
            return file;
        }
        file = new File(System.getProperty("user.dir")+"/src/main/resources"+Constants.FILE_PATH);
        if(file.exists() && file.isFile()){
            return file;
        }
        return null;

    }

    public static String lineStr2Graph(String lineStr,Graph<String> graph){
        String rest = "";
        String[] strArr = lineStr.split(" ");
        if(strArr != null && strArr.length > 0){
            for (int i =0, size = strArr.length; i < size; i++){
                if(validate(strArr[i])){
                    String[] routeInfo = convert2Route(strArr[i]);
                    graph.add(routeInfo[0],routeInfo[1],Double.valueOf(routeInfo[2]));
                } else if(i == size-1){
                    rest = strArr[i];
                } else {
                    throw new RuntimeException("Please check your content in file:" + Constants.FILE_PATH);
                }
            }
        }
        return rest;
    }

    public static boolean validate(String routeStr){
        if(routeStr.length() <= Constants.stationNmLength *2){
            return false;
        }

        String patternStr = "^[a-z,A-Z]{"+Constants.stationNmLength *2+"}[0-9]+$";

        return Pattern.matches(patternStr,routeStr);
    }

    public static String[] convert2Route(String route){
        String[] routeInfo = new String[3];
        String from,to;
        int weight;
        from = route.substring(0,Constants.stationNmLength);
        to = route.substring(Constants.stationNmLength,2*Constants.stationNmLength);
        try{
            weight = Integer.valueOf(route.substring(2*Constants.stationNmLength)).intValue();
        } catch (Exception e){
            weight = 0;
        }

        routeInfo[0] = from;
        routeInfo[1] = to;
        routeInfo[2] = weight+"";
        return routeInfo;
    }
}
