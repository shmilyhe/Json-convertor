package io.shmilyhe.convert.impl;

import java.util.ArrayList;
import java.util.List;

import io.shmilyhe.convert.api.IConvertor;

public class ComplexConvertor implements IConvertor {

    List<IConvertor> clist = new ArrayList<IConvertor>();

    @Override
    public Object convert(Object root) {
        Object o=root;
        for(IConvertor c:clist){
            if(c==null)continue;
            o=c.convert(root);
        }
        return o;
    }

    public void addConvertor(IConvertor c){
        clist.add(c);
    }
    
}
