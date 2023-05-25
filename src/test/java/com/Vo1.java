package com;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Vo1 {
    private String name;
    private Vo2 v1;
    private List<Vo2> list;

    private Date time;

    private LocalDateTime datetime;

    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    private Boolean up;

    public boolean isUp() {
        return up;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
    public Vo1(){}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Vo2 getV1() {
        return v1;
    }
    public void setV1(Vo2 v1) {
        this.v1 = v1;
    }
    public List<Vo2> getList() {
        return list;
    }
    public void setList(List<Vo2> list) {
        this.list = list;
    }


    
    
}
