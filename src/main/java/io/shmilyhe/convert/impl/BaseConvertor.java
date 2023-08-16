package io.shmilyhe.convert.impl;

import java.util.ArrayList;
import java.util.List;

import io.shmilyhe.convert.api.IConvertor;

/**
 * 
 */
public abstract class BaseConvertor implements IConvertor {
    protected IConvertor parent;

    protected List<IConvertor> clist = new ArrayList<IConvertor>();

    public IConvertor getParent() {
        return parent;
    }

    public void setParent(IConvertor parent) {
        this.parent = parent;
    }

    public void addConvertor(IConvertor c){
        parent(c);
        clist.add(c);
    }

    protected void parent(IConvertor c){
        if(c instanceof BaseConvertor)
        ((BaseConvertor)c).setParent(this);
    }
    
}
