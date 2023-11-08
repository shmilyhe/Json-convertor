package io.shmilyhe.convert.ast.statement;

import io.shmilyhe.convert.ast.expression.Expression;

public class EachStatement extends Statement {
    @Override
    public String getType() {
        return TYPE_EACH;
    }

    Expression target;

    public Expression getTarget() {
        return target;
    }

    public EachStatement setTarget(Expression target) {
        this.target = target;
        return this;
    }
}
