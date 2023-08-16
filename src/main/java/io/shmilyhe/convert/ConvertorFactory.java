package io.shmilyhe.convert;

import java.util.HashMap;

import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.impl.ComplexConvertor;
import io.shmilyhe.convert.impl.Getter;
import io.shmilyhe.convert.impl.Remove;
import io.shmilyhe.convert.impl.SelfGetter;
import io.shmilyhe.convert.impl.Setter;
import io.shmilyhe.convert.tools.ExpCalculate;

public class ConvertorFactory {

    /**
     * 解析单条指令
     * @param exp
     * @return
     */
    public IConvertor func(String exp){
        return func(exp,0);
    }

    /**
     * 解析单条函数指令
     * @param exp
     * @return
     */
    public IConvertor func(String exp,int line){
        String str[] =exp.split("\\(|\\)|\\,");
        String f=str[0];
        if("set".equals(f.trim())){
            if(str.length !=3||!str[1].startsWith(".")){throw  new RuntimeException("syntax error:"+exp+" at line:"+line);}

            //final IGet get =str[2].startsWith(".")?new Getter(removeRootString(str[2])):new ConstantGetter(str[2]);
            final IGet get =ExpCalculate.getExpression(str[2], line);
            final Setter set = new Setter(removeRootString(str[1]));
            return data->{ set.set(data, get.get(data));return data;};
        }else if("move".equals(f.trim())){
            String gStr=str[1];
            if(str.length !=3
            ||!str[1].startsWith(".")
            ||!str[2].startsWith(".")){throw  new RuntimeException("syntax error(move):"+exp+" at line:"+line);}
            if(".".equals(gStr)){
                final Setter set = new Setter(removeRootString(str[2]));
                final SelfGetter get = new SelfGetter();
                return data->{  HashMap m= new HashMap(); set.set(m, get.get(data));return m;};
            }
            final Getter get = new Getter(removeRootString(str[1]));
            final Setter set = new Setter(removeRootString(str[2]));
            final Remove remove= new Remove(removeRootString(str[1]));
            return data->{ set.set(data, get.get(data));remove.remove(data);return data;};
        }else if("remove".equals(f.trim())){
            if(str.length !=2||!str[1].startsWith(".")){throw  new RuntimeException("syntax error(remove):"+exp+" at line:"+line);}
            final Remove remove= new Remove(removeRootString(str[1]));
            return data->{remove.remove(data);return data;};
        }else if("setNotExists".equals(f.trim())){
            if(str.length !=3||!str[1].startsWith(".")){throw  new RuntimeException("syntax error(setNotExists):"+exp+" at line:"+line);}

            //final IGet get =str[2].startsWith(".")?new Getter(removeRootString(str[2])):new ConstantGetter(str[2]);
            final IGet get =ExpCalculate.getExpression(str[2], line);
            final IGet old= new Getter(removeRootString(str[1]));
            final Setter set = new Setter(removeRootString(str[1]));
            return data->{ 
                Object oldvalue=old.get(data);
                if(oldvalue==null||"".equals(oldvalue)){
                    set.set(data, get.get(data));
                }  
                return data; 
            };
        }else if("print".equals(f.trim())){
            if(str.length !=2){throw  new RuntimeException("syntax error:"+exp+" at line:"+line);}
            //final IGet get =str[2].startsWith(".")?new Getter(removeRootString(str[2])):new ConstantGetter(str[2]);
            final IGet get =ExpCalculate.getExpression(str[2], line);
            return data->{ 
                Object oldvalue=get.get(data);
                System.out.println(oldvalue); 
                return data; 
            };
        }else if("convert".equals(f.trim())){
            
        }
        return null;
    }


    /**
     * 解析多条指令
     * @param commands
     * @return
     */
    public IConvertor getConvertor(String commands){
        ComplexConvertor convertor = new ComplexConvertor();
        String[] cs =commands.split("[\r\n]+");
        int line=0;
        for(String c :cs){
            line++;
            if(c==null||c.length()==0)continue;
            c=c.trim();
            if(c.startsWith("#"))continue;
            IConvertor ic=null;
            if(isAssignment(c)){
                ic =assignment(c,line);
            }else{
                ic = func(c,line);
            }
             
            if(ic!=null)convertor.addConvertor(ic);
        }
        return convertor;
    }

    IConvertor assignment(String exp,Integer line){
        String a=exp.substring(0, exp.indexOf('=')).trim();
        String v=exp.substring(exp.indexOf('=')+1).trim();
        if(!a.startsWith(".")){throw  new RuntimeException("syntax error:"+exp+" at line:"+line);}

            //final IGet get =v.startsWith(".")?new Getter(removeRootString(v)):new ConstantGetter(v);
            final IGet get = ExpCalculate.getExpression(v, line);
            final Setter set = new Setter(removeRootString(a));
            return data->{ set.set(data, get.get(data));return data;};
    }

    //ASSIGNMENT
    private boolean isAssignment(String str){
        if(str.indexOf('=')>-1)return true;
        return false;
    }

    private boolean isForEach(String str){
        return false;
    }

    private boolean isCondition(String str){
        return false;
    }


    /**
     * 移除表达式的 “.”
     * @param s
     * @return
     */
    private String removeRootString(String s){
        if(s==null)return null;
        s=s.trim();
        if(s.startsWith("."))return s.substring(1);
        return s;
    }


    public static void main(String[] args){
       String str= "123123\r23242342\r\ndfsdfdsf\r\r\r\r\rgggg";
       String[] strs =str.split("[\r\n]+");
       for(String s :strs){
        System.out.println(s);
       }
    }
}
