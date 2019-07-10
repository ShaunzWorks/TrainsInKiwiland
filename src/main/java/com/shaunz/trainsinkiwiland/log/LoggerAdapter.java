package com.shaunz.trainsinkiwiland.log;

import com.shaunz.trainsinkiwiland.tool.Constants;

public class LoggerAdapter implements Logger {

    @Override
    public boolean info(Object object) {
        if(Constants.LOG_TYPE.equals(Constants.LogType.Console)){
            System.out.println("INFO: " + object.toString());
        }

        return true;
    }

    @Override
    public boolean err(Object object) {
        if(Constants.LOG_TYPE.equals(Constants.LogType.Console)){
            System.out.println("ERROR: " + object.toString());
        }

        return true;
    }

    @Override
    public boolean warn(Object object) {
        if(Constants.LOG_TYPE.equals(Constants.LogType.Console)){
            System.out.println("WARNING: " + object.toString());
        }

        return true;
    }

    @Override
    public boolean debug(Object object) {
        if(Constants.LOG_TYPE.equals(Constants.LogType.Console)){
            System.out.println("DEBUG: " + object.toString());
        }
        return true;
    }
}
