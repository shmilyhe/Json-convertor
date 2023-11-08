package io.shmilyhe.convert.ast.token;

import java.util.ArrayList;
import java.util.List;

public class CalleeToken extends Token {

    protected ITokenizer tokens;
    protected  List<ITokenizer> arguments;
    

    public ITokenizer getTokens() {
        return tokens;
    }


    public CalleeToken setTokens(ITokenizer tokens) {
        this.tokens = tokens;
        return this;
    }


    public List<ITokenizer> getArguments() {
        return arguments;
    }


    public CalleeToken setArguments(List<ITokenizer> arguments) {
        this.arguments = arguments;
        return this;
    }

    public CalleeToken addArguments(ITokenizer arg) {
        if(arguments==null)arguments= new ArrayList<>();
        arguments.add(arg);
        return this;
    }



    public CalleeToken(String value) {
        super(value);
        this.type=Token.CALLEE;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("call:").append(raw).append("(");
        for(ITokenizer tks:arguments){
            if(tks!=null)while(tks.hasNext()){
                Token t=tks.next();
                sb.append(t);
            }
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    
}
