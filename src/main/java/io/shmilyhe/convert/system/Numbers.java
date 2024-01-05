package io.shmilyhe.convert.system;


import io.shmilyhe.convert.callee.IFunction;

public class Numbers {
    public static IFunction number(){
        return (param,env)->{
            if(param==null||param.size()==0) return 0;
            Object arg1 =param.get(0);
            if(arg1==null)return 0;
            if(arg1 instanceof Integer
               || arg1 instanceof Long
               || arg1 instanceof Double
               || arg1 instanceof Float
            ){
                return arg1;
            }else if(arg1 instanceof String){
                String vv = (String) arg1;
                if(vv.matches("\\d+")){
                    if(vv.length()>10)return Long.parseLong(vv);
                    return Integer.parseInt(vv);
                }else if(vv.matches("\\d+\\.\\d+"))
                return Double.parseDouble(vv);
            }
            return 0;
        };
    }

    public static IFunction toInt(){
        return (param,env)->{
            if(param==null||param.size()==0) return 0;
            Object arg1 =param.get(0);
            return getInteger(arg1);
        };
    }

    public static IFunction toLong(){
        return (param,env)->{
            if(param==null||param.size()==0) return 0;
            Object arg1 =param.get(0);
            return getLong(arg1);
        };
    }

    public static IFunction toByte(){
        return (param,env)->{
            if(param==null||param.size()==0) return 0;
            Object arg1 =param.get(0);
            return getByte(arg1);
        };
    }

    public static IFunction toDouble(){
        return (param,env)->{
            if(param==null||param.size()==0) return 0;
            Object arg1 =param.get(0);
            return getDouble(arg1);
        };
    }
 

    public static int getInteger(Object o){
        if(o==null)return 0;
        if(o instanceof Integer)return (Integer)o;
        if(o instanceof Short)return ((Short)o).intValue();
        if(o instanceof Long)return ((Long)o).intValue();
        if(o instanceof Double)return ((Double)o).intValue();
        if(o instanceof String){
            try{
                return Integer.parseInt(o.toString());
            }catch(Exception e){
                return 0;
            }
        }
        return 0;
    }
    public static byte getByte(Object o){
        if(o==null)return 0;
        if(o instanceof Integer)return ((Integer)o).byteValue();
        if(o instanceof Short)return ((Short)o).byteValue();
        if(o instanceof Long)return ((Long)o).byteValue();
        if(o instanceof Double)return ((Double)o).byteValue();
        if(o instanceof String){
            try{
                return Byte.valueOf(o.toString());
            }catch(Exception e){
                return 0;
            }
        }
        return 0;
    }
    public static long getLong(Object o){
        if(o==null)return 0;
        if(o instanceof Integer)return ((Integer)o).longValue();
        if(o instanceof Short)return ((Short)o).longValue();
        if(o instanceof Long)return ((Long)o).longValue();
        if(o instanceof Double)return ((Double)o).longValue();
        if(o instanceof String){
            try{
                return Long.valueOf(o.toString());
            }catch(Exception e){
                return 0;
            }
        }
        return 0;
    }

    public static double getDouble(Object o){
        if(o==null)return 0;
        if(o instanceof Double)return (Double)o;
        if(o instanceof Integer)return ((Integer)o).doubleValue();
        if(o instanceof Short)return ((Short)o).doubleValue();
        if(o instanceof Long)return ((Long)o).doubleValue();
        if(o instanceof String){
            try{
                return Double.valueOf(o.toString());
            }catch(Exception e){
                return 0;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("10.1".matches("\\d+\\.\\d+"));
        System.out.println("10a1".matches("\\d+\\.\\d+"));
        System.out.println("101".matches("\\d+\\.\\d+"));
    }
}
