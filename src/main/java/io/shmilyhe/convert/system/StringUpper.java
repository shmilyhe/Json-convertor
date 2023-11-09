package io.shmilyhe.convert.system;

import java.util.List;
import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.ExpEnv;

public class StringUpper implements IFunction{

    @Override
    public Object call(List args, ExpEnv env) {
        //JSON.parse
        try{
            Object o =args.get(0);
            if(o==null)return null;
            String text =null;
            if(o instanceof String){
                text=(String)o;
            }else {
                text =String.valueOf(o);
            }
            return text.toUpperCase();
        }catch(Exception e){
            return null;
        }
    }
    
}
