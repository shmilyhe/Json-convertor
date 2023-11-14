package io.shmilyhe.convert.ast.parser;

import io.shmilyhe.convert.ast.token.CacheTokenizer;
import io.shmilyhe.convert.ast.token.CalleeToken;
import io.shmilyhe.convert.ast.token.ITokenizer;
import io.shmilyhe.convert.ast.token.Token;

/**
 * 调用转化为TOKEN
 */
public class CalleeParser {
    
    public static ITokenizer parseCallee(ITokenizer tks){
        CacheTokenizer ctks= new CacheTokenizer();
        Token last =null;
        for(;tks.hasNext();){
            Token t =tks.next();
            if("(".equals(t.getRaw())&&last!=null&&last.getType()==Token.IDENTIFIER){
                CalleeToken ct1= getCallee(last.getRaw(),tks);
                ctks.removeTail();
                ctks.add(ct1);
            }else {
                ctks.add(t);
            }
            if(t.getType()!=Token.SPACE)
            last=t;
        }
        return ctks;
    }

    static CalleeToken getCallee(String name,ITokenizer tks){
        CalleeToken  ctk= new CalleeToken(name);
        CacheTokenizer arg= new CacheTokenizer();
        ctk.addArguments(arg);
        Token last =null;
        int i=0;
        while(tks.hasNext()){
            Token t =tks.next();
            if("(".equals(t.getRaw())){
                if(last!=null&&last.getType()==Token.IDENTIFIER){
                    CalleeToken ct1= getCallee(last.getRaw(),tks);
                    arg.removeTail();
                    arg.add(ct1);
                }else{
                    i++;
                    arg.add(t);
                }
            }else if(")".equals(t.getRaw())){
                if(i>0){
                    arg.add(t);
                    i--;
                }else{
                    if(last!=null)ctk.setEnd(last.getEnd());
                    return ctk;
                }   
            }else if(",".equals(t.getRaw())){
                arg= new CacheTokenizer();
                ctk.addArguments(arg);
            }else{
                arg.add(t);
            }
            if(t.getType()!=Token.SPACE)
            last=t;
        }
        if(last!=null){
            ctk.setEnd(last.getEnd());
        }
        return ctk;
    }
}
