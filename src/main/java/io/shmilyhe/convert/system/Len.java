package io.shmilyhe.convert.system;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.ExpEnv;

public class Len implements IFunction{

    @Override
    public Object call(List args, ExpEnv env) {
        //JSON.parse
        try{
            Object o =args.get(0);
            if(o==null)return 0;
            if(o instanceof String){
                return ((String)o).length();
            }else if(o.getClass().isArray()){
                return ((Object[])o).length;
            }else if(o instanceof Collection){
                return ((Collection)o).size();
            }else if(o instanceof Map){
                return ((Map)o).size();
            }
            return String.valueOf(o).length();
        }catch(Exception e){
            return 0;
        }
    }
    
}
