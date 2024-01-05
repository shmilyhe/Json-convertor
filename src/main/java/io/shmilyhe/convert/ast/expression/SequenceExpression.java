package io.shmilyhe.convert.ast.expression;

import java.util.ArrayList;
import java.util.List;

public class SequenceExpression extends Expression {

    public SequenceExpression(){
        this.setType(TYPE_ARRAY);
    }
    
    protected List<Expression> expressions;

    public List<Expression> getExpressions() {
        return expressions;
    }

    public SequenceExpression setExpressions(List<Expression> expressions) {
        this.expressions = expressions;
        return this;
    } 
    
    public SequenceExpression addExpressions(Expression exp){
        if(expressions==null){
            expressions= new ArrayList<>();
        }
        expressions.add(exp);
        return this;
    }
}
