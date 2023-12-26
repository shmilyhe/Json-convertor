package io.shmilyhe.convert.system;

import java.util.HashMap;

import io.shmilyhe.convert.Json;
import io.shmilyhe.convert.callee.IFunction;

public class JsonFun {
    public static IFunction parse(){
        return (param,env)->{
            try{
                String text =(String)param.get(0);
                Json j = Json.parse(text);
                return j.getRaw();
            }catch(Exception e){
                return new HashMap();
            }
        };
    }
    public static IFunction stringify (){
        return (param,env)->{
            try{
                Object js =param.get(0);
                return Json.asJsonString(js);
            }catch(Exception e){
                return "";
            }
        };
    }
}
