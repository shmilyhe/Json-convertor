package io.shmilyhe.convert.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.impl.ConstantGetter;
import io.shmilyhe.convert.impl.ExpGeter;
import io.shmilyhe.convert.impl.Getter;
import io.shmilyhe.convert.impl.OperatorType;
import io.shmilyhe.convert.impl.SelfGetter;

/**
 * 算式编译器
 * 本版本还不支持函数
 */
public class ExpCalculate {

    /**
     * 优先级
     * @param s
     * @return
     */
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

    public static String[] tokenizer(String exp) {
        ArrayList<String> tokens = new ArrayList<String>();
        int offset = 0;
        StringBuilder part = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);
            switch (ch) {
                case ' ':

                    break;
                case '-':
                case '+':
                    if (part.length() > 0 && offset > 0) {
                        tokens.add(part.toString());
                        tokens.add(String.valueOf(exp.charAt(i)));
                        part = new StringBuilder();
                    } else {
                        part.append(ch);
                    }
                    break;
                case '/':
                case '*':
                case '(':
                case ')':
                case '%':
                    if (part.length() > 0)
                        tokens.add(part.toString());
                    tokens.add(String.valueOf(exp.charAt(i)));
                    part = new StringBuilder();
                    offset = 0;
                    break;
                case '>':
                    if (part.length() > 0)
                        tokens.add(part.toString());
                    if(exp.length()>i+2 
                    &&exp.charAt(i+1)=='>'
                    &&exp.charAt(i+2)=='>'){
                        tokens.add(">>>");
                        i+=2; 
                    }else if(exp.length()>i+1 
                    &&exp.charAt(i+1)=='>'){
                        tokens.add(">>");
                        i+=1; 
                    }else if(exp.length()>i+1 
                    &&exp.charAt(i+1)=='='){
                        tokens.add(">=");
                        i+=1; 
                    }else{
                        tokens.add(">");
                    }
                    part = new StringBuilder();
                    offset = 0;
                    break;
                case '<':
                if (part.length() > 0)
                        tokens.add(part.toString());
                     if(exp.length()>i+1 
                    &&exp.charAt(i+1)=='<'){
                        tokens.add("<<");
                        i+=1; 
                    }else if(exp.length()>i+1 
                    &&exp.charAt(i+1)=='='){
                        tokens.add("<=");
                        i+=1; 
                    }else{
                        tokens.add("<");
                    }
                    part = new StringBuilder();
                    offset = 0;
                    break;
                case '!':
                if (part.length() > 0)
                        tokens.add(part.toString());
                     if(exp.length()>i+1 
                    &&exp.charAt(i+1)=='='){
                        tokens.add("!=");
                        i+=1;  
                    }else{
                        tokens.add("!");
                    }
                    part = new StringBuilder();
                    offset = 0;
                    break;
                case '=':
                case '|':
                case '&':
                    if (part.length() > 0)
                        tokens.add(part.toString());
                    if(exp.length()>i+2 
                    &&exp.charAt(i+1)==ch){
                        tokens.add(new String(new char[]{ch,ch}));
                        i+=1; 
                    }else{
                        tokens.add(String.valueOf(ch));
                    }
                    part = new StringBuilder();
                    offset = 0;
                    break;
                default:
                    part.append(ch);
                    break;
            }
            offset += 1;
        }
        //if(offset>0)tokens.add(part.toString());
        if(part.length()>0){
            tokens.add(part.toString());
            //System.out.println("end:"+part+"=");
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    public static IGet getExpression(String expr) throws RuntimeException {
        return getExpression(expr,null);
    }



    static String operate="+-*/%<<=>>>==^||&&)()!=";

    public static IGet getExpression(String expr,Integer line) throws RuntimeException {
        DEBUG.debug("parse exp:",expr);
        /* 数字栈 */
        Stack<IGet> number = new Stack<IGet>();
        /* 符号栈 */
        Stack<String> operator = new Stack<String>();
        operator.push(null);// 在栈顶压人一个null，配合它的优先级，目的是减少下面程序的判断
       String[] tks = tokenizer(expr);
        /*for(String k:tks){
            System.out.print(k+"_");
        }*/
        //System.out.println();
        for (String temp : tks) {
            // System.out.println("split:"+temp);

            //if (temp.matches("[+\\-*/()%=><\\|\\!&]")) {// 遇到符号
            //    System.out.println(temp+":"+(operate.indexOf(temp)));
            if(operate.indexOf(temp)>-1){

                if (temp.equals("(")) {// 遇到左括号，直接入符号栈
                    operator.push(temp);
                    // System.out.println("符号栈更新："+operator);
                } else if (temp.equals(")")) {// 遇到右括号，"符号栈弹栈取栈顶符号b，数字栈弹栈取栈顶数字a1，数字栈弹栈取栈顶数字a2，计算a2 b a1
                                              // ,将结果压入数字栈"，重复引号步骤至取栈顶为左括号，将左括号弹出
                    String b = null;
                    while (!(b = operator.pop()).equals("(")) {
                        // System.out.println("符号栈更新："+operator);
                        IGet a1 = number.pop();
                        IGet a2 = number.pop();
                        // System.out.println("数字栈更新："+number);
                        // System.out.println("计算"+a2+b+a1);
                        number.push(new ExpGeter(a2, a1, OperatorType.find(b)).setExpression(temp).setLine(line));
                        // System.out.println("数字栈更新："+number);
                    }
                    // System.out.println("符号栈更新："+operator);
                } else {// 遇到运算符，满足该运算符的优先级大于栈顶元素的优先级压栈；否则计算后压栈
                    while (getPriority(temp) <= getPriority(operator.peek())) {
                        IGet a1 = number.pop();
                        //System.out.println("excp:"+temp);
                        IGet a2 = number.pop();
                        String b = operator.pop();
                        // System.out.println("符号栈更新："+operator);
                        // System.out.println("数字栈更新："+number);
                        // System.out.println("计算"+a2+b+a1);
                        number.push(new ExpGeter(a2, a1, OperatorType.find(b)).setExpression(temp).setLine(line));
                        // System.out.println("数字栈更新："+number);
                    }
                    operator.push(temp);
                    // System.out.println("符号栈更新："+operator);
                }
            } else {// 遇到数字，直接压入数字栈
                IGet get = null;
                if (temp.equals(".")) {
                    get = new SelfGetter();
                }else if (temp.startsWith(".")) {
                    get = new Getter(temp);
                }else if (temp.startsWith("-")) {
                    Getter g = new Getter(temp.substring(1)).setVar(!(temp.charAt(1)=='.'));
                    get = new ExpGeter(new ConstantGetter("-1"),g, OperatorType.MULT);
                }else {
                    if(temp.startsWith("\"")
                    ||temp.startsWith("\'")
                    ||temp.equalsIgnoreCase("true")
                    ||temp.equalsIgnoreCase("false")
                    ||temp.equalsIgnoreCase("null")
                    ||temp.matches("[+\\-]?\\d+\\.?\\d*")
                    ){
                        get = new ConstantGetter(temp);
                    }else{
                        get = new Getter(temp).setVar(true);
                    }
                    
                }
                number.push(get);
                //System.out.println("数字栈更新："+temp);
            }
        }

        while (operator.peek() != null) {// 遍历结束后，符号栈数字栈依次弹栈计算，并将结果压入数字栈
            String b = operator.pop();
            IGet a1 = number.pop();
            IGet a2 = null;
            if(number.size()>0){
                a2=number.pop();
            }else{
                number.push(new ExpGeter(new ConstantGetter(b+"1"), a1, OperatorType.MULT));
                break;
            }
            
            
            // System.out.println("符号栈更新："+operator);
            // System.out.println("数字栈更新："+number);
            // System.out.println("计算"+a2+b+a1);
            number.push(new ExpGeter(a2, a1, OperatorType.find(b)));
            // System.out.println("数字栈更新："+number);
        }
        IGet get =number.pop();
        if(get instanceof ExpGeter){
            ((ExpGeter)get).setExpression(expr).setLine(line);
        }
        return get;
    }

    public static void main(String[] args) throws Exception {
        /*{
            String str = "-3.5*( 4.5 -( 4 + (-1       -1/2)))";
            IGet exp =getExpression(str);
            System.out.println("计算:"+exp);
            System.out.println(exp.get(new HashMap<>()));
            System.out.println();
        }
        {
            String str = "-3.7  *  (2   +   -1   -3)";
           IGet exp =getExpression(str);
           System.out.println("计算:"+exp);
            System.out.println(exp.get(new HashMap<>()));
        }

        {
            HashMap evn = new HashMap();
            evn.put("x", 3.7);
           String str = ".x  *  (2   +   -1   -3)";
            IGet exp =getExpression(str);
            System.out.println("计算:"+exp);
            System.out.println(exp.get(evn));
            evn.put("x", -3.7);
            System.out.println(exp.get(evn));
            evn.put("x", -7);
            System.out.println(exp.get(evn));
            evn.put("x", 9);
            System.out.println(exp.get(evn));
        }

        {
            System.out.println("================");
            HashMap evn = new HashMap();
            evn.put("x", 5);
           String str = ".x*5 ";
            IGet exp =getExpression(str);
            System.out.println("计算:"+exp);
            System.out.println(exp.get(evn));
            evn.put("x", 7);
            System.out.println(exp.get(evn));
     
        }
        {
            System.out.println("================");
            HashMap evn = new HashMap();
            evn.put("x", 5);
           String str = ".x%5 ";
            IGet exp =getExpression(str);
            System.out.println("计算:"+exp);
            System.out.println(exp.get(evn));
            evn.put("x", 7);
            System.out.println(exp.get(evn));
     
        }
        {
            System.out.println("================");
            HashMap evn = new HashMap();
            evn.put("x", 5);
           String str = "-(.x%5)";
            IGet exp =getExpression(str);
            System.out.println("计算:"+exp);
            System.out.println(exp.get(evn));
            evn.put("x", 7);
            System.out.println(exp.get(evn));
     
        }
        {
            System.out.println("================");
            HashMap evn = new HashMap();
            evn.put("x", 5);
           String str = "-.x%5*-.x";
            IGet exp =getExpression(str);
            System.out.println("计算:"+exp);
            System.out.println(exp.get(evn));
            evn.put("x", 7);
            System.out.println(exp.get(evn));
     
        }
         {
            System.out.println("================");
            HashMap evn = new HashMap();
            evn.put("x", 5);
           String str = ".x";
            IGet exp =getExpression(str);
            System.out.println("计算:"+exp);
            System.out.println(exp.get(evn));
            evn.put("x", 7);
            System.out.println(exp.get(evn));
        }
        {
            System.out.println("================");
            HashMap evn = new HashMap();
            evn.put("x", 5);
           String str = "-.x";
            IGet exp =getExpression(str);
            System.out.println("计算:"+exp);
            System.out.println(exp.get(evn));
            evn.put("x", 7);
            System.out.println(exp.get(evn));
        }
*/
        {
            System.out.println("================");
            HashMap evn = new HashMap();
            evn.put("x", 5);
           String str = ".x*(1-20/2 )/2";
            IGet exp =getExpression(str);
            System.out.println("计算:"+exp);
            System.out.println(exp.get(evn,null));
  
        }

    }

}
