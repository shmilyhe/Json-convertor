package io.shmilyhe.convert.system;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.shmilyhe.convert.callee.IFunction;

public class Regex {
    public static IFunction test(){
        return (param,env)->{
            if(param==null||param.size()<2)return false;
            try{
                String a1=(String) param.get(0);
                String a2=(String) param.get(1);
                return a1.matches(a2);
            }catch(Exception e){

            }
            return false;
        };
    }

    public static IFunction group(){
        return (param,env)->{
            if(param==null||param.size()<2)return false;
            try{
                String a1=(String) param.get(0);
                String a2=(String) param.get(1);
                Pattern p=Pattern.compile(a2);
                Matcher m = p.matcher(a1);
                if(m.matches()){
                    int count = m.groupCount();
                    ArrayList list = new ArrayList<>();
                    for(int i=0;i<count+1;i++){
                        list.add(m.group(i));
                    }
                    return list;
                }
            }catch(Exception e){

            }
            return null;
        };
    }

    public static IFunction replaceAll(){
        return (param,env)->{
            if(param==null||param.size()<3)return false;
            try{
                String a1=(String) param.get(0);
                String a2=(String) param.get(1);
                String a3=(String) param.get(2);
                return a1.replaceAll(a2, a3);
            }catch(Exception e){

            }
            return null;
        };
    }
}
