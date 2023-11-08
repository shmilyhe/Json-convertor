package io.shmilyhe.convert.ast.token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.shmilyhe.convert.ast.expression.Identifier;
import io.shmilyhe.convert.tokenizer.StringTokenizer;

public class Tokenizer implements ITokenizer{
    
    StringTokenizer tks;
    public Tokenizer(StringTokenizer tks){
        this.tks=tks;
    }
    
    public boolean hasNext(){
        if(back!=null)return true;
        return this.tks.hasNext();
    }

    public Token next1(){
        StringBuilder sb = new StringBuilder();
        char c=tks.next();
        Token t =null;
        int start =tks.offset();
        int line =tks.line();
        switch (c) {
            case '#':
                 return new Token("#"+tks.toLineEnd())
                 .setType(Token.COMMONS)
                 .setStart(start)
                 .setEnd(tks.offset())
                 .setLine(line)
                 ;
            case '\n':
            case '\r':
                return new Token("\n")
                .setType(Token.NEWLINE)
                .setStart(start)
                .setEnd(tks.offset())
                .setLine(line);
            case ' ':
            case '\t':
                return new Token(c+tks.whitespace())
                .setType(Token.SPACE)
                .setStart(start)
                .setEnd(tks.offset())
                .setLine(line);
            case '"' :
                return new Token("\""+tks.tillNext(c))
                .setLine(line)
                .setStart(start)
                .setEnd(tks.offset())
                .setType(Token.LITERAL);
            case '\'':
                return new Token(c+tks.tillNext(c))
                .setType(Token.LITERAL)
                .setStart(start)
                .setEnd(tks.offset())
                .setLine(line);
                case '/':
                case '*':
                case '%':
                case '(':
                case ')':
                case ',':
                case ';':
                case '{':
                case '}':
                case '^':
                return new Token(String.valueOf(c))
                .setType(Token.SYMBOL)
                .setStart(start)
                .setEnd(tks.offset())
                .setLine(line);
                case '-':
                case '+':
                case '<':
                case '&':
                case '|':
                case '!':
                    if(tks.hasNext()){
                        char c1=tks.next();
                        if(c1==c){
                            //++,--,<<,&&,||
                            return new Token(new String(new char[]{c,c}))
                            .setType(Token.SYMBOL)
                            .setStart(start)
                            .setEnd(tks.offset())
                            .setLine(line);
                        }else if(c1=='='){
                            //+=,-=,<=
                            return new Token(new String(new char[]{c,'='}))
                            .setType(Token.SYMBOL)
                            .setStart(start)
                            .setEnd(tks.offset())
                            .setLine(line);
                        }else{
                            tks.back(c1);
                            return new Token(String.valueOf(c))
                            .setType(Token.SYMBOL)
                            .setStart(start)
                            .setEnd(tks.offset())
                            .setLine(line);
                        }

                    }else{
                       return new Token(String.valueOf(c))
                        .setType(Token.SYMBOL)
                        .setStart(start)
                        .setEnd(tks.offset())
                        .setLine(line);
                    }
                case '>':
                    if(tks.hasNext()){
                        char c1=tks.next();
                        if(c1==c){
                            //>>
                            if(tks.hasNext()){
                                char c2=tks.next();
                                if(c2==c){
                                    return new Token(">>>")
                                    .setType(Token.SYMBOL)
                                    .setStart(start)
                                    .setEnd(tks.offset())
                                    .setLine(line);
                                }else{
                                    tks.back(c2);
                                    return new Token(">>")
                                    .setType(Token.SYMBOL)
                                    .setStart(start)
                                    .setEnd(tks.offset())
                                    .setLine(line);
                                }
                            }else{
                                return new Token(new String(new char[]{c,c}))
                                .setType(Token.SYMBOL)
                                .setStart(start)
                                .setEnd(tks.offset())
                                .setLine(line);

                            }
                        }else if(c1=='='){
                            //+=,-=,<=
                            return new Token(new String(new char[]{c,'='}))
                            .setType(Token.SYMBOL)
                            .setStart(start)
                            .setEnd(tks.offset())
                            .setLine(line);
                        }else{
                            tks.back(c1);
                            return new Token(String.valueOf(c))
                            .setType(Token.SYMBOL)
                            .setStart(start)
                            .setEnd(tks.offset())
                            .setLine(line);
                        }

                    }else{
                       return new Token(String.valueOf(c))
                        .setType(Token.SYMBOL)
                        .setStart(start)
                        .setEnd(tks.offset())
                        .setLine(line);
                    }
                case '=':
                    if(tks.hasNext()){
                        char c1=tks.next();
                        if(c1==c){
                            return new Token("==")
                            .setType(Token.SYMBOL)
                            .setStart(start)
                            .setEnd(tks.offset())
                            .setLine(line);
                        }else{
                            tks.back(c1);
                            return new Token("=")
                            .setType(Token.SYMBOL)
                            .setStart(start)
                            .setEnd(tks.offset())
                            .setLine(line);
                        }
                    }else{
                        return new Token(String.valueOf(c))
                        .setType(Token.SYMBOL)
                        .setStart(start)
                        .setEnd(tks.offset())
                        .setLine(line);
                    }
            default:
                String str=c+tks.toSymbol();
                int type=Token.IDENTIFIER;
                if("true".equalsIgnoreCase(str)||"false".equalsIgnoreCase(str)){
                    type=Token.LITERAL;
                }else if(isNumber(str)) {
                    type=Token.LITERAL;
                }
                return new Token(str)
                        .setType(type)
                        .setStart(start)
                        .setEnd(tks.offset())
                        .setLine(line);
        }

    }
    Pattern number=Pattern.compile("[0-9.]+");

    private boolean isNumber(String str){
        if(str==null||str.length()==0)return false;
        Matcher m= number.matcher(str);
        return m.matches();
    }

    @Override
    public void back() {
        back=last;
    }

    Token back;

    Token last;
    @Override
    public Token next() {
        if(back!=null){
            last=back;
            back=null;
        }else{
            Token t=next1();
            last=t;
        }
        return last;
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }
}
