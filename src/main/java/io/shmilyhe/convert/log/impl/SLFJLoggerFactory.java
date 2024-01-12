package io.shmilyhe.convert.log.impl;

import io.shmilyhe.convert.log.api.ILoggerFactory;
import io.shmilyhe.convert.log.api.Logger;

public class SLFJLoggerFactory implements ILoggerFactory{

    @Override
    public Logger getLogger(Class clazz) {
        return new SLFJLogger(clazz);
    }
    
}
