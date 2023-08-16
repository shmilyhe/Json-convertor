package io.shmilyhe.convert.impl;

import io.shmilyhe.convert.api.IGet;

/**
 * 算式计算器
 * 
 */
public class ExpGeter implements IGet{
    static int TYPE_INT=0;
    static int TYPE_STRING=1;
    static int TYPE_FLOAT=2;
    static int TYPE_BOOLEAN=3;
    static int TYPE_DATE=4;
    static final char[] OPS = {'=', '>', '<', '+', '-', '*', '/', '%', '!', '&', '|'};

    //表达式
    private String expression;
   //参数 代码的行号
    private Integer line;

    //参数1
    private  IGet p1;
    //参数2
    private IGet p2;

    private char operator;
    public ExpGeter(IGet g1,IGet g2,char operator){
        p1=g1;
        p2=g2;
        this.operator=operator;
    }

    @Override
    public Object get(Object data) {
        Object param1=p1.get(data);
        Object param2=p2.get(data);
        //System.out.println("cal:"+param1+operator+param2);
        switch(operator){
            case '+':
            return add(param1,param2);
            case '-':
            return sub(param1,param2);
            case '/':
            return div(param1,param2);
            case '*':
            return mult(param1,param2);
            case '%':
            return mod(param1,param2);
        }
        return null;
    }

    public static Object add(Object num1, Object num2) {
        if(num1==null)num1="null";
        if(num2==null)num2="null";
        if(isString(num1)||isString(num1)||!isNumber(num1)||!isNumber(num2)){
            return new StringBuilder().append(num1).append(num2).toString();
        }
        Number n1=(Number)num1;
        Number n2=(Number)num2;
        if (isInteger(n1) && isInteger(n2)) {
            return n1.longValue() + n2.longValue();
        } else {
            return n1.doubleValue() + n2.doubleValue();
        }
    }


    public static Object div(Object num1, Object num2) {
        if(!isNumber(num1)||!isNumber(num2)){
            return "NaN";
        }
        Number n1=(Number)num1;
        Number n2=(Number)num2;
        if(n2.doubleValue()==0)return "NaN";
        return n1.doubleValue() / n2.doubleValue();
        
    }

    public static Object sub(Object num1, Object num2) {
        if(!isNumber(num1)||!isNumber(num2)){
            return "NaN";
        }
        Number n1=(Number)num1;
        Number n2=(Number)num2;
        if (isInteger(n1) && isInteger(n2)) {
            return n1.longValue() - n2.longValue();
        } else {
            return n1.doubleValue()-n2.doubleValue();
        }
    }

     public static Object mult(Object num1, Object num2) {
        if(!isNumber(num1)||!isNumber(num2)){
            return "NaN";
        }
        Number n1=(Number)num1;
        Number n2=(Number)num2;
        if (isInteger(n1) && isInteger(n2)) {
            return n1.longValue() * n2.longValue();
        } else {
            return n1.doubleValue()*n2.doubleValue();
        }
    }
    public static Object mod(Object num1, Object num2) {
         if(!isNumber(num1)||!isNumber(num2)){
            return "NaN";
        }
         Number n1=(Number)num1;
        Number n2=(Number)num2;
        if (isInteger(n1) && isInteger(n2)) {
            return n1.longValue()%n2.longValue();
        } else {
            return "NaN";
        }
    }




    private static boolean isInteger(Number num) {
        return num instanceof Integer || num instanceof Long || num instanceof Short || num instanceof Byte;
    }
     private static boolean isNumber(Object num) {
        if(num instanceof Number){
            return true;
        }
        return false;
     }
    private static boolean isString(Object o){
        if(o==null)return false;
        if(o instanceof String){
            return true;
        }
        return false;
    }

    public Integer getLine() {
        return line;
    }

    public ExpGeter setLine(Integer line) {
        this.line = line;
        return this;
    }
     public String getExpression() {
        return expression;
    }

    public ExpGeter setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    @Override
    public String toString() {
        if(line!=null) return "line:"+line+"\t"+expression;
        return expression;
    }

    
    
}

