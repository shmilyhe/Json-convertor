package io.shmilyhe.convert;

import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.tools.JsonString;
import io.shmilyhe.convert.tools.SimpleJson;

public class JsonConvertor {

    IConvertor convertor=null;

    public JsonConvertor(String commands){
        convertor= new ConvertorFactory().getConvertor(commands);
    }
    public String convert(String json){
            if(convertor==null)return json;
            Object jdata = SimpleJson.parse(json).getRoot();
            jdata=convertor.convert(jdata);
            return JsonString.asJsonString(jdata);
    }
}
