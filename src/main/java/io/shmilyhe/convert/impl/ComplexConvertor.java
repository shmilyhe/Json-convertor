package io.shmilyhe.convert.impl;

import java.util.ArrayList;
import java.util.List;

import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.tools.ExpEnv;

public class ComplexConvertor  extends BaseConvertor {

    @Override
    public Object convert(Object root,ExpEnv env) {
        Object o=root;
        for(IConvertor c:clist){
            if(c==null)continue;
            o=c.convert(o,env);
        }
        return o;
    }

    
}
