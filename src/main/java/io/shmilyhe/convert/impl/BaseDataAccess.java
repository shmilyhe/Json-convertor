package io.shmilyhe.convert.impl;

import io.shmilyhe.convert.api.IDataAccess;

public abstract class BaseDataAccess implements IDataAccess {
    protected     IDataAccess next;
    protected     IDataAccess tail;
    @Override
    public IDataAccess next() {
        return next;
    }

    public void next(IDataAccess n){
        next=n;
    }

    

    @Override
    public void append(IDataAccess n) {
        n.isTail(true);
        if(tail==null){
            next=n;
            tail=n;
            this.isTail(false);
        }else{
            tail.isTail(false);
            tail.next(n);
            tail=n;
        }
    }

    
    @Override
    public abstract boolean set(Object v, Object da);

    @Override
    public abstract Object get(Object da);

    boolean isTail=true;

    @Override
    public boolean isTail() {
        return isTail;
    }

    @Override
    public void isTail(boolean t) {
         isTail=t;
    }
    
    
}
