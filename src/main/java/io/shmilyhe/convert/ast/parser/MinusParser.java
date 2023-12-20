package io.shmilyhe.convert.ast.parser;

import io.shmilyhe.convert.ast.token.CacheTokenizer;
import io.shmilyhe.convert.ast.token.ITokenizer;
import io.shmilyhe.convert.ast.token.Token;

/**
 * 处理减号，当表达式之前出现“-”时合成一个TOKEN标记为取反
 */
public class MinusParser {
    
    public static ITokenizer parseMinus(ITokenizer tks){
        CacheTokenizer ctks= new CacheTokenizer();
        Token last =null;
        for(;tks.hasNext();){
            Token t =tks.next();
            if(t.isCommons()||t.isSpace())continue;
            if(t.isSymbol()&&("-".equals(t.getRaw())||"!".equals(t.getRaw()))){
                Token t2=tks.next();
                if(t2==null){
                    ctks.add(t);
                    break;
                }
                if(t2.isSpace())t2=tks.next();
                if(t2==null){
                    ctks.add(t);
                    break;
                }
                if(last==null||
                (last.isSymbol()&&("=".equals(last.getRaw())
                                    ||"==".equals(last.getRaw())
                                    ||"(".equals(t.getRaw())
                )
                )){
                    ctks.add(t2.minus(true));
                }else{
                    ctks.add(t);
                    ctks.add(t2);
                }

            }else{
                ctks.add(t);
            }
            last=t;
        }
        return ctks;
    }


    
}
