package io.shmilyhe.convert.system;

import java.util.HashMap;
import java.util.List;

import io.shmilyhe.convert.Json;
import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.ExpEnv;

public class JsonParse implements IFunction{

    @Override
    public Object call(List args, ExpEnv env) {
        //JSON.parse
        try{
            String text =(String)args.get(0);
            //System.out.println("JSON.parse:"+text);
            Json j = Json.parse(text);
            return j.getRaw();
        }catch(Exception e){
            return new HashMap();
        }
    }
    
}
