package io.shmilyhe.convert.ast.expression;

public class Identifier extends Expression {

    public Identifier(){

    }

    public Identifier(String name){
        this.name=name;
    }

    @Override
    public String getType() {
        return TYPE_ID;
    }

    protected String name;

    public String getName() {
        return name;
    }

    public Identifier setName(String name) {
        this.name = name;
        return this;
    }

    
    
}
