package io.shmilyhe.convert.ast.expression;


public class AssignmentExpression extends BinaryExpression {
    
    @Override
    public String getType() {
        return TYPE_ASSIGN;
    }
}
