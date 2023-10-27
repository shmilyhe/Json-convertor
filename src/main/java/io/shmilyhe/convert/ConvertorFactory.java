package io.shmilyhe.convert;

import java.util.HashMap;

import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.impl.BaseConvertor;
import io.shmilyhe.convert.impl.ComplexConvertor;
import io.shmilyhe.convert.impl.EachConvertor;
import io.shmilyhe.convert.impl.Getter;
import io.shmilyhe.convert.impl.IfConvertor;
import io.shmilyhe.convert.impl.Remove;
import io.shmilyhe.convert.impl.SelfGetter;
import io.shmilyhe.convert.impl.Setter;
import io.shmilyhe.convert.tools.DEBUG;
import io.shmilyhe.convert.tools.ExpCalculate;

/**
 * 脚本解析
 * 
 */
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
        DEBUG.debug("parse script:",exp," at line:",line);
        String str[] =exp.split("\\(|\\)|\\,");
        String f=str[0];
        if("set".equals(f.trim())){
            
            if(str.length !=3){throw  new RuntimeException("syntax error:"+exp+" at line:"+line);}
            //final IGet get =str[2].startsWith(".")?new Getter(removeRootString(str[2])):new ConstantGetter(str[2]);
            final IGet get =ExpCalculate.getExpression(str[2], line);
            final Setter set = new Setter(removeRootString(str[1]));
            set.setVar(!str[1].startsWith("."));
            
            return (data,env)->{ 
                DEBUG.debug("========start:",exp,"========"); 
                Object d = get.get(data,env);
                DEBUG.debug("set:",exp,"=",d);
                set.set(set.isVar()?env:data,d);
                DEBUG.debug("========end:",exp,"========"); 
                return data;
            };
        }else if("move".equals(f.trim())){
            String gStr=str[1];
            if(str.length !=3
            ||!str[1].startsWith(".")
            ||!str[2].startsWith(".")){throw  new RuntimeException("syntax error(move):"+exp+" at line:"+line);}
            if(".".equals(gStr)){
                final Setter set = new Setter(removeRootString(str[2]));
                final SelfGetter get = new SelfGetter();
                return (data,env)->{
                    DEBUG.debug("========start:",exp,"========"); 
                    HashMap m= new HashMap(); set.set(m, get.get(data,env));
                    DEBUG.debug("========end:",exp,"========"); 
                    return m;};
            }
            final Getter get = new Getter(removeRootString(str[1]));
            final Setter set = new Setter(removeRootString(str[2]));
            final Remove remove= new Remove(removeRootString(str[1]));
            return (data,env)->{ 
                DEBUG.debug("========start:",exp,"========"); 
                set.set(data, get.get(data,env));remove.remove(data);
                DEBUG.debug("========end:",exp,"========"); 
                return data;
                
            };
        }else if("del".equals(f.trim())||"remove".equals(f.trim())){
            if(str.length !=2||!str[1].startsWith(".")){throw  new RuntimeException("syntax error(remove):"+exp+" at line:"+line);}
            if(".".equals(str[1])){
                return (data,env)->{return null;};
            }
            final Remove remove= new Remove(removeRootString(str[1]));
            return (data,env)->{remove.remove(data);return data;};
        }else if("setNotExists".equals(f.trim())){
            if(str.length !=3||!str[1].startsWith(".")){throw  new RuntimeException("syntax error(setNotExists):"+exp+" at line:"+line);}

            //final IGet get =str[2].startsWith(".")?new Getter(removeRootString(str[2])):new ConstantGetter(str[2]);
            final IGet get =ExpCalculate.getExpression(str[2], line);
            final IGet old= new Getter(removeRootString(str[1]));
            final Setter set = new Setter(removeRootString(str[1]));
            return (data,env)->{ 
                DEBUG.debug("========start:",exp,"========"); 
                Object oldvalue=old.get(data,env);
                if(oldvalue==null||"".equals(oldvalue)){
                    set.set(data, get.get(data,env));
                }  
                DEBUG.debug("========end:",exp,"========"); 
                return data; 
            };
        }else if("print".equals(f.trim())){
            if(str.length !=2){throw  new RuntimeException("syntax error:"+exp+" at line:"+line);}
            //final IGet get =str[2].startsWith(".")?new Getter(removeRootString(str[2])):new ConstantGetter(str[2]);
            final IGet get =ExpCalculate.getExpression(str[1], line);
            return (data,env)->{ 
                Object oldvalue=get.get(data,env);
                System.out.println("[print]:"+oldvalue); 
                return data; 
            };
        }else if("namespace".equalsIgnoreCase(f.trim())){
            if(str.length !=2){throw  new RuntimeException("syntax error:"+exp+" at line:"+line);}
            final IGet get =ExpCalculate.getExpression(str[1], line);
            return (data,env)->{ 
                Object v=get.get(data,env);
                if(env!=null)
                env.nameSpace(String.valueOf(v));
                return data; 
            };
        }else if("global".equals(f.trim())){
            if(str.length !=3){throw  new RuntimeException("syntax error:"+exp+" at line:"+line);}
            //final IGet get =ExpCalculate.getExpression(str[1], line);
            final IGet get2 =ExpCalculate.getExpression(str[2], line);
            return (data,env)->{ 
                Object v=get2.get(data,env);
                if(env!=null)env.global(str[1],v);
                return data; 
            };
        }else if("exit".equals(f.trim())){
            return (data,env)->{
                if(str.length>1){

                }else{
                    env.exit();
                }
                DEBUG.debug("exit at line ",line," exp:",exp);
                return data; 
            };
        }
        return null;
    }


    /**
     * 解析多条指令
     * @param commands
     * @return
     */
    public IConvertor getConvertor(String commands){
        DEBUG.debug("=======parse start========");
        BaseConvertor convertor = new ComplexConvertor().setName("root");
        String[] cs =commands.split("[\r\n]+");
        int line=0;
        for(String c :cs){
            DEBUG.debug("line",line,":",c);
            line++;
            if(c==null||c.length()==0)continue;
            c=c.trim();
            if(c.startsWith("#"))continue;
            if(this.isForEach(c)){
                
                BaseConvertor bc=new EachConvertor(this.getEachExpression(c)).setName(c);
                convertor.addConvertor(bc);
                convertor=bc;
                String ep= this.getExpression(c);
                //System.out.println();
                if(ep!=null&&ep.length()>0){
                    IConvertor ic = expressionline(ep,line);
                    if(ic!=null)convertor.addConvertor(ic);
                }
            }else if(this.isCondition(c)){
                //System.out.println("if:"+c);
                BaseConvertor bc=new IfConvertor(this.getIfExpression(c)).setName(c);
                convertor.addConvertor(bc);
                convertor=bc;
                String ep= this.getExpression(c);
                DEBUG.debug("if:",ep," at line",line);
                if(ep!=null&&ep.length()>0){
                    IConvertor ic = expressionline(ep,line);
                    if(ic!=null)convertor.addConvertor(ic);
                }
            }else if(this.isBolckStart(c)){
                String ep= this.getExpression(c);
                if(ep!=null&&ep.length()>0){
                    IConvertor ic = expressionline(ep,line);
                    if(ic!=null)convertor.addConvertor(ic);
                }
            }else{
                 DEBUG.debug("func:",c);
                IConvertor ic = expressionline(c,line);
                if(ic!=null)convertor.addConvertor(ic);
            }

            if(this.isBolckEnd(c)){
                convertor=convertor.getParent();
                if(convertor==null)throw new RuntimeException("syntax error:"+c+" at line:"+line);
                DEBUG.debug("back:",convertor.getName());
                //System.out.println("back:"+convertor.getName());
            }
            
        }
       DEBUG.debug("final:",convertor.getName());
       DEBUG.debug("=======parse finished========");
        return convertor;
    }

    protected IConvertor expressionline(String c,int line){
            IConvertor ic=null;
            if(isAssignment(c)){
                ic =assignment(c,line);
            }else{
                ic = func(c,line);
            }
            return ic;
    }

    IConvertor assignment(String exp,Integer line){
        
        String a=exp.substring(0, exp.indexOf('=')).trim();
        String v=exp.substring(exp.indexOf('=')+1).trim();
        //if(!a.startsWith(".")){throw  new RuntimeException("syntax error:"+exp+" at line:"+line);}
        final IGet get = ExpCalculate.getExpression(v, line);
        if(".".equals(a)){
            return (data,env)->{ return get.get(data,env);};
        }
        final Setter set =new Setter(removeRootString(a));
        set.setVar(!a.startsWith("."));
        //System.out.println(a+" isVar:"+set.isVar());
        return (data,env)->{ 
            DEBUG.debug("========start:",exp,"========"); 
            set.set(set.isVar()?env:data, get.get(data,env));
            DEBUG.debug("========end:",exp,"========"); 
            return data;};
    }

    //ASSIGNMENT
    private boolean isAssignment(String str){
        if(str.indexOf('=')>-1)return true;
        return false;
    }

    private boolean isForEach(String line){
        if(line==null)return false;
        line=line.trim();
        if(line.startsWith("each ")
        ||line.startsWith("each("))return true;
        return false;
    }

    private boolean isCondition(String line){
        if(line==null)return false;
        line=line.trim();
        if(line.startsWith("if ")
        ||line.startsWith("if("))return true;
        return false;
    }

    private boolean isBolckStart(String line){
        if(line==null)return false;
        line=line.trim();
        if(line.startsWith("{"))return true;
        if(line.startsWith("if ")
        ||line.startsWith("if(")
        ||line.startsWith("each ")
        ||line.startsWith("each("))
        {
            return line.indexOf('{')>-1;
        }
        return false;
    }

    private boolean isBolckEnd(String line){
        if(line==null)return false;
        line=line.trim();
        return line.endsWith("}");
    }

    protected String getExpression(String line){
        if(line==null)return null;
        line=line.trim();
        line = removeifeach(line);
        return line;
    }


    protected String getIfExpression(String line){
        line=line.trim();
        line=line.substring(2);
        int p=line.indexOf('{');
        if(p>-1){
            line=line.substring(0,p);
        }
        line=line.trim();
        
        if(line.startsWith("(")&&line.endsWith(")"))
        line=line.substring(1, line.length()-1);
        //System.out.println("xxx:"+line);
        return line.trim();
    }

    protected String getEachExpression(String line){
        line=line.trim();
        line=line.substring(4);
        int p=line.indexOf('{');
        if(p>-1){
            line=line.substring(0,p);
        }
        line=line.trim();
        if(line.startsWith("(")&&line.endsWith(")"))
        line=line.substring(1, line.length()-1);
        return line.trim();
    }

    private String removeifeach(String line){
        if(line==null)return null;
        line=line.trim();
        if(line.startsWith("if ")
        ||line.startsWith("if(")
        ||line.startsWith("{")
        ||line.startsWith("each ")
        ||line.startsWith("each("))
        {
            int p=line.indexOf('{');
            if(p>-1){
                if(line.length()>p+1)
                {
                line=line.substring(p+1).trim();
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }
        int p=line.indexOf('}');
        if(p>-1){
            if(p>0){
            line=line.substring(0,p).trim();
            }
            else 
            return null;
        }
        return line;

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
