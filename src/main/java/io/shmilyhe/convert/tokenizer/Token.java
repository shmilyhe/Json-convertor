package io.shmilyhe.convert.tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Token {
    public final static int TYPE_VAR=0;
    public final static int TYPE_OPERATE=1;
    public final static int TYPE_FUNCTION=2;
    public final static int TYPE_COMMA=3;
    Block block;
   
    private int line;
    private int column;
    private int type;
    private String text;
    private List<Token> sub;

     public Block getBlock() {
        return block;
    }

    public Token setBlock(Block block) {
        this.block = block;
        return this;
    }
    public Token addSub(Token t){
        if(sub==null){
            sub=new ArrayList<>();
        }
        sub.add(t);
        return this;
    }

    public boolean isFunction(){
        return TYPE_FUNCTION==type;
    }

    public boolean isVar(){
        return TYPE_VAR==type;
    }

    public boolean isOperator(){
        return TYPE_OPERATE==type;
    }

    public String getText() {
        return text;
    }
    public Token setText(String text) {
        this.text = text;
        return this;
    }
    public int getLine() {
        return line;
    }
    public Token setLine(int line) {
        this.line = line;
        return this;
    }
    public int getColumn() {
        return column;
    }
    public Token setColumn(int column) {
        this.column = column;
        return this;
    }
    public int getType() {
        return type;
    }
    public Token setType(int type) {
        this.type = type;
        return this;
    }
    
    public String toString(){
        if(this.type==TYPE_FUNCTION)return this.getBlock().toString();
        return this.text;
    }

}
