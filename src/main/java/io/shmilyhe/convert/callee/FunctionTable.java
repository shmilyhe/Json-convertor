package io.shmilyhe.convert.callee;

import java.util.HashMap;

import io.shmilyhe.convert.ext.HttpFun;
import io.shmilyhe.convert.ext.HttpPostFun;
import io.shmilyhe.convert.system.JsonParse;
import io.shmilyhe.convert.system.JsonStringify;
import io.shmilyhe.convert.system.Len;
import io.shmilyhe.convert.system.Maps;
import io.shmilyhe.convert.system.PrintFFunction;
import io.shmilyhe.convert.system.RoundRfuntion;
import io.shmilyhe.convert.system.StringJoin;
import io.shmilyhe.convert.system.StringLower;
import io.shmilyhe.convert.system.StringSubstring;
import io.shmilyhe.convert.system.StringUpper;


public class FunctionTable {
    static HashMap<String,IFunction> function;
    static{
        function= new HashMap<>();
        function.put("httpget", new HttpFun());
        function.put("http.get", new HttpFun());
        function.put("http.post", new HttpPostFun());
        function.put("printf",new PrintFFunction());
        function.put("round", new RoundRfuntion());
        function.put("Math.round", new RoundRfuntion());
        function.put("JSON.parse", new JsonParse());
        function.put("JSON.stringify", new JsonStringify());
        function.put("String.toUpperCase", new StringUpper());
        function.put("String.toLowerCase", new StringLower());
        function.put("String.substring", new StringSubstring());
        function.put("String.join", new StringJoin());
        function.put("len",new Len());
        function.put("Maps.camelCase",Maps.camelCase());
        function.put("Maps.unixLike",Maps.unixLike());

        
    }
    public FunctionTable registry(String name,IFunction fun){
        function.put(name, fun);
        return this;
    }
    
    public IFunction getFunction(String name){
        return function.get(name);
    }

}
