package io.shmilyhe.convert.system;

import java.util.Collection;
import java.util.List;

import io.shmilyhe.convert.callee.IFunction;

public class Bytes {
    
    private static Object listGet(List list,int index){
        if(list==null)return null;
        if(index<list.size()){
            return list.get(index);
        }else{
            return null;
        }
    }

    public static IFunction fromHex(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            if(arg1 instanceof String){
                System.out.println("from hex:"+arg1);
                return io.shmilyhe.convert.tools.Bytes.hexToBytes(arg1.toString());
            } 
            return null;
        };
    }

     public static IFunction toHex(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            if(arg1 instanceof byte[]){
                return io.shmilyhe.convert.tools.Bytes.toHexString((byte[])arg1);
            } 
            if(arg1 instanceof Byte){
                return io.shmilyhe.convert.tools.Bytes.toHexString((byte)arg1);
            } 
            if(arg1 instanceof Collection){
                try{
                    Collection c=(Collection)arg1;
                    byte[] ar= new byte[c.size()];
                    int i=0;
                    for(Object o:c){
                        ar[i++]=getByte(o);
                    }
                    return io.shmilyhe.convert.tools.Bytes.toHexString(ar);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        };
    }
    public static byte getByte(Object o){
        if(o instanceof Byte)return (byte)o;
        if(o instanceof Integer)return ((Integer)o).byteValue();
        if(o instanceof Long)return ((Long)o).byteValue();
        return 0;
    }
}
