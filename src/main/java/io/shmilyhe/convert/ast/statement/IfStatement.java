package io.shmilyhe.convert.ast.statement;

import io.shmilyhe.convert.ast.expression.Expression;

public class IfStatement extends Statement {
    @Override
    public String getType() {
        return TYPE_IF;
    }

    protected Expression test;

    protected Statement consequent;

    protected Statement alternate;

    public Expression getTest() {
        return test;
    }

    public IfStatement setTest(Expression test) {
        this.test = test;
        return this;
    }

    public Statement getConsequent() {
        return consequent;
    }

    public IfStatement setConsequent(Statement consequent) {
        this.consequent = consequent;
        return this;
    }

    public Statement getAlternate() {
        return alternate;
    }

    public IfStatement setAlternate(Statement alternate) {
        this.alternate = alternate;
        return this;
    }

    public void clearParent(){
        super.clearParent();
        if(alternate!=null)
        alternate.clearParent();
    }

    


}
