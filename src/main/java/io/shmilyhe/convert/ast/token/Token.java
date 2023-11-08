package io.shmilyhe.convert.ast.token;

public class Token {
    public final static int SYMBOL=0;
    public final static int IDENTIFIER=1;
    public final static int LITERAL=2;
    public final static int NEWLINE=3;
    public final static int COMMONS=4;
    public final static int SPACE=5;
    public final static int CALLEE=6;
    public final static int BRACKET=7;
    protected String raw;
    protected int type;
    protected int start;
    protected int end;
    protected int line;

    public Token(){}

    public Token(String value){
        this.raw=value;
    }

    public int getType() {
        return type;
    }
    public Token setType(int type) {
        this.type = type;
        return this;
    }
    public int getStart() {
        return start;
    }
    public Token setStart(int start) {
        this.start = start;
        return this;
    }
    public int getEnd() {
        return end;
    }
    public Token setEnd(int end) {
        this.end = end;
        return this;
    }
    public int getLine() {
        return line;
    }
    public Token setLine(int line) {
        this.line = line;
        return this;
    }
    public String getRaw() {
        return raw;
    }
    public Token setRaw(String raw) {
        this.raw = raw;
        return this;
    }

    
    public String toString(){
        return this.getRaw();
    }

}
