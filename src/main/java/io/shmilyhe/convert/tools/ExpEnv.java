package io.shmilyhe.convert.tools;

import java.util.HashMap;

/**
 * 运行的上下文
 */
public class ExpEnv extends HashMap {
    private ExpEnv parent;

    public ExpEnv(ExpEnv p){
        parent=p;
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

    
}
