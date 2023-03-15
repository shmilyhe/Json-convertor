/**
 * 给JSON表达式赋值
 */
package io.shmilyhe.convert.impl;

import java.util.List;
import java.util.Map;

import io.shmilyhe.convert.api.ExpPartVo;
import io.shmilyhe.convert.api.IDataAccess;
import io.shmilyhe.convert.api.ISet;

/**
 * 
 */
public class Setter implements ISet{
    IDataAccess da;

    /**
     * 
     * @param ext 表达式
     */
    public Setter(String ext){
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
    public void set(Object o,Object v) {
        Object b=o;
        IDataAccess flag =da;
        IDataAccess last=da;
        Object lastO=b;
       
        for(;flag!=null;flag=flag.next()){
            if(flag.next()==null){
                //System.out.println("===i===="+v+"|"+flag+"|"+b);
                if(!flag.set(v, b)){
                    //System.out.println("======="+v);
                    b=flag.create();
                    last.set(b, lastO);
                    flag.set(v, b);
                }
              break;
            }
            Object d=flag.get(b);
            boolean isContainer =false;
            if(d instanceof Map){
                isContainer=true;
            }else if(d instanceof List){
                isContainer=true;
            }
            if(d==null||!isContainer){
                d=flag.create();
                if(!flag.set(d, b)){
                    b=flag.create();
                    last.set(d, lastO);
                }
            }
            b=d;
            last=flag;
            lastO=b;
            
        }
    }
    
}
