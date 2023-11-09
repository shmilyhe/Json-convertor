package io.shmilyhe.convert.impl;

import java.math.BigDecimal;

import io.shmilyhe.convert.api.ExpPartVo;
import io.shmilyhe.convert.api.IDataAccess;
import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.system.SystemFunction;
import io.shmilyhe.convert.tools.DEBUG;
import io.shmilyhe.convert.tools.ExpEnv;

public class Getter implements IGet{
    IDataAccess da;
    private boolean isVar;
    String exp;

    protected boolean minus;

    public Getter setMinus(boolean m){
        this.minus=m;
        return this;
    }

    public Getter(String ext){
        this(ext,false);
    }

    public Getter(String ext,boolean isVar){
        this.isVar=isVar;
        exp=ext;
        ExpPartVo vo =  TokenizeExpress.tokenize(ext);
        for(;vo!=null;vo=vo.getNext()){
            IDataAccess ida =null;
            if(vo.getType()==0){
                ida=new MapDataAccess(vo.getKey(),false);
            }else if(vo.getType()==1){
                ida=new ArrayDataAccess(vo.getIndex(),vo.getType()==2);
            }else if(vo.getType()==2){
                ida=new ArrayDataAccess(vo.getKey());
            }
            if(da==null){
                da=ida;
            }else{
                da.append(ida);
            }
        }
    }

    public Object get1(Object data,ExpEnv env) {
        Object b=data;
        if(isVar)b=env;
        IDataAccess flag =da;       
        for(;flag!=null;flag=flag.next()){
            if(flag.next()==null){
                return flag.get(b);
            }
            b =flag.get(b);
            
            if(b==null){
                //System.out.println("out:"+flag);
                return null;
            }  
        }
        return null;
    }
    @Override
    public Object get(Object data,ExpEnv env) {
        
        Object o =get1(data,env);
        System.out.println("m:"+minus+" v:"+o);
        if(o==null)return null;
        if(this.minus){
            return SystemFunction.revert(o);
        }
        return o;
    }
     public boolean isVar() {
        return isVar;
    }

    public Getter setVar(boolean isVar) {
        this.isVar = isVar;
        return this;
    }

    public String toString(){
        return "getter:"+this.exp;
    }
    
}
