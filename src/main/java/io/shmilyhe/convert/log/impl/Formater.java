package io.shmilyhe.convert.log.impl;

/**
 * 输出格式化
 */
public class Formater {
static final String DELIM_STR = "{}";

public static String format(String format,Object... args){
    if(args==null||args.length==0)return format;
    StringBuilder sb = new StringBuilder();
    format(sb,format,args);
    return sb.toString();
}


public static void format(StringBuilder sb,String format,Object... args){
    if(format==null)return ;
    if(args==null||args.length==0){
        sb.append(format);
        return ;
    }
    int start =0;
    for(Object o:args){
        int  j = format.indexOf(DELIM_STR, start);
        if(j<0){
            sb.append(format,start,format.length());
            start =format.length();
            break;
        }else{
            sb.append(format,start,j);
            sb.append(o);
        }
        start=j+2;
    }
    if(start<format.length()){
        sb.append(format,start,format.length());
    }
}

public static void main(String[] args) {
    System.out.println(format("my name is years old,nice to meet you! "));
    System.out.println(format("my name is {} {} years old,nice to meet you! ","eric"));
    System.out.println(format("my name is {} {} years old,nice to meet you! ","eric",37));
    System.out.println(format("my name is {} {} years old,nice to meet you! ","a",34,1000));
}
    
}
