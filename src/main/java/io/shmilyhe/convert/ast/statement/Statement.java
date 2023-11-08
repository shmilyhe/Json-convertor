package io.shmilyhe.convert.ast.statement;

import java.util.ArrayList;
import java.util.List;

import io.shmilyhe.convert.ast.token.ITokenizer;

public class Statement {
    public static final String TYPE_IF    = "IfStatement";
    public static final String TYPE_FOR   = "ForStatement";
    public static final String TYPE_EXP   = "ExpressionStatement";
    public static final String TYPE_EACH  = "EachStatement";
    public static final String TYPE_BLOCK = "BlockStatement";
    public static final String TYPE_FUN   = "FunctionStatement";
    public static final String TYPE_RETURN= "ReturnStatement";
    public static final String TYPE_ROOT= "RootStatement";
    

    private ITokenizer tokens;

    public ITokenizer getTokens() {
        return tokens;
    }

    public Statement setTokens(ITokenizer tokens) {
        this.tokens = tokens;
        return this;
    }

    protected String type;
    protected int start;
    protected int end;
    protected int line;
    
    protected List<Statement> body;

    public Statement addBody(Statement b){
        if(body==null){
            body= new ArrayList<>();
        }
        body.add(b);
        return this;
    }

    public String getType() {
        return type;
    }
    public Statement setType(String type) {
        this.type = type;
        return this;
    }
    public int getStart() {
        return start;
    }
    public Statement setStart(int start) {
        this.start = start;
        return this;
    }
    public int getEnd() {
        return end;
    }
    public Statement setEnd(int end) {
        this.end = end;
        return this;
    }
    public List<Statement> getBody() {
        return body;
    }
    public Statement setBody(List<Statement> body) {
        this.body = body;
        return this;
    }

    public int getLine() {
        return line;
    }

    public Statement setLine(int line) {
        this.line = line;
        return this;
    }
}
