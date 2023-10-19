package io.shmilyhe.convert.impl;

import io.shmilyhe.convert.api.ExpPartVo;
import io.shmilyhe.convert.api.IDataAccess;
import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.tools.DEBUG;
import io.shmilyhe.convert.tools.ExpEnv;

public class Getter implements IGet{
    IDataAccess da;
    private boolean isVar;
    String exp;

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

    @Override
    public Object get(Object data,ExpEnv env) {
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
