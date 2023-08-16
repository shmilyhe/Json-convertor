package io.shmilyhe.convert.impl;

import java.util.ArrayList;
import java.util.List;

import io.shmilyhe.convert.api.IConvertor;

/**
 * 分支计算
 */
public class IfConvertor extends ComplexConvertor{


    protected List<IConvertor> elseList = new ArrayList<IConvertor>();

    String exp;

    public IfConvertor(String exp){
        this.exp=exp;
    }
    @Override
    public Object convert(Object root) {
        if(calCondition(root)){
            return super.convert(root);
        }else{
            if(elseList==null||elseList.size()==0)
            return root;
            return convertElse(root);
        }
        
    }

    
    public Object convertElse(Object root) {
        Object o=root;
        for(IConvertor c:clist){
            if(c==null)continue;
            o=c.convert(o);
        }
        return o;
    }

    public void addElseConvertor(IConvertor c){
        parent(c);
        elseList.add(c);
    }



    /**
     * 计算条件是否成立
     * @param root
     * @return
     */
    protected boolean calCondition(Object root){
        //TODO 计算条件是否成立
        return false;
    }

    

    
}
