package io.shmilyhe.convert.log.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.shmilyhe.convert.log.api.Logger;

public class DefaultLogger  implements Logger{
    static boolean isDebug=false;

    static {
        String db = System.getenv("DEBUG");
        if("true".equalsIgnoreCase(db))isDebug=true;
        System.out.println("isDebug:"+isDebug);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    String className="";
    public DefaultLogger(Class clazz){
        if(clazz!=null)className=clazz.getName();
    }

    public DefaultLogger(){
        className="";
    }
    
    protected void log(String level,String format, Object... arguments){
        StringBuilder sb = new StringBuilder();
        sb.append(sdf.format(new Date())).append("\t[").append(level).append("]\t").append(className).append(":");
        Formater.format(sb, format, arguments);
        System.out.println(sb);
    }

    protected void error(String level,String format,Throwable t){
        StringBuilder sb = new StringBuilder();
        sb.append(sdf.format(new Date())).append("\t[").append(level).append("]\t").append(className).append(":");
         StringWriter sw = new StringWriter();
        PrintWriter pr=new PrintWriter(sw);
        t.printStackTrace(pr);
        sb.append(sw.toString());
        System.out.println(sb);
    }

    @Override
    public void debug(String format, Object... arguments) {
        if(!isDebug)return;
        log("DEBUG",format,arguments);
    }

    @Override
    public void info(String format, Object... arguments) {
        log("INFO",format,arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        error("WARN",msg,t);
    }

    @Override
    public void warn(String format, Object... arguments) {
        log("WARN",format,arguments);
    }

    @Override
    public void error(String format, Object... arguments) {
        log("ERROR",format,arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        error("ERROR",msg,t);
    }
    
}
