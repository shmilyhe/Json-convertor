package com;

import java.util.Date;

import org.junit.Test;

import io.shmilyhe.convert.Json;

public class TestJson {

    @Test
    public void test(){
        String js = "{\"name\":\"eric\",\"profile\":{\"company\":\"tylw\",\"birthday\":\"1984-05-17\"},\"data\":[1,2,3,4,5,6,7.9]}";
        Json json = Json.parse(js);
        json.set("js[0]", new Date());
        json.set("js[1]", new Date().getTime());
        System.out.println(json);
        System.out.println(json.Q("name").getRaw());
        System.out.println(json.Q("profile"));
        System.out.println(json.Q("profile.company").getRaw());
        System.out.println(json.Q("profile.company"));
        System.out.println(json.Q("profile.company").asString());
        System.out.println(json.Q("profile.company").asDate());
        System.out.println(json.Q("profile.birthday").asDate());
        System.out.println(json.Q("data[6]").asDate());
        json.remove("profile");
        System.out.println(json);
    }
}
