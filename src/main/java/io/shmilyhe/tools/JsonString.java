package io.shmilyhe.tools;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class JsonString {
	//static Log log =Logger.getLogger(JsonString.class);
	public static String asJsonString(Object o){
		StringBuilder json = new StringBuilder();
		asJson(json,o,0);
		return json.toString();
	}
	
	
	private static void asJson(StringBuilder json,Object o,int level){
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
				||o.getClass().equals(boolean.class)
				){
			json.append(o);
		}else if (o instanceof Date){
			json.append('"').append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o)).append('"');
		}else if (isArray(o)){
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
		json.append('[');
		boolean isFirst=true;
		for(Object o:co){
			if(isFirst){isFirst=false;}else{json.append(',');}
			asJson(json,o,level+1);
		}
		json.append(']');
	}
	private static void jsonArray(StringBuilder json,Object[] co,int level){
		json.append('[');
		boolean isFirst=true;
		for(Object o:co){
			if(isFirst){isFirst=false;}else{json.append(',');}
			asJson(json,o,level+1);
		}
		json.append(']');
	}
	
	private static void jsonMap(StringBuilder json,Map map,int level){
		json.append('{');
		boolean isFirst=true;
		for(Object o:map.entrySet()){
			Map.Entry e=(Map.Entry)o;
			if(e.getValue()==null)continue;
			if(isFirst){isFirst=false;}else{json.append(',');}
			json.append('"').append(e.getKey()).append("\":");
			asJson(json,e.getValue(),level+1);
		}
		json.append('}');
	}
	
	private static String firstLower(String str){
		char [] ca=str.toCharArray();
		if(ca[0]<91)ca[0]+=32;
		return new String(ca);
	}
	private static void jsonObject(StringBuilder json,Object o,int level){
		json.append('{');
		boolean isFirst=true;
		Method [] methods = o.getClass().getDeclaredMethods();
		BeanInfo beanInfo=null;
		try {
			beanInfo = Introspector.getBeanInfo(o.getClass());
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if(beanInfo==null){json.append('}');return;}
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		//for(Method m:methods){
			for (PropertyDescriptor pd : propertyDescriptors) {
			String name =pd.getName();
			Method m=pd.getReadMethod();
			String mname=m.getName();
			if(m.getName().equals("getClass"))continue;
			if("class".equals(name)
			||"annotations".equals(name)
			||"annotatedInterfaces".equals(name)
			||"annotatedOwnerType".equals(name)
			)continue;
			if(!mname.startsWith("get")&&!mname.startsWith("is"))continue;
			//System.out.println(mname);
			Object value=null;
			try {
				value=m.invoke(o, null);
			} catch (Exception e) {
				//e.printStackTrace();
				//log.warn(" fail to get value:",o.getClass(),m.getName(), e);
			} 
			if(value==null)continue;
			//name=firstLower(name.substring(3));
			
			if(isFirst){isFirst=false;}else{json.append(',');}
			json.append('"').append(name).append("\":");
			asJson(json,value,level+1);
		}
		json.append('}');
	}
	
	private static boolean isArray(Object o){
		if(o==null)return false;
		return o.getClass().isArray();
	}
	
	
	

}
