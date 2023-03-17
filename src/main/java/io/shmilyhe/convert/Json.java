package io.shmilyhe.convert;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.api.IRemove;
import io.shmilyhe.convert.api.ISet;
import io.shmilyhe.convert.impl.Getter;
import io.shmilyhe.convert.impl.Remove;
import io.shmilyhe.convert.impl.Setter;
import io.shmilyhe.tools.JsonString;
import io.shmilyhe.tools.SimpleJson;
import io.shmilyhe.tools.StringValue;
/**
 * 
 */
public class Json {
    protected Object raw=new HashMap();
    protected Map<String,IGet> getMap= new HashMap<String,IGet>();
    protected Map<String,ISet> setMap= new HashMap<String,ISet>();
    protected Map<String,IRemove> mMap= new HashMap<String,IRemove>();
    boolean array;

    public static Json parse(String json){
        SimpleJson sj = SimpleJson.parse(json);
        Json j = new Json();
        j.raw=sj.getRoot();
        return j;
    }

    public void wrap(Object o){
        raw=o;
        array=isArray(o)||isCollection(o);
    }


    public void set(String path,Object value){
        ISet s =setMap.get(path);
        if(s==null){
            s= new Setter(path);
            setMap.put(path, s);
        }
        s.set(raw, value);
    }    

    public void remove(String path){
        IRemove s =mMap.get(path);
        if(s==null){
            s= new Remove(path);
            mMap.put(path, s);
        }
        s.remove(raw);
    }

    public Object getRaw(){return raw;}

    public Json Q(String path){
        IGet g =getMap.get(path);
        if(g==null){
            g= new Getter(path);
            getMap.put(path, g);
        }
        Object o=g.get(raw);
        Json j = new Json();
        j.wrap(o);
        return j;
    }

    public Integer asInt(){
        if(raw==null)return null;
        if(raw instanceof Integer)return (Integer)raw;
        if(raw instanceof Boolean)return (Boolean)raw?1:0;
        if(raw instanceof Date)return (int)((Date)raw).getTime();
        if(raw instanceof Number)return ((Number)raw).intValue();
        if(raw instanceof String)return Integer.parseInt((String)raw);
        return null;
    }

    public Double asDouble(){
        if(raw==null)return null;
        if(raw instanceof Boolean)return (double)((Boolean)raw?1:0);
        if(raw instanceof Date)return (double)((Date)raw).getTime();
        if(raw instanceof Number)return ((Number)raw).doubleValue();
        if(raw instanceof String)return Double.valueOf((String)raw);
        return null;
    }

    public Date asDate(){
        if(raw==null)return null;
        if(raw instanceof Boolean)return null;
        if(raw instanceof Date)return (Date)raw;
        if(raw instanceof Number)return new Date(((Number)raw).longValue());
        if(raw instanceof String)return StringValue.toDate((String)raw);
        return null;
    }

    public Long asLong(){
        if(raw==null)return null;
        if(raw instanceof Boolean)return (long)((Boolean)raw?1:0);
        if(raw instanceof Date)return (long)((Date)raw).getTime();
        if(raw instanceof Number)return ((Number)raw).longValue();
        if(raw instanceof String)return Long.valueOf((String)raw);
        return null;
    }
    public Boolean asBoolean(){
        if(raw==null)return null;
        if(raw instanceof Boolean)return (Boolean)raw;
        if(raw instanceof Number)return ((Number)raw).intValue()>0;
        if(raw instanceof String)return "true".equalsIgnoreCase((String)raw);
        return null;
    }

    public String asString(){
        if(raw==null)return null;
        if(raw instanceof Boolean)return raw.toString();
        if(raw instanceof Date)return raw.toString();
        if(raw instanceof Number)return raw.toString();
        if(raw instanceof String)return  raw.toString();
        return toString();
    }

    public String toString(){
        return JsonString.asJsonString(raw);
    }
    private static boolean isArray(Object o){
		if(o==null)return false;
		return o.getClass().isArray();
	}
    private static boolean isCollection(Object o){
        if (o instanceof Collection){return true;}
        return false;
    }
}
