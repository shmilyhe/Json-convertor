package io.shmilyhe.convert.system;

import java.util.List;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.B64;

public class Base64 {
    private static Object listGet(List list,int index){
        if(list==null)return null;
        if(index<list.size()){
            return list.get(index);
        }else{
            return null;
        }
    }

    public static IFunction encode(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            if(arg1 instanceof String){
                return B64.encode(arg1.toString().getBytes());
            }
            return null;
        };
    }

    public static IFunction decode(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            if(arg1 instanceof String){
                return  new String(B64.decode(arg1.toString()));
            }
            return null;
        };
    }
}
