package io.shmilyhe.convert.impl;

import io.shmilyhe.convert.tools.ExpEnv;

public class SelfGetter extends Getter{
    
    public SelfGetter(String ext) {
        super(ext);
    }

    @Override
    public Object get(Object data,ExpEnv evn) {
        return data;
    }
    
}
