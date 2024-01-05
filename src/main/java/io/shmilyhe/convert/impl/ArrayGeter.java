package io.shmilyhe.convert.impl;

import java.util.ArrayList;
import java.util.List;

import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.tools.ExpEnv;

public class ArrayGeter implements IGet{

    List<IGet> getters = new ArrayList<IGet>();
    public ArrayGeter addGeter(IGet g){
        getters.add(g);
        return this;
    }

    @Override
    public Object get(Object data, ExpEnv evn) {
        ArrayList d= new ArrayList<>();
        for(IGet g:getters){
            d.add(g.get(data, evn));
        }
        return d;
    }
    
}
