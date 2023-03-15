package io.shmilyhe.convert.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArrayDataAccess extends BaseDataAccess {

    int index=0;
    boolean withnName=false;
    private String name;
    public ArrayDataAccess(int index,boolean wn){

        this.index=index;
    }

    public ArrayDataAccess(String name){
        this.name=name;
        withnName=true;
    }


    @Override
    public boolean set(Object v, Object da) {
        if(withnName){
            if(da instanceof Map){
                ((Map)da).put(name, v);
                return true;
            }
            return false;
        }else if(da instanceof List){
            insert((List)da,index,v);
            return true;
        }
        return false;
    }

    @Override
    public Object get(Object da) {
        if(withnName){
            if(da instanceof Map){
                return  ((Map)da).get(name);
            }
            return null;
        }
        if(da instanceof List){
            List list =(List) da;
            if(list.size()>index)return list.get(index);
            return null;
        }
        return null;
    }


    private void insert(List list,int i,Object value){
        int padding=i-list.size();
        if(padding>=0){
            for(int j=0;j<padding;j++){
                list.add(null);
            }
            list.add(value);
        }else{
            list.set(i, value);
        }
    }

    @Override
    public Object create() {
        return new ArrayList();
    }

    public String toString(){
        return withnName?"array:"+name: "array:"+index;
    }

    @Override
    public Object remove(Object da) {
       this.set(da, null);
       return da;
    }

    
}
