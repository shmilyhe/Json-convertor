package io.shmilyhe.convert.api;

public interface IDataAccess {
    boolean set(Object v,Object da);
    Object get(Object da);
    Object remove(Object da);
    IDataAccess next();
    void next(IDataAccess n);
    void append(IDataAccess n);
    boolean isTail();
    void isTail(boolean t);
    Object create();
}
