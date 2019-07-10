package com.shaunz.trainsinkiwiland.log;

public interface Logger {
    boolean info(Object object);
    boolean err(Object object);
    boolean warn(Object object);
    boolean debug(Object object);
}
