package io.shmilyhe.convert.impl;

import io.shmilyhe.convert.api.IGet;

public class SelfGetter implements IGet{

    @Override
    public Object get(Object data) {
        return data;
    }
    
}
