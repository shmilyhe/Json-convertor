package io.shmilyhe.convert.system;

import java.util.List;
import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.ExpEnv;

public class StringSubstring implements IFunction{

    @Override
    public Object call(List args, ExpEnv env) {
        //JSON.parse
        String text=null;
        try{
            Object o =args.get(0);
            if(o==null)return o;
            if(o instanceof String){
                text =(String)o;
            }else{
                text =String.valueOf(o);
            }
            if(args.size()==2){
                Integer start =(Integer)args.get(1);
                if(start==null||start==0)return text;
                return text.substring(start);
            }else if(args.size()==3){
                Integer start =(Integer)args.get(1);
                Integer end =(Integer)args.get(2);
                //System.out.println(start+"|"+end);
                if(end==null||end==0)end=text.length();
                if(end<0)end=text.length()+end;
                if(end<0||end<=start)return "";
                if(end>text.length())end=text.length();
                return text.substring(start,end);
            }
            return text;
        }catch(Exception e){
            return text;
        }
    }
    
}
