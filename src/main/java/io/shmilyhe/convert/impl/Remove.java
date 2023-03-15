package io.shmilyhe.convert.impl;

import io.shmilyhe.convert.api.ExpPartVo;
import io.shmilyhe.convert.api.IDataAccess;
import io.shmilyhe.convert.api.IRemove;

public class Remove  implements IRemove{
    IDataAccess da;
    public Remove(String exp){
        ExpPartVo vo =  TokenizeExpress.tokenize(exp);
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
    public void remove(Object o) {
        Object b=o;
        IDataAccess flag =da;       
        for(;flag!=null;flag=flag.next()){
            if(flag.next()==null){
                flag.remove(b);
                return ;
            }
            b =flag.get(b);
            
            if(b==null){
                System.out.println("out:"+flag);
                return ;
            }  
        }
        return ;
    }
    
}
