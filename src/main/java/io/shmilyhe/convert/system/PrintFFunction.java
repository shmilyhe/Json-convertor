package io.shmilyhe.convert.system;

import java.util.List;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.ExpEnv;

/**
 * 类似  c 的printf 方法
 */
public class PrintFFunction implements IFunction{

    @Override
    public Object call(List args, ExpEnv env) {
        try{
            String text =(String)args.get(0);
            Object a[] = new Object[args.size()-1];
            int i=0;
            for(Object o:args){
                if(i>0){
                    a[i-1]=o;
                }
                i++;
            }
            String str=String.format(text,a);
            return str;
        }catch(Exception e){
            return null;
        }
        
    }
    //format
}
