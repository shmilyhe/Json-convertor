package io.shmilyhe.convert.tools;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
public class JBean {



    public static <T> T toBean(String json, T t){
        try {
            return (T) mapToBean((Map)SimpleJson.parse(json).getRoot(),t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T mapToBean(Map<String, Object> map, T t) throws IntrospectionException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {

        BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Method writeMethod;
        String propertyName;
        Class classFromPropertyType;
        Constructor constructor;
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            writeMethod = propertyDescriptor.getWriteMethod();
            propertyName = propertyDescriptor.getName();
            classFromPropertyType = propertyDescriptor.getPropertyType();
            if (map.containsKey(propertyName)) {
               // constructor = classFromPropertyType.getConstructor(String.class);
                //writeMethod.invoke(t, constructor.newInstance(map.get(propertyName)));
                Object v =map.get(propertyName);
                if(classFromPropertyType.isInstance(v)){
                    writeMethod.invoke(t, v);
                }else  if(Map.class.isInstance(v)){
                    writeMethod.invoke(t, mapToBean((Map)v,classFromPropertyType.newInstance()));
                }else  if(Integer.class.equals(classFromPropertyType)){
                    writeMethod.invoke(t, getInteger(v));
                }else  if(Long.class.equals(classFromPropertyType)){
                    writeMethod.invoke(t, getLong(v));
                }else  if(boolean.class.equals(classFromPropertyType)||Boolean.class.equals(classFromPropertyType)){
                    writeMethod.invoke(t, getBoolean(v));
                    //System.out.println(propertyName+"|"+classFromPropertyType+"|"+v.getClass()+"|"+v);
                }else  if(Date.class.equals(classFromPropertyType)){
                    writeMethod.invoke(t, getDate(v));
                }else {
                    System.out.println(propertyName+"|"+classFromPropertyType+"|"+v.getClass());
                }

            }
        }
        return t;
    }

    private static Integer getInteger(Object o){
        if(o instanceof String){
            return Integer.parseInt((String)o);
        }else if(o instanceof Long){
            return ((Long)o).intValue();
        }else if(o instanceof Double){
            return ((Double)o).intValue();
        }else if(o instanceof Integer){
            return ((Integer)o);
        }
        return null;
    }

    private static Long getLong(Object o){
        if(o instanceof String){
            return Long.parseLong((String)o);
        }else if(o instanceof Long){
            return ((Long)o);
        }else if(o instanceof Double){
            return ((Double)o).longValue();
        }else if(o instanceof Integer){
            return ((Integer)o).longValue();
        }
        return null;
    }

    private static Boolean getBoolean(Object o){
        if(o instanceof String){
            return "true".equals(o);
        }else if(o instanceof Long){
            return ((Long)o>0);
        }else if(o instanceof Double){
            return ((Double)o>0);
        }else if(o instanceof Integer){
            return ((Integer)o>0);
        }else if(o instanceof Boolean){
            return (Boolean)o;
        }
        return false;
    }

    private static Date getDate(Object raw){
        if(raw instanceof Date)return (Date)raw;
        if(raw instanceof Number)return new Date(((Number)raw).longValue());
        if(raw instanceof String)return StringValue.toDate((String)raw);
        return null;
    }

}
