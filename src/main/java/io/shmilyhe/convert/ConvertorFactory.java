package io.shmilyhe.convert;

import java.util.HashMap;

import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.impl.ComplexConvertor;
import io.shmilyhe.convert.impl.ConstantGetter;
import io.shmilyhe.convert.impl.Getter;
import io.shmilyhe.convert.impl.Remove;
import io.shmilyhe.convert.impl.SelfGetter;
import io.shmilyhe.convert.impl.Setter;

public class ConvertorFactory {

    /**
     * 解析单条指令
     * @param exp
     * @return
     */
    public IConvertor exp(String exp){
        String str[] =exp.split("\\(|\\)|\\,");
        String f=str[0];
        if("set".equals(f.trim())){
            if(str.length !=3||!str[1].startsWith("$")){throw  new RuntimeException("syntax error:"+exp);}

            final IGet get =str[2].startsWith("$")?new Getter(removeRootString(str[2])):new ConstantGetter(str[2]);
            final Setter set = new Setter(removeRootString(str[1]));
            return data->{ set.set(data, get.get(data));return data;};
        }else if("move".equals(f.trim())){
            String gStr=str[1];
            if(str.length !=3
            ||!str[1].startsWith("$")
            ||!str[2].startsWith("$")){throw  new RuntimeException("syntax error:"+exp);}
            if("$".equals(gStr)){
                final Setter set = new Setter(removeRootString(str[2]));
                final SelfGetter get = new SelfGetter();
                return data->{  HashMap m= new HashMap(); set.set(m, get.get(data));return m;};
            }
            final Getter get = new Getter(removeRootString(str[1]));
            final Setter set = new Setter(removeRootString(str[2]));
            final Remove remove= new Remove(removeRootString(str[1]));
            return data->{ set.set(data, get.get(data));remove.remove(data);return data;};
        }else if("remove".equals(f.trim())){
            if(str.length !=2||!str[1].startsWith("$")){throw  new RuntimeException("syntax error:"+exp);}
            final Remove remove= new Remove(removeRootString(str[1]));
            return data->{remove.remove(data);return data;};
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
        for(String c :cs){
            IConvertor ic = exp(c);
            if(ic!=null)convertor.addConvertor(ic);
        }
        return convertor;
    }


    /**
     * 移除表达式的 “$.”
     * @param s
     * @return
     */
    private String removeRootString(String s){
        if(s==null)return null;
        s=s.trim();
        if(s.startsWith("$"))return s.substring(2);
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
