package com.shaunz.trainsinkiwiland.log;

public class Log implements Logger {
    private LoggerAdapter loggerAdapter = new LoggerAdapter();
    @Override
    public boolean info(Object object) {
        return loggerAdapter.info(object);
    }

    @Override
    public boolean err(Object object) {
        return loggerAdapter.err(object);
    }

    @Override
    public boolean warn(Object object) {
        return loggerAdapter.warn(object);
    }

    @Override
    public boolean debug(Object object) {
        return loggerAdapter.debug(object);
    }
}
