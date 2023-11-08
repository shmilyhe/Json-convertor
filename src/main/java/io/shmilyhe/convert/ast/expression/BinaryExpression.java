package io.shmilyhe.convert.ast.expression;

public class BinaryExpression extends Expression{
 
    @Override
    public String getType() {
        return TYPE_BIN;
    }

    protected Expression left;
    protected Expression right;
    protected String operater;

    public Expression getLeft() {
        return left;
    }
    public BinaryExpression setLeft(Expression left) {
        this.left = left;
        return this;
    }
    public Expression getRight() {
        return right;
    }
    public BinaryExpression setRight(Expression right) {
        this.right = right;
        return this;
    }
    public String getOperater() {
        return operater;
    }
    public BinaryExpression setOperater(String operater) {
        this.operater = operater;
        return this;
    }

    public int getPriority(){
        return getPriority(operater);
    }

    private static int getPriority(String s){
        if (s == null)
            return -10;
        switch (s) {
            case "(":
                return -9;
            case "+":
                ;
            case "-":
                return 2;
            case "*":
                ;
            case "/":
                return 3;
            case "%":
                return 3;
            case ">>":
            case "<<":
            case ">>>":
                return 0;
            case ">":
            case "<":
            case ">=":
            case "<=":
                return -1;
            case "==":
            case "!=":
                return -2;
            case "&":
                return -3;
            case "^":
                return -4;
            case "|":
                return -5;
            case "&&":
                return -6;
            case "||":
                return -7;
            default:
                return -8;
        }
    }
    


    
}
