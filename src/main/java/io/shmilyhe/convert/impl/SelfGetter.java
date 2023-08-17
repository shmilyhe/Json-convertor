package io.shmilyhe.convert.impl;

import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.tools.ExpEnv;

public class SelfGetter implements IGet{
    
    @Override
    public Object get(Object data,ExpEnv evn) {
        return data;
    }
    
}
