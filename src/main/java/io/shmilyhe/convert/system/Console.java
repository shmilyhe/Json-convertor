package io.shmilyhe.convert.system;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.log.Log;
import io.shmilyhe.convert.log.api.Logger;


public class Console {
    static Logger log = Log.getLogger(Console.class);
    
    public static IFunction info(){
        return (args,env)->{
            String format=null;
            try{               
                Object[] ar = new Object[args.size()-1];
                int i=0;
                for(Object o:args){
                    if(i==0){
                        if(o instanceof String){
                            format=(String)o;
                        }else{
                            format=String.valueOf(o);
                        }
                    }else{
                        ar[i-1]=o;
                    }
                    i++;
                }
                log.info(format, ar);

            }catch(Exception e){
                //return  format;
            }
            return null;
        };
    }
    public static IFunction debug(){
        return (args,env)->{
            String format=null;
            try{               
                Object[] ar = new Object[args.size()-1];
                int i=0;
                for(Object o:args){
                    if(i==0){
                        if(o instanceof String){
                            format=(String)o;
                        }else{
                            format=String.valueOf(o);
                        }
                    }else{
                        ar[i-1]=o;
                    }
                    i++;
                }
                log.debug(format, ar);

            }catch(Exception e){
                //return  format;
            }
            return null;
        };
    }

    public static IFunction error(){
        return (args,env)->{
            String format=null;
            try{               
                Object[] ar = new Object[args.size()-1];
                int i=0;
                for(Object o:args){
                    if(i==0){
                        if(o instanceof String){
                            format=(String)o;
                        }else{
                            format=String.valueOf(o);
                        }
                    }else{
                        ar[i-1]=o;
                    }
                    i++;
                }
                log.error(format, ar);

            }catch(Exception e){
                //return  format;
            }
            return null;
        };
    }

    public static IFunction warn(){
        return (args,env)->{
            String format=null;
            try{               
                Object[] ar = new Object[args.size()-1];
                int i=0;
                for(Object o:args){
                    if(i==0){
                        if(o instanceof String){
                            format=(String)o;
                        }else{
                            format=String.valueOf(o);
                        }
                    }else{
                        ar[i-1]=o;
                    }
                    i++;
                }
                log.warn(format, ar);

            }catch(Exception e){
                //return  format;
            }
            return null;
        };
    }
}
