package io.shmilyhe.convert.log;

import io.shmilyhe.convert.log.api.ILoggerFactory;
import io.shmilyhe.convert.log.api.Logger;
import io.shmilyhe.convert.log.impl.LoggerFactory;
import io.shmilyhe.convert.log.impl.SLFJLoggerFactory;

public class Log {

    private static ILoggerFactory fac;

    static{
        try{
           Class clazz = Class.forName("org.slf4j.LoggerFactory");
           if(clazz!=null){
                SLFJLoggerFactory f=new SLFJLoggerFactory();
                Logger log = f.getLogger(Log.class);
                log.info("JSONConvertor 加载SL4J成功");
                fac=f;
            }else{
                fac= new LoggerFactory();
            }
        }catch(Throwable e){
            fac= new LoggerFactory();
            Logger log =fac.getLogger(Log.class);
            log.info("JSONConvertor 加载默认日志器！");
        }
        
    }

    public static void setLoggerFactory(ILoggerFactory factory){
        fac=factory;
    }

    public static Logger getLogger(Class clazz){
        return fac.getLogger(clazz);
    }
}
