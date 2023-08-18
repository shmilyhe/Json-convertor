package io.shmilyhe.convert.impl;

import java.util.ArrayList;
import java.util.Collection;

import io.shmilyhe.convert.tools.DEBUG;
import io.shmilyhe.convert.tools.ExpEnv;

/**
 * 遍历
 */
public class EachConvertor extends ComplexConvertor {

    Getter get ;
    Setter set ;
    public EachConvertor(String exp){
        DEBUG.debug("each ===",exp);
        String path=removeRootString(exp);
        get= new Getter(path);
        get.setVar(!exp.startsWith("."));
        set= new Setter(path);
        set.setVar(!exp.startsWith("."));
    }

    @Override
    public Object convert(Object root,ExpEnv env) {
        DEBUG.debug("========start:",this.getName(),"========"); 
        ExpEnv p=env;
        Object setroot=root;
        if(set.isVar())setroot=env;
        DEBUG.debug(setroot.getClass());
        env= new ExpEnv(p);
         Object data =get.get(root,env);
         DEBUG.debug("get:",data," is Var :",get.isVar());
         if(data==null)return root;
        if (data instanceof Collection){
            Collection els =  (Collection)data;
            ArrayList ndata= new ArrayList<>();
            for(Object el:els){
                Object r=each(el,env);
                if(r!=null)ndata.add(r);
            }
            set.set(setroot, ndata);
        }else if (isArray(data)){
         Object[] els =  (Object[])data;
         for(int i=0;i<els.length;i++){
            els[i]=each(els[i],env);
         }
         set.set(setroot, els);
        }
        DEBUG.debug("========end:",this.getName(),"========"); 
         return root;
    }

    protected Object each(Object o,ExpEnv env){
        try{
            return super.convert(o,env);
        }catch(Exception e){
            e.printStackTrace();
        }
        return o;
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

    private static boolean isArray(Object o){
		if(o==null)return false;
		return o.getClass().isArray();
	}





    
    
}
