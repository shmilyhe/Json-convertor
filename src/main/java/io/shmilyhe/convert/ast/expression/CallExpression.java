package io.shmilyhe.convert.ast.expression;

import java.util.List;

public class CallExpression extends Expression{

    @Override
    public String getType() {
        return TYPE_CALL;
    }
    
    protected Expression callee;

    protected List<Expression> arguments;

    public Expression getCallee() {
        return callee;
    }

    public CallExpression setCallee(Expression callee) {
        this.callee = callee;
        return this;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public CallExpression setArguments(List<Expression> arguments) {
        this.arguments = arguments;
        return this;
    }

    


}
