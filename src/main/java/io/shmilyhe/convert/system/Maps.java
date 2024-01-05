package io.shmilyhe.convert.system;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.StringStyle;

public class Maps {
    public static IFunction camelCase(){
        return (param,env)->{
            if(param==null||param.size()==0) return null;
            Object arg1 =param.get(0);
            if(arg1==null)return null;
            if(arg1 instanceof Map){
                return camelCase((Map)arg1);
            }else{
                return arg1;
            }
        };
    }

    public static IFunction unixLike(){
        return (param,env)->{
            if(param==null||param.size()==0) return null;
            Object arg1 =param.get(0);
            if(arg1==null)return null;
            if(arg1 instanceof Map){
                return unixLike((Map)arg1);
            }else{
                return arg1;
            }
        };
    }

    public static IFunction get(){
        return (param,env)->{
            if(param==null||param.size()<2) return null;
            Object arg1 =param.get(0);
            Object arg2 =param.get(1);
            if(arg1==null)return null;
            if(arg1 instanceof Map){
                return ((Map)arg1).get(arg2);
            }else{
                return null;
            }
        };
    }
    public static IFunction retain(){
        return (param,env)->{
            if(param==null||param.size()<2) return null;
            Object arg1 =param.get(0);
            Object arg2 =param.get(1);
            if(arg1==null)return null;
            if(arg1 instanceof Map){
                return ((Map)arg1).get(arg2);
            }else{
                return null;
            }
        };
    }

    private static Map camelCase(Map map){
        if(map==null||map.size()==0)return map;
        HashMap nm= new HashMap();
        Set<Map.Entry> ks = map.entrySet();
        for(Map.Entry e:ks){
            String k=String.valueOf(e.getKey());
            nm.put(StringStyle.camelCase(k),e.getValue());
        }
        return nm;
    }

    private static Map unixLike(Map map){
        if(map==null||map.size()==0)return map;
        HashMap nm= new HashMap();
        Set<Map.Entry> ks = map.entrySet();
        for(Map.Entry e:ks){
            String k=String.valueOf(e.getKey());
            nm.put(StringStyle.unixLike(k),e.getValue());
        }
        return nm;
    }

    public static void main(String []args){
        Map h= new HashMap<>();
        h.put("q_bb_fbbb", 1);
        h.put("_ag_bb_fbbb", new HashMap());
        h.put(111, new HashMap());
        Map h2 = camelCase(h);
        System.out.println(h2);
        System.out.println(unixLike(h2));
    }
}
