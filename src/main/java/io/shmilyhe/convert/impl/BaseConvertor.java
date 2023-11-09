package io.shmilyhe.convert.impl;

import java.util.ArrayList;
import java.util.List;

import io.shmilyhe.convert.api.IConvertor;

/**
 * 
 */
public abstract class BaseConvertor implements IConvertor {
    protected BaseConvertor parent;


    private String name;

    

    protected List<IConvertor> clist = new ArrayList<IConvertor>();

    public BaseConvertor getParent() {
        return parent;
    }

    public void setParent(BaseConvertor parent) {
        this.parent = parent;
    }

    public void addConvertor(IConvertor c){
        if(c==null)return;
        parent(c);
        clist.add(c);
    }

    protected void parent(IConvertor c){
        if(c instanceof BaseConvertor)
        ((BaseConvertor)c).setParent(this);
    }
    public String getName() {
        return name;
    }

    public BaseConvertor setName(String name) {
        this.name = name;
        return this;
    }
    
}
