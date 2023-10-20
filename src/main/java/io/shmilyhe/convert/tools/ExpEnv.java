package io.shmilyhe.convert.tools;

import java.util.HashMap;

/**
 * 运行的上下文
 */
public class ExpEnv extends HashMap {
    private ExpEnv parent;
    private boolean exited=false;
    int level=1;

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

    @Override
    public Object get(Object key) {
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

    public boolean isExited(){
        if(parent!=null){
            return parent.isExited();
        }else{
            return this.exited;
        } 
    }

    
}
