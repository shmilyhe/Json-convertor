package io.shmilyhe.convert.api;


import io.shmilyhe.convert.callee.IFunction;


public interface IFunctionRegistry {
    public void registry(String name,IFunction fun);
    
    public IFunction getFunction(String name);
}
