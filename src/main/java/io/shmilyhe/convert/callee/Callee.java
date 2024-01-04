package io.shmilyhe.convert.callee;

import java.util.ArrayList;
import java.util.List;

import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.system.SystemFunction;
import io.shmilyhe.convert.tools.ExpEnv;

public class Callee implements IGet{
    FunctionTable talble = new FunctionTable();
    IFunction fun;
    List<IGet> args;
    boolean minus;
    public boolean isMinus() {
        return minus;
    }

    public Callee setMinus(boolean minus) {
        this.minus = minus;
        return this;
    }
    private String name;
    public Callee(String name,List<IGet> args){
        this.args=args;
        this.name=name;
        fun=talble.getFunction(name);
    }

    @Override
    public Object get(Object data, ExpEnv env) {
        List a =getArgs(args,data,env);
        IFunction f=env.getFunction(name);
        //System.out.println("Call function:"+f);
        if(f==null)f=fun;
        if(f==null)return null;
        Object res =f.call(a,env);
        if(isMinus()) return SystemFunction.revert(res);
        return res;
    }

    private List getArgs(List<IGet> args,Object data, ExpEnv evn){
        List alist = new ArrayList<>();
        if(args!=null)for(IGet g:args){
            alist.add(g.get(data, evn));
        }
        return alist;
    }
    
}
