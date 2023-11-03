package io.shmilyhe.convert.ext;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class FormString {
	public static String asForm(Object o){
		StringBuilder json = new StringBuilder();
		asForm(json,o,0);
		return json.toString();
	}
	
	private static String encode(String str) {
		if(str==null)return "";
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	
	private static void asForm(StringBuilder json,Object o,int level){
		if(o==null){
			//json.append("null");
		}else if(o instanceof String||o instanceof Boolean){
			json.append(encode((String)o));
			return;
		}else if (o instanceof Integer
				||o instanceof Double
				||o instanceof Long
				||o instanceof Float
				||o instanceof Short
				||o instanceof Byte
				||o instanceof BigDecimal
				){
			json.append(o);
		}else if (o instanceof Date){
			json.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o));
		}else if (o instanceof Array){
			jsonArray(json,(Object[])o,level);
		}else if (o instanceof Map){
			jsonMap(json,(Map)o,level);
		}else if (o instanceof Collection){
			jsonCollection(json,(Collection)o,level);
		}else{
			jsonObject(json,o,level);
		}
	}
	
	private static void jsonCollection(StringBuilder json,Collection co,int level){
		boolean isFirst=true;
		for(Object o:co){
			if(isFirst){isFirst=false;}else{json.append(',');}
			asForm(json,o,level+1);
		}
	}
	private static void jsonArray(StringBuilder json,Object[] co,int level){
		boolean isFirst=true;
		for(Object o:co){
			if(isFirst){isFirst=false;}else{json.append(',');}
			asForm(json,o,level+1);
		}
	}
	
	private static void jsonMap(StringBuilder json,Map map,int level){
		boolean isFirst=true;
		for(Object o:map.entrySet()){
			Map.Entry e=(Map.Entry)o;
			if(e.getValue()==null)continue;
			if(isFirst){isFirst=false;}else{json.append('&');}
			json.append(e.getKey()).append("=");
			asForm(json,e.getValue(),level+1);
		}
	}
	
	private static String firstLower(String str){
		char [] ca=str.toCharArray();
		if(ca[0]<91)ca[0]+=32;
		return new String(ca);
	}
	private static void jsonObject(StringBuilder json,Object o,int level){
		boolean isFirst=true;
		Method [] methods = o.getClass().getDeclaredMethods();
		for(Method m:methods){
			String name =m.getName();
			if(!name.startsWith("get"))continue;
			Object value=null;
			try {
				value=m.invoke(o, null);
			} catch (Exception e) {
				//e.printStackTrace();
			} 
			if(value==null)continue;
			name=firstLower(name.substring(3));
			if(isFirst){isFirst=false;}else{json.append('&');}
			json.append(name).append("=");
			asForm(json,value,level+1);
		}
	}
}
