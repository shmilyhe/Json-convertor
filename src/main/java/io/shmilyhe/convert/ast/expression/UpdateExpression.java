package io.shmilyhe.convert.ast.expression;

public class UpdateExpression extends Expression{

    @Override
    public String getType() {
        return TYPE_UPDATE;
    }

    protected Expression argument;
    protected boolean prefix=false;
    protected String operater;

    public Expression getArgument() {
        return argument;
    }
    public UpdateExpression setArgument(Expression argument) {
        this.argument = argument;
        return this;
    }
    public boolean isPrefix() {
        return prefix;
    }
    public UpdateExpression setPrefix(boolean prefix) {
        this.prefix = prefix;
        return this;
    }
    public String getOperater() {
        return operater;
    }
    public UpdateExpression setOperater(String operater) {
        this.operater = operater;
        return this;
    }
    

}
