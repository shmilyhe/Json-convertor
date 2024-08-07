package io.shmilyhe.convert.system;

import java.util.Arrays;
import java.util.Collection;

import io.shmilyhe.convert.callee.IFunction;

/**
 * 集合
 */
public class CollectionFun {
    
    /**
     * 是否包含
     * @return
     */
    public static IFunction contains(){
        return (param,env)->{
            if(param==null||param.size()<2) return null;
            Object arg1 =param.get(0);
            Object arg2 =param.get(1);
            if(arg1==null)return false;
            if(arg1 instanceof Collection){
                return ((Collection)arg1).contains(arg2);
            }else if(arg1.getClass().isArray()){
                Object[] oa =(Object[]) arg1;
                for(Object o:oa){
                    if(o.equals(arg2))return true;
                }
                return false;
            } else{
                return false;
            }
        };
    }
}
