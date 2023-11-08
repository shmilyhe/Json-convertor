package io.shmilyhe.convert.tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.shmilyhe.convert.Json;

public class Block {
    private Block parent;
    private int type;
    public final static int TYPE_IF=0;
    public final static int TYPE_ELSE=1;
    public final static int TYPE_EACH=2;
    public final static int TYPE_EXP=3;
    public final static int TYPE_LINE=4;
    public final static int TYPE_FUN=5;
    public final static int TYPE_ROOT=6;

    private String name;

    private int line;


    List<Token> tokens=new ArrayList<>();
     Block sub ;
     List<List<Token>> params= new ArrayList<>();


      public String getName() {
        return name;
    }

    private int level=0;

     public int getLine() {
        return line;
    }
    public Block setLine(int line) {
        this.line = line;
        return this;
    }

    public int getLevel() {
        return level;
    }
    public Block setLevel(int level) {
        this.level = level;
        return this;
    }
    public Block setName(String name) {
        this.name = name;
        return this;
    }
     public Block addToken(Token t){
        if(t.getType()==Token.TYPE_COMMA){
            params.add(tokens);
            tokens= new ArrayList<>();
            return this;
        }
        tokens.add(t);
        return this;
     }

     public Block sub(Block b){
        b.setParent(this);
        sub=b;
        return this;
     }

    private Block next;
    public int getType() {
        return type;
    }
    public Block setType(int type) {
        this.type = type;
        return this;
    }
    public Block getNext() {
        return next;
    }
    public Block setNext(Block next) {
        next.setParent(this.getParent());
        this.next = next;
        return this;
    }
     public Block getParent() {
        return parent;
    }

    public void setParent(Block parent) {
        this.parent = parent;
    }

    protected void toString(Map t,List lines){
        t.put("name", name);
        t.put("line", line);
        t.put("tokens",tokens);
        if(sub!=null){
            Map sub = new HashMap<>(); 
            t.put("sub", sub);
            ArrayList l= new ArrayList<>();  
            sub.put("list", l);   
            this.sub.toString(sub,l);
        }
        if(next!=null){
            Map next = new HashMap<>(); 
            lines.add(next);       
            this.next.toString(next,lines);
        }
    }
    public String toString(){
        HashMap t = new HashMap();
        
        ArrayList lines = new ArrayList<>();
        t.put("list", lines);
        toString(t,lines);
        return Json.asJsonString(t);
    }
}
