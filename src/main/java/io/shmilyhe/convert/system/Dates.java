package io.shmilyhe.convert.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.shmilyhe.convert.callee.IFunction;

public class Dates {
    
    
    public static IFunction parse(){
        return (param,env)->{
            if(param==null||param.size()==0) return null;
            Object arg1 =param.get(0);
            if(arg1==null)return null;
            if(arg1 instanceof Long){
                return new Date((Long)arg1);
            }else if(arg1 instanceof Date){
                return arg1;
            }else if(arg1 instanceof String){
                Object arg2 = listGet(param,1);
                String dateString =arg1.toString();
                String format =null;
                if(arg2 instanceof String)format=arg2.toString();
                return parse(dateString,format);
            }else {
                throw new RuntimeException("unkown date:"+arg1);
            }
        };
    }
    public static IFunction format(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Object arg2= listGet(param,1);
            if(arg1==null)return null;
            String format =null;
            if(arg2 instanceof String)format=arg2.toString();
            return format(arg1, format);
        };
    }

    public static IFunction getWeek(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Date date =getDate(arg1);
            if(date==null)return null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        };
    }
    public static IFunction getDay(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Date date =getDate(arg1);
            if(date==null)return null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_WEEK);
        };
    }
    public static IFunction getDate(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Date date =getDate(arg1);
            if(date==null)return null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DATE);
        };
    }
    public static IFunction getTime(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Date date =getDate(arg1);
            if(date==null)return null;
            return date.getTime();
        };
    }
    public static IFunction getMonth(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Date date =getDate(arg1);
            if(date==null)return null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH);
        };
    }

    public static IFunction getHour(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Date date =getDate(arg1);
            if(date==null)return null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.HOUR);
        };
    }
    public static IFunction getYear(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Date date =getDate(arg1);
            if(date==null)return null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        };
    }
    public static IFunction getMinute(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Date date =getDate(arg1);
            if(date==null)return null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MINUTE);
        };
    }

    public static IFunction getSecond(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Date date =getDate(arg1);
            if(date==null)return null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.SECOND);
        };
    }

private static Date getDate(Object arg1){
    if(arg1 instanceof Long){
                return new Date((Long)arg1);
    }else if(arg1 instanceof Date){
                return (Date)arg1;
    }else if(arg1 instanceof String){
        return parse(arg1.toString(),null);
    }
    return null;
}

private static String format(Object arg1,String format){
    Date date=null;
    if(arg1 instanceof Long){
        date= new Date((Long)arg1);
    }else if(arg1 instanceof Date){
        date=(Date)arg1;
    }else if(arg1 instanceof String){
        date=parse(arg1.toString(),null);
    } 
    if(date==null)return null;
    if(format==null){
        format="yyyy-MM-dd HH:mm:ss";
    }
    return new SimpleDateFormat(format).format(date);
}


private static Date parse(String dateString,String format){
    if(format==null){
        if(dateString.indexOf('-')>1){
            if(dateString.length()<10){
                format="yyyy-M-d";
            }else if(dateString.length()==10){
                format="yyyy-MM-dd";
            }else if(dateString.length()<16){
                format="yyyy-M-d H:m";
            }else if(dateString.length()==16){
                format="yyyy-MM-dd HH:mm";
            }else if(dateString.length()==19){
                format="yyyy-MM-dd HH:mm:ss";
            }
        }else if(dateString.indexOf('/')>1){
            if(dateString.length()<10){
                format="yyyy/M/d";
            }else if(dateString.length()==10){
                format="yyyy/MM/dd";
            }else if(dateString.length()<16){
                format="yyyy/M/d H:m";
            }else if(dateString.length()==16){
                format="yyyy/MM/dd HH:mm";
            }else if(dateString.length()==19){
                format="yyyy/MM/dd HH:mm:ss";
            }
        }else{
            if(dateString.length()==8){
                format="yyyyMMdd";
            }else if(dateString.length()==14){
                format="yyyyMMddHHmmss";
            }else if(dateString.length()==12){
                format="yyyyMMddHHmm";
            }
        }
    }
    try {
        return new SimpleDateFormat(format).parse(dateString);
    } catch (ParseException e) {
        return null;
    }
}
   
    private static Object listGet(List list,int index){
        if(list==null)return null;
        if(index<list.size()){
            return list.get(index);
        }else{
            return null;
        }
    }

     public static void main(String[] args) {
        System.out.println(parse("20231211",null));
        System.out.println(parse("2023/12/11",null));
        System.out.println(parse("2023-12-11",null));
        System.out.println(parse("2023/12/1",null));
        System.out.println(parse("2023-12-1",null));
        System.out.println(parse("202312111233",null));
        System.out.println(parse("2023/12/11 12:33",null));
        System.out.println(parse("2023-12-11 12:33",null));
        System.out.println(parse("20231211123301",null));
        System.out.println(parse("2023/12/11 12:33:01",null));
        System.out.println(parse("2023-12-11 12:33:01",null));

        System.out.println(format("2023-12-11 12:33:01",null));
        System.out.println(format("2023/12/11 12:33:01",null));
        System.out.println(format(new Date(),null));
        System.out.println(format(new Date().getTime(),null));
        System.out.println(format(new Date(),"yyyyMMddHHmmss"));
    }
}
