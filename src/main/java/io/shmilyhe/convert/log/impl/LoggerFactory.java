package io.shmilyhe.convert.log.impl;

import io.shmilyhe.convert.log.api.ILoggerFactory;
import io.shmilyhe.convert.log.api.Logger;

public class LoggerFactory implements ILoggerFactory {

    @Override
    public Logger getLogger(Class clazz) {
        return new DefaultLogger(clazz);
    }

    @Override
    public Logger getLogger() {
        return new DefaultLogger();
    }
    
}
