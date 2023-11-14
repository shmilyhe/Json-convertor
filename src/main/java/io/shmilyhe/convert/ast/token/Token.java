package io.shmilyhe.convert.ast.token;

public class Token {

    /**
     * Token 类型
     */
    public final static int SYMBOL=0;
    public final static int IDENTIFIER=1;
    public final static int LITERAL=2;
    public final static int NEWLINE=3;
    public final static int COMMONS=4;
    public final static int SPACE=5;
    public final static int CALLEE=6;
    public final static int BRACKET=7;

    /**
     * 值类型
     */
    public final static int V_NUMBER=1;
    public final static int V_STRING=0;
    public final static int V_BOOLEAN=2;
    public final static int V_NULL=3;

    /**
     * 原始值
     */
    protected String raw;
    protected int type;
    protected int start;
    protected int end;
    protected int line;


    /**
     * 值类型
     */
    protected int valueType;
    


    public int getValueType() {
        return valueType;
    }

    public Token setValueType(int valueType) {
        this.valueType = valueType;
        return this;
    }


    protected boolean minus;

    public Token minus(boolean tf){
        minus=tf;
        return this;
    }

    public boolean minus(){
        return minus;
    }

    public boolean isLiteral(){
        return this.getType()==LITERAL;
    }

    public boolean isCommons(){
        return this.getType()==COMMONS;
    }

    public boolean isNewline(){
        return this.getType()==NEWLINE;
    }

    public boolean isSymbol(){
        return this.getType()==SYMBOL;
    }
    public boolean isIdentifier(){
        return this.getType()==IDENTIFIER;
    }

    public boolean isSpace(){
        return this.getType()==SPACE;
    }

    public boolean isBracket(){
        return this.getType()==BRACKET;
    }
    
    public boolean isCallee(){
        return this.getType()==CALLEE;
    }

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
