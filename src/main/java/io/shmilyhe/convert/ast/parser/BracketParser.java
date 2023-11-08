package io.shmilyhe.convert.ast.parser;

import io.shmilyhe.convert.ast.token.BracketToken;
import io.shmilyhe.convert.ast.token.CacheTokenizer;
import io.shmilyhe.convert.ast.token.ITokenizer;
import io.shmilyhe.convert.ast.token.Token;

public class BracketParser {
    
    public static ITokenizer parsebracket(ITokenizer tks){
        //System.out.println("===========tks===========");
        //print(tks);
        //System.out.println("===========tks===========");
        CacheTokenizer ctks= new CacheTokenizer();
        for(;tks.hasNext();){
            Token t =tks.next();
            if(t.getType()==Token.COMMONS||t.getType()==Token.SPACE)continue;
            //System.out.println(t.getRaw());
            if(t.getType()==Token.SYMBOL&&"(".equals(t.getRaw())){
                BracketToken ct1= getBracket(tks);
                ctks.add(ct1);
            }else{
                ctks.add(t);
            }
        }
        //System.out.println("===========ctks==========");
        //print(ctks);
        //System.out.println("===========ctks==========");
        return ctks;
    }

    private static void print(ITokenizer tks){
        StringBuilder b = new StringBuilder();
        while(tks.hasNext()){
            Token t = tks.next();
            b.append("_").append(t);
        }
        tks.reset();
        System.out.println(b);
    }

    static BracketToken getBracket(ITokenizer tks){
        BracketToken  ctk= new BracketToken();
        CacheTokenizer arg= new CacheTokenizer();
        ctk.setTokens(arg);
        Token last =null;
        while(tks.hasNext()){
            Token t =tks.next();
            if(t.getType()==Token.COMMONS||t.getType()==Token.SPACE)continue;
            if(t.getType()==Token.SYMBOL&&"(".equals(t.getRaw())){
                //System.out.println("0000000"+t.getClass().getSimpleName());
                BracketToken ct1= getBracket(tks);
                arg.add(ct1);
            }else if(t.getType()==Token.SYMBOL&&")".equals(t.getRaw())){
                //System.out.println("0000000"+t.getClass().getSimpleName());
                ctk.setEnd(last.getEnd());
                return ctk; 
            }else{
                arg.add(t);
            }
            last=t;
        }
        if(last!=null){
            ctk.setEnd(last.getEnd());
        }
        return ctk;
    }
}
