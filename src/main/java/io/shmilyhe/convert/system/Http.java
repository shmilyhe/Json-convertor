package io.shmilyhe.convert.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.ext.HttpFun;
import io.shmilyhe.convert.ext.HttpFunction;
import io.shmilyhe.convert.ext.HttpPostFun;

public class Http {

    public static IFunction get(){
        return new HttpFun();
    }
    public static IFunction post(){
        return new HttpPostFun();
    }

    public static IFunction request(){
        return (args,env)->{
        Map param=null;
        if(args!=null&&args.size()>0){
            param=(Map) args.get(0);
        }
        if(param ==null)param= new HashMap();
        Object body=param.get("body");
        if(body!=null 
            && body instanceof Collection
            &&isByteArray((Collection)body)){
            param.put("body", fixedByteArray((Collection)body));
        }
        return HttpFunction.request(param, env);
        };
    }
    

    private static byte[] fixedByteArray(Collection co){
        byte[] b = new byte[co.size()];
        int i=0;
        for(Object o:co){
            b[i++]=Bytes.getByte(o);
        }
        return b;
    }

    /**
     * 试探前5个是否byte
     * @param co
     * @return
     */
    private static boolean isByteArray(Collection co){
        if(co==null)return false;
        int i=0;
        for(Object o:co){
            if(i++>5)return true;
            if(o instanceof Byte)continue;
            //Byte 位操作后有可能自动转成Int
            if(o instanceof Integer)continue;
            return false;
        }
        return true;
    }

}
