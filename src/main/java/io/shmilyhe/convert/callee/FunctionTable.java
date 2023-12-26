package io.shmilyhe.convert.callee;

import java.util.HashMap;

import io.shmilyhe.convert.ext.HttpFun;
import io.shmilyhe.convert.ext.HttpPostFun;
import io.shmilyhe.convert.system.Base64;
import io.shmilyhe.convert.system.Dates;
import io.shmilyhe.convert.system.JsonFun;
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
        function.put("JSON.parse", JsonFun.parse());
        function.put("JSON.stringify", JsonFun.stringify());
        function.put("String.toUpperCase", new StringUpper());
        function.put("String.toLowerCase", new StringLower());
        function.put("String.substring", new StringSubstring());
        function.put("String.join", new StringJoin());
        function.put("len",new Len());
        function.put("Maps.camelCase",Maps.camelCase());
        function.put("Maps.unixLike",Maps.unixLike());
        function.put("Maps.get",Maps.get());
        function.put("Date.format",Dates.format());
        function.put("Date.getTime",Dates.getTime());
        function.put("Date.getDate",Dates.getDate());
        function.put("Date.getDay",Dates.getDay());
        function.put("Date.getHour",Dates.getHour());
        function.put("Date.getMinute",Dates.getMinute());
        function.put("Date.getMonth",Dates.getMonth());
        function.put("Date.getSecond",Dates.getSecond());
        function.put("Date.getWeek",Dates.getWeek());
        function.put("Date.parse",Dates.parse());
        function.put("Base64.decode",Base64.decode());
        function.put("Base64.encode",Base64.encode());
        function.put("Number",io.shmilyhe.convert.system.Number.number());
    }
    public FunctionTable registry(String name,IFunction fun){
        function.put(name, fun);
        return this;
    }
    
    public IFunction getFunction(String name){
        return function.get(name);
    }

}
