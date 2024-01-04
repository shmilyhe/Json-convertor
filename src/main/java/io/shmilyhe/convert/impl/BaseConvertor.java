package io.shmilyhe.convert.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.api.IFunctionRegistry;
import io.shmilyhe.convert.callee.IFunction;

/**
 * 
 */
public abstract class BaseConvertor implements IConvertor,IFunctionRegistry {

    protected BaseConvertor parent;


    private String name;

    HashMap<String,IFunction> function = new HashMap<String,IFunction>();
    public void registry(String name,IFunction fun){
        function.put(name, fun);
    }
    
    public IFunction getFunction(String name){
        IFunction f =function.get(name);
        if(f==null&&parent!=null){
            f=parent.getFunction(name);
        }
        return f;
    }

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
