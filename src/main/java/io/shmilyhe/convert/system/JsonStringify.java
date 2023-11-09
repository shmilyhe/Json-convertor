package io.shmilyhe.convert.system;

import java.util.List;

import io.shmilyhe.convert.Json;
import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.ExpEnv;

public class JsonStringify implements IFunction{

    @Override
    public Object call(List args, ExpEnv env) {
        try{
            Object js =args.get(0);
            return wrap(Json.asJsonString(js));
        }catch(Exception e){
            return "";
        }
    }

    public String wrap(String json){
        if(json==null)return null;
        return json.replaceAll("\\\"", "\\\\\"");
    }
    
}
