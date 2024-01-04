package io.shmilyhe.convert.ast.token;

import java.util.List;

public class FunctionToken extends Token {

    protected ITokenizer tokens;
    protected  List<ITokenizer> arguments;
    protected  List<ITokenizer> body;

    protected  List<ITokenizer> returns;
    
    public List<ITokenizer> getReturns() {
        return returns;
    }

    public void setReturns(List<ITokenizer> returns) {
        this.returns = returns;
    }

    public List<ITokenizer> getBody() {
        return body;
    }

    public void setBody(List<ITokenizer> body) {
        this.body = body;
    }

    public ITokenizer getTokens() {
        return tokens;
    }
    
}
