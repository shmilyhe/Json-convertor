package com;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.shmilyhe.convert.Json;


public class TestJsonList {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, IntrospectionException{
        Map m= new HashMap<>();
        m.put("name", "erc");
        
        List list= new ArrayList();
        m.put("list",list);
        for(int i=0;i<10;i++){
            Vo2 v2 = new Vo2();
            v2.setAge(i);
            v2.setName("name"+i);
            list.add(v2);
        }
        String str = Json.asJsonString(m);
        System.out.println(str);
        Json j1= Json.parse(str);
        j1.Q("list").asList().stream().forEach(e->{ 
            Vo2 v2 = e.asBean(Vo2.class);
            System.out.println(Json.asJsonString(v2));
        });

        
    }
}
