package io.shmilyhe.convert.ast.statement;

import java.util.List;

import io.shmilyhe.convert.ast.expression.Identifier;
import io.shmilyhe.convert.ast.token.CalleeToken;

public class FunctionStatement extends Statement {

    @Override
    public String getType() {
        return TYPE_FUN;
    }

    public boolean isFuntion(){
        return true;
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

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    private CalleeToken call;

    public CalleeToken getCall() {
        return call;
    }
    public void setCall(CalleeToken call) {
        this.call = call;
    }


    

}
