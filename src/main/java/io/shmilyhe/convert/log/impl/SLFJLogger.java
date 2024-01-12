package io.shmilyhe.convert.log.impl;

import io.shmilyhe.convert.log.api.Logger;

public class SLFJLogger  implements Logger{
    org.slf4j.Logger raw ;
    public SLFJLogger(Class clazz){
        raw =  org.slf4j.LoggerFactory.getLogger(clazz);
        if(raw.getClass().getName().indexOf("NOPLogger")>-1){
            throw new RuntimeException("SLF4J: Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
        }
        System.out.println(raw.getClass());
    }
    
    @Override
    public void debug(String format, Object... arguments) {
       raw.debug(format, arguments);
    }

    @Override
    public void info(String format, Object... arguments) {
        raw.info(format, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        raw.warn(msg, t);
    }

    @Override
    public void warn(String format, Object... arguments) {
        raw.warn(format, arguments);
    }

    @Override
    public void error(String format, Object... arguments) {
        raw.error(format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        raw.error(msg, t);
    }
    
}
