package io.shmilyhe.convert.ast.token;

/**
 * 括号TOKEN
 */
public class BracketToken extends Token {

    public BracketToken(){
        this.setType(Token.BRACKET);
    }

    protected ITokenizer tokens;

    public ITokenizer getTokens() {
        return tokens;
    }


    public BracketToken setTokens(ITokenizer tokens) {
        this.tokens = tokens;
        return this;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("bracket:").append("(");
        if(tokens!=null)while(tokens.hasNext()){
            Token t=tokens.next();
            sb.append("_").append(t);
        }
        tokens.reset();     
        sb.append(")");
        return sb.toString();
    }

    
}
