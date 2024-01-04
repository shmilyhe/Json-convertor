package io.shmilyhe.convert.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.api.IFunctionRegistry;
import io.shmilyhe.convert.ast.token.CalleeToken;
import io.shmilyhe.convert.ast.token.ITokenizer;
import io.shmilyhe.convert.tools.DEBUG;
import io.shmilyhe.convert.tools.ExpEnv;

/**
 * 遍历
 */
public class FunctionConvertor extends ComplexConvertor {


    List<String> argumentNames = new ArrayList<>();


    public ExpEnv callEnv(List args,ExpEnv env,IFunctionRegistry reg){
        ExpEnv e= new ExpEnv(env);
        int index=0;
        //e.setFunctionRegistry(reg);
        for(Object v :args){
            try{
                String n=argumentNames.get(index++);
                //System.out.println(n+"="+v);
                e.put(n, v);
            }catch(Exception ex){

            }
        }
        return e;
    }


    public CalleeToken getCallee() {
        return callee;
    }



    public void setCallee(CalleeToken callee) {
        //System.out.println("set callee:"+callee.getRaw());
        this.callee = callee;
        if(callee!=null){
            argumentNames.clear();
            if(callee.getArguments()!=null){
                for(ITokenizer tkz:callee.getArguments()){
                    tkz.reset();
                    if(tkz.hasNext()){
                        String n=tkz.next().getRaw();
                        argumentNames.add(n);
                    }
                    tkz.reset();
                }
            }
        }
    }



    CalleeToken callee;

    IConvertor returns;

    

    public IConvertor getReturns() {
        return returns;
    }



    public void setReturns(IConvertor returns) {
        this.returns = returns;
    }



    @Override
    public Object convert(Object root,ExpEnv env) {
        //System.out.println("expsize:"+clist.size());
        Object o=root;
        for(IConvertor c:clist){
            if(c==null)continue;
            o=c.convert(o,env);
            if(env.isExited()){
                break;
            }
        }
        if(returns!=null)return returns.convert(root, env);
        return null;
    }

}
