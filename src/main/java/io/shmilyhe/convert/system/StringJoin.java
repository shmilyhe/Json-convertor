package io.shmilyhe.convert.system;

import java.util.List;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.ExpEnv;

public class StringJoin implements IFunction{

    @Override
    public Object call(List args, ExpEnv env) {
        try{
            StringBuilder sb = new StringBuilder();
            if(args!=null)for(Object o:args){
                sb.append(o);
            }
            return sb.toString();
        }catch(Exception e){
            return null;
        }
    }
    
}
