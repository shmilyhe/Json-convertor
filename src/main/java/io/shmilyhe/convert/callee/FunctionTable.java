package io.shmilyhe.convert.callee;

import java.util.HashMap;

import io.shmilyhe.convert.ext.HttpFun;
import io.shmilyhe.convert.system.PrintFFunction;
import io.shmilyhe.convert.system.RoundRfuntion;


public class FunctionTable {
    static HashMap<String,IFunction> function;
    static{
        function= new HashMap<>();
        function.put("httpget", new HttpFun());
        function.put("printf",new PrintFFunction());
        function.put("round", new RoundRfuntion());
    }
    public FunctionTable registry(String name,IFunction fun){
        function.put(name, fun);
        return this;
    }
    
    public IFunction getFunction(String name){
        return function.get(name);
    }

}
