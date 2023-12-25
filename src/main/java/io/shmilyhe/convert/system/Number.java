package io.shmilyhe.convert.system;

import io.shmilyhe.convert.callee.IFunction;

public class Number {
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
    public static void main(String[] args) {
        System.out.println("10.1".matches("\\d+\\.\\d+"));
        System.out.println("10a1".matches("\\d+\\.\\d+"));
        System.out.println("101".matches("\\d+\\.\\d+"));
    }
}
