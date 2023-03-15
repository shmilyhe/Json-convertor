package io.shmilyhe.tools;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class Json2String {
	//static Log log =Logger.getLogger(Json2String.class);
	public static String asJsonString(Object o,String ...ig){
		StringBuilder json = new StringBuilder();
		asJson(json,o,0,ig);
		return json.toString();
	}
	
	
	private static void asJson(StringBuilder json,Object o,int level,String ...ig){
		if(o==null){
			json.append("null");
		}else if(o instanceof String){
			json.append('"').append(o).append('"');
			return;
		}else if (o instanceof Integer
				||o instanceof Double
				||o instanceof Long
				||o instanceof Float
				||o instanceof Short
				||o instanceof Byte
				||o instanceof BigDecimal
				||o instanceof Boolean
				){
			json.append(o);
		}else if (o instanceof Date){
			json.append('"').append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o)).append('"');
		}else if (o instanceof Array){
			jsonArray(json,(Object[])o,level,ig);
		}else if (o instanceof Map){
			jsonMap(json,(Map)o,level,ig);
		}else if (o instanceof Collection){
			jsonCollection(json,(Collection)o,level,ig);
		}else{
			jsonObject(json,o,level,ig);
		}
	}
	
	private static void jsonCollection(StringBuilder json,Collection co,int level,String ...ig){
		json.append('[');
		boolean isFirst=true;
		for(Object o:co){
			if(isFirst){isFirst=false;}else{json.append(',');}
			asJson(json,o,level+1,ig);
		}
		json.append(']');
	}
	private static void jsonArray(StringBuilder json,Object[] co,int level,String ...ig){
		json.append('[');
		boolean isFirst=true;
		for(Object o:co){
			if(isFirst){isFirst=false;}else{json.append(',');}
			asJson(json,o,level+1,ig);
		}
		json.append(']');
	}
	
	private static boolean contain(Object o,String ...ig) {
		if(ig!=null&&ig.length>0)
			for(String i:ig) {
				if(i!=null&&i.equals(o))return true;
			}
		return false;
	}
	private static void jsonMap(StringBuilder json,Map map,int level,String ...ig){
		json.append('{');
		boolean isFirst=true;
		for(Object o:map.entrySet()){
			Map.Entry e=(Map.Entry)o;
			Object k=e.getKey();
			if(contain(k,ig))continue;
			if(e.getValue()==null)continue;
			if(isFirst){isFirst=false;}else{json.append(',');}
			json.append('"').append(e.getKey()).append("\" : ");
			asJson(json,e.getValue(),level+1,ig);
		}
		json.append('}');
	}
	
	private static String firstLower(String str){
		char [] ca=str.toCharArray();
		if(ca[0]<91)ca[0]+=32;
		return new String(ca);
	}
	private static void jsonObject(StringBuilder json,Object o,int level,String ...ig){
		json.append('{');
		boolean isFirst=true;
		Method [] methods = o.getClass().getMethods();//getDeclaredMethods();
		for(Method m:methods){
			String name =m.getName();
			if(!name.startsWith("get"))continue;
			if("getClass".equals(name))continue;
			Object value=null;
			try {
				value=m.invoke(o, null);
			} catch (Exception e) {
				e.printStackTrace();
				//log.warn(" fail to get value:",o.getClass(),m.getName(), e);
			} 
			if(value==null)continue;
			name=firstLower(name.substring(3));
			if(contain(name,ig))continue;
			if(isFirst){isFirst=false;}else{json.append(',');}
			json.append('"').append(name).append("\" : ");
			asJson(json,value,level+1,ig);
		}
		json.append('}');
	}
	
	
	
	

}
