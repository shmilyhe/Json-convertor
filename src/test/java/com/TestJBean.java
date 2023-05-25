package com;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.shmilyhe.convert.Json;
import io.shmilyhe.convert.tools.JBean;
import io.shmilyhe.convert.tools.JsonString;
import io.shmilyhe.convert.tools.SimpleJson;

public class TestJBean {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, IntrospectionException{
        Map m= new HashMap<>();
        m.put("name", "erc");

        Map m2= new HashMap<>();
        m2.put("name", "eric");
        m.put("v1", m2);
        List list= new ArrayList();
        list.add(m2);
        m.put("list", list);
        Vo1 v = JBean.mapToBean(m,  new Vo1());
        System.out.println(JsonString.asJsonString(v));
        String jsons="{\"datetime\":\"2023-02-03\",\"time\":\"2023-01-01\",\"up\":true,\"name\" : \"erc\",\"v1\" : {\"name\" : \"eric\",\"age\" : 1,\"v3\":{\"title\":\"tttt\"},\"list\":[{\"title\":\"ooooo\"}]},\"list\" : [{\"name\":\"eric\"},{\"name\":\"eric1\"}]}";
        
        Vo1 v2 = JBean.mapToBean((Map)SimpleJson.parse(jsons).getRoot(),  new Vo1());

        System.out.println(JsonString.asJsonString(SimpleJson.parse(jsons).getRoot()));
        System.out.println(JsonString.asJsonString(v2));
        Vo1 v3 = JBean.toBean(jsons, new Vo1());
        System.out.println(v3.isUp());
        System.out.println(JsonString.asJsonString(v3));

        Json js = Json.parse(jsons);
        Vo1 v4= js.asBean(Vo1.class);
        //System.out.println(JsonString.asJsonString(v4));
        Vo2 vo2 =js.Q("v1").asBean(Vo2.class);
        //System.out.println(JsonString.asJsonString(vo2));
    }
}
