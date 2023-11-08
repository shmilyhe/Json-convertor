package io.shmilyhe.convert.ast.statement;

import io.shmilyhe.convert.ast.expression.Expression;

public class ReturnStatement extends Statement {

    protected Expression argument;

    public Expression getArgument() {
        return argument;
    }

    public ReturnStatement setArgument(Expression argument) {
        this.argument = argument;
        return this;
    }


    
}
