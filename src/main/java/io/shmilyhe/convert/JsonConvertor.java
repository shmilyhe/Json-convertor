package io.shmilyhe.convert;

import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.impl.ComplexConvertor;
import io.shmilyhe.convert.tools.ExpEnv;
import io.shmilyhe.convert.tools.JsonString;
import io.shmilyhe.convert.tools.SimpleJson;

public class JsonConvertor {

    IConvertor convertor=null;

    public JsonConvertor(String commands){
        convertor= new AstConvertorFactory().getConvertor(commands);
    }

    public JsonConvertor(String[] cmds){
        ComplexConvertor con  = new ComplexConvertor();
        convertor=con;
        AstConvertorFactory cf = new AstConvertorFactory();
        for(String c:cmds){
            con.addConvertor(cf.getConvertor(c));
        }
    }

    public String convert(String json){
            if(convertor==null)return json;
            Object jdata = toData(json);//SimpleJson.parse(json).getRoot();
            jdata=convert(jdata,null);
            return JsonString.asJsonString(jdata);
    }

    public static Object toData(String json){
        return SimpleJson.parse(json).getRoot();
    }

    public static String toJsonString(Object obj){
        return JsonString.asJsonString(obj);
    }

    public Object convert(Object jdata,ExpEnv env){
            if(convertor==null)return jdata;
            if(env==null){env= new ExpEnv(null);}
            jdata=convertor.convert(jdata,env);
            return jdata;
    }
}
