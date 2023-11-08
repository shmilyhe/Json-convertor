package io.shmilyhe.convert.ast.statement;

import java.util.List;

import io.shmilyhe.convert.ast.expression.Identifier;

public class FunctionStatement extends Statement {
    @Override
    public String getType() {
        return TYPE_FUN;
    }

    protected Identifier id;
    protected List<Identifier> params;

    public Identifier getId() {
        return id;
    }
    public FunctionStatement setId(Identifier id) {
        this.id = id;
        return this;
    }
    public List<Identifier> getParams() {
        return params;
    }
    public FunctionStatement setParams(List<Identifier> params) {
        this.params = params;
        return this;
    }

    

}
