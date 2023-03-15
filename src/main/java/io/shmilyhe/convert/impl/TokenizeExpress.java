package io.shmilyhe.convert.impl;

import java.util.StringTokenizer;

import io.shmilyhe.convert.api.ExpPartVo;

public class TokenizeExpress {
    
    public static ExpPartVo tokenize(String exp){
        StringTokenizer st=new StringTokenizer(exp,".");
        ExpPartVo vo =null;
        while(st.hasMoreTokens()) { 
            //System.out.println(st.nextToken());
            ExpPartVo v= handle(st.nextToken());
            if(vo==null){
                vo=v;
            }else{
                vo.append(v);
            }
        }
        return vo;
    }

    private static ExpPartVo handle(String str) {
        if(str.indexOf(']')>-1){
            return handleArray(str);
        }else{
            return handleObject(str);
        }
    }

    private static ExpPartVo handleArray(String str){
        String []ps =str.replaceAll(" ", "")
        .split("\\]\\[|[\\[\\]]");
        ExpPartVo vo=null;
        for(String s:ps){
            if(vo==null){
                vo=new ExpPartVo(s,2);
            }else{
                vo.append(new ExpPartVo(Integer.parseInt(s)));
            }
        }
        return vo;
    }

    private static ExpPartVo handleObject(String srt){
        return  new ExpPartVo(srt);
    }

    public static void main(String[] args){
        ExpPartVo vo = tokenize("aa.aasd [0]  [0]       .aaa");
        for(;vo!=null;vo=vo.getNext()){
            System.out.println(vo.getType()+"|"+vo.getKey()+"|"+vo.getIndex());
        }
    }
}
