package io.shmilyhe.convert.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import io.shmilyhe.convert.api.IFunctionRegistry;
import io.shmilyhe.convert.callee.IFunction;

/**
 * 运行的上下文
 */
public class ExpEnv extends HashMap {
    private ExpEnv parent;
    private boolean exited=false;
    int level=1;

    private IFunctionRegistry functionRegistry;

    private FunctionChain fc = new FunctionChain();


    public IFunctionRegistry getFunctionRegistry() {
        return functionRegistry;
    }

    public ExpEnv setFunctionRegistry(IFunctionRegistry functionRegistry) {
        this.functionRegistry = functionRegistry;
        fc.addFunction(functionRegistry);
        return this;
    }

    public ExpEnv unsetFunctionRegistry(IFunctionRegistry functionRegistry){
         //fc.revomeFunction();
         return this;
    }

    public IFunction getFunction(String name){
        IFunction f=fc.geFunction(name);
        return f;
        //if(functionRegistry==null)return null;
        //return functionRegistry.getFunction(name);
    }

    public ExpEnv(ExpEnv p){
        parent=p;
        if(p!=null){
            this.level+=p.level;
        }
    }

    public ExpEnv getParent() {
        return parent;
    }

    public void setParent(ExpEnv parent) {
        this.parent = parent;
    }

    @Override
    public boolean containsKey(Object key) {
        if(this.parent!=null&&parent.containsKey(key)){
                return true;
        }
        return super.containsKey(key);
    }

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public Object get(Object key) {
        if("now".equals(key))return sdf.format(new Date());
        if("timestamp".equals(key))return System.currentTimeMillis();
        if("uuid".equals(key))return UUID.randomUUID().toString().replaceAll("-", "");
        Object o=null;
        if(this.parent!=null){
            o=parent.get(key);
        }
        if(o!=null)return o;
        //DEBUG.debug("evn ",key," ",o);
        return super.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        //DEBUG.debug("env put ",key," ",value);
        if(this.parent!=null&&parent.containsKey(key)){
            //DEBUG.debug("env containsKey:",key);
            parent.put(key, value);
        }
        return super.put(key, value);
    }

    @Override
    public Object remove(Object key) {
       if(this.parent!=null&&parent.containsKey(key)){
            parent.remove(key);
        }
        return super.remove(key);
    }


    /**
     * 设置全局
     * @param k KEY
     * @param v VALUE
     */
    public void global(Object k,Object v){
        if(parent!=null){
            parent.global(k,v);
        }else{
            super.put(k,v);
        } 
    }

    /**
     * 获取全局变量
     * @param k KEY
     * @return  VALUE
     */
    public Object global(Object k){
        if(parent!=null){
            return parent.global(k);
        }else{
            return super.get(k);
        } 
    }

    protected String namespace;

    /**
     * 获取命名空间
     * @return 命名空间
     */
    public String nameSpace(){
        if(parent!=null){
            return parent.nameSpace();
        }else{
            return this.namespace;
        } 
    }

    /**
     * 设定命名空间
     * @param ns 命名空间
     */
    public void nameSpace(String ns){
        if(parent!=null){
             parent.nameSpace(ns);
        }else{
             this.namespace=ns;
        } 
    }

    public void exit(){  
        if(parent!=null){
            parent.exit();
        }else{
            this.exited=true;
        }  
    }

    private int exitCode=0;
    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public void exit(String code){  
        if(parent!=null){
            parent.exit(code);
        }else{
            try{
                exitCode = Integer.valueOf(code);
            } catch(Exception e){
                exitCode=-1;
            }
            this.exited=true;
        }  
    }

    public boolean isExited(){
        if(parent!=null){
            return parent.isExited();
        }else{
            return this.exited;
        } 
    }

    
}

class FunctionChain{
    private IFunctionRegistry f;
    FunctionChain parent;
    public IFunction geFunction(String name){
        IFunction fun =null;
        if(f!=null){
            fun=f.getFunction(name);
            if(fun!=null)return fun;
        }
        if(parent==null){
            return null;
        }
        return parent.geFunction(name);
    }
    public void addFunction(IFunctionRegistry f){
        if(this.f==null){
            this.f=f;
        }else{
            FunctionChain p= new FunctionChain();
            p.f=this.f;
            p.parent=this.parent;
            this.parent=p;
            this.f=f;
        }
    }

    public void revomeFunction(){
        if(parent!=null){
            this.f=parent.f;
            this.parent=parent.parent;
        }
    }

    FunctionChain getParent(){
        return parent;
    }

}