package com;

import java.util.List;

public class Vo2 {
    private String name;
    private Integer age;
    private Vo3 v3;
    private List<Vo3> list;
    public List<Vo3> getList() {
        return list;
    }
    public void setList(List<Vo3> list) {
        this.list = list;
    }
    public Vo3 getV3() {
        return v3;
    }
    public void setV3(Vo3 v3) {
        this.v3 = v3;
    }
    public Vo2(){}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    
}
