package io.shmilyhe.convert.ast.expression;

public class Literal extends Expression {
    public Literal(String name){
        this.setRaw(name);
    }

    @Override
    public String getType() {
        return TYPE_LIT;
    }

    protected Object value;
    
    protected String raw;

    public String getRaw() {
        return raw;
    }

    public Literal setRaw(String raw) {
        this.raw = raw;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Literal setValue(Object value) {
        this.value = value;
        return this;
    }
    
}
