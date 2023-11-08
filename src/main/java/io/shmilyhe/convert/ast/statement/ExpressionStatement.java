package io.shmilyhe.convert.ast.statement;

import io.shmilyhe.convert.ast.expression.Expression;

public class ExpressionStatement extends Statement {
    @Override
    public String getType() {
        return TYPE_EXP;
    }

    protected Expression experssion;

    public Expression getExperssion() {
        return experssion;
    }

    public ExpressionStatement setExperssion(Expression experssion) {
        this.experssion = experssion;
        return this;
    }
}
