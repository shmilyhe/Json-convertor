package io.shmilyhe.convert.system;

import java.util.Collection;
import java.util.Date;

import io.shmilyhe.convert.callee.IFunction;

public class Objects {
    public static IFunction typeof(){
        return (param,env)->{
            if(param==null||param.size()==0) return "NotThing";
            Object arg1 =param.get(0);
            if(arg1==null)return "NULL";
            if(arg1 instanceof String )return "String";
            if(arg1 instanceof Integer )return "Integer";
            if(arg1 instanceof Double )return "Double";
            if(isArray(arg1))return "Array";
            if(arg1 instanceof Collection )return "Collection";
            if(arg1 instanceof Date )return "Date";
            if(arg1 instanceof Byte )return "Byte";
            if(arg1 instanceof Long )return "Long";
            if(arg1 instanceof Float )return "Float";
            if(arg1 instanceof IFunction )return "function";
            return "Object";
        };
    }
    private static boolean isArray(Object o){
		if(o==null)return false;
		return o.getClass().isArray();
	}
}
