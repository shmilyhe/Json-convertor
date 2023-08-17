package io.shmilyhe.convert.tools;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpCalculate3 {


    private static double doubleCal(double a1, double a2, char operator) throws Exception {
        switch (operator) {
        case '+':
        return a1 + a2;
        case '-':
        return a1 - a2;
        case '*':
        return a1 * a2;
        case '/':
        return a1 / a2;
        default:
        break;
        }
        throw new Exception("illegal operator!");
    }

    private static int getPriority(String s) throws Exception {
        if(s==null) return 0;switch(s) {
            case "(":return 1;
            case "+":;
            case "-":return 2;
            case "*":;
            case "/":return 3;
            default:break;
        }throw new Exception("illegal operator!");
    }

    private static String toSufExpr(String expr) throws Exception {

    System.out.println("将"+expr+"解析为后缀表达式...");/*返回结果字符串*/
    StringBuffer sufExpr= new StringBuffer();/*盛放运算符的栈*/
    Stack<String> operator = new Stack();
    operator.push(null);//在栈顶压人一个null，配合它的优先级，目的是减少下面程序的判断

    /*将expr打散分散成运算数和运算符*/
    Pattern p= Pattern.compile("(?");

    Matcher m =p.matcher(expr);
    while(m.find()) {

    String temp=m.group();if (temp.matches("[+\\-*/()]")) { //是运算符

        if (temp.equals("(")) { //遇到左括号，直接压栈
            operator.push(temp);
        System.out.println("'('压栈");

        }else if (temp.equals(")")) { //遇到右括号，弹栈输出直到弹出左括号(左括号不输出)

    String topItem = null;
    while (!(topItem = operator.pop()).equals("(")) {
    System.out.println(topItem+"弹栈");
        sufExpr.append(topItem+" ");
        System.out.println("输出:"+sufExpr);
    }

}else {//遇到运算符，比较栈顶符号，若该运算符优先级大于栈顶，直接压栈；若小于栈顶，弹栈输出直到大于栈顶，然后将改运算符压栈。

while(getPriority(temp) <=getPriority(operator.peek())) {

sufExpr.append(operator.pop()+" ");

System.out.println("输出sufExpr:"+sufExpr);

}

operator.push(temp);

System.out.println("\""+temp+"\""+"压栈");

}

}else {//遇到数字直接输出

sufExpr.append(temp+" ");

System.out.println("输出sufExpr:"+sufExpr);

}

}

String topItem= null;//最后将符合栈弹栈并输出

while(null != (topItem =operator.pop())) {

sufExpr.append(topItem+" ");

}return sufExpr.toString();

}

public static String getResult(String expr) throws Exception {
    System.out.println("计算"+expr);
    /*数字栈*/
    Stack<Double> number = new Stack<Double>(); 
    /*符号栈*/
    Stack<String> operator = new Stack<String>();
    operator.push(null);// 在栈顶压人一个null，配合它的优先级，目的是减少下面程序的判断

    /* 将expr打散为运算数和运算符 */
    //Pattern p = Pattern.compile("(?<!\\d)-?\\d+(\\.\\d+)?|[+\\-*/()]");// 这个正则为匹配表达式中的数字或运算符
    //Matcher m = p.matcher(expr);
    String[] tks = tokenizer(expr);
   // while(m.find()) {
   //     String temp = m.group();
   for(String temp:tks){
        //System.out.println("split:"+temp);
        if(temp.matches("[+\\-*/()]")) {//遇到符号
            if(temp.equals("(")) {//遇到左括号，直接入符号栈
                operator.push(temp);
                //System.out.println("符号栈更新："+operator);
            }else if(temp.equals(")")){//遇到右括号，"符号栈弹栈取栈顶符号b，数字栈弹栈取栈顶数字a1，数字栈弹栈取栈顶数字a2，计算a2 b a1 ,将结果压入数字栈"，重复引号步骤至取栈顶为左括号，将左括号弹出
                String b = null;
                while(!(b = operator.pop()).equals("(")) {
                    //System.out.println("符号栈更新："+operator);
                    double a1 = number.pop();
                    double a2 = number.pop();
                    //System.out.println("数字栈更新："+number);
                    System.out.println("计算"+a2+b+a1);
                    number.push(doubleCal(a2, a1, b.charAt(0)));
                    //System.out.println("数字栈更新："+number);
                }
                //System.out.println("符号栈更新："+operator);
            }else {//遇到运算符，满足该运算符的优先级大于栈顶元素的优先级压栈；否则计算后压栈
                while(getPriority(temp) <= getPriority(operator.peek())) {
                    double a1 = number.pop();
                    double a2 = number.pop();
                    String b = operator.pop();
                    //System.out.println("符号栈更新："+operator);
                    //System.out.println("数字栈更新："+number);
                    System.out.println("计算"+a2+b+a1);
                    number.push(doubleCal(a2, a1, b.charAt(0)));
                    //System.out.println("数字栈更新："+number);
                }
                operator.push(temp);
                //System.out.println("符号栈更新："+operator);
            }
        }else {//遇到数字，直接压入数字栈
            number.push(Double.valueOf(temp));
            //System.out.println("数字栈更新："+number);
        }
    }

    while(operator.peek()!=null) {//遍历结束后，符号栈数字栈依次弹栈计算，并将结果压入数字栈
        double a1 = number.pop();
        double a2 = number.pop();
        String b = operator.pop();
        //System.out.println("符号栈更新："+operator);
        //System.out.println("数字栈更新："+number);
        System.out.println("计算"+a2+b+a1);
        number.push(doubleCal(a2, a1, b.charAt(0)));
        //System.out.println("数字栈更新："+number);
    }
    return number.pop()+"";
}

public static void main(String[] args) throws Exception {
    {
        String str= "-3.5*( 4.5 -( 4 + (-1       -1/2)))";
        String[] ss = tokenizer(str);
        for(String s :ss){
            System.out.print(s+" ");
        }
        System.out.println("result:"+getResult(str));
        System.out.println();
    }
     {
        String str= "-3.7  *  (2   +   -1   -3)";
        String[] ss = tokenizer(str);
        for(String s :ss){
            System.out.print(s+" ");
        }
        System.out.println("result:"+getResult(str));
    } 
}

public static String [] tokenizer(String exp){
    ArrayList<String> tokens = new  ArrayList<String>();
    int offset =0;
    StringBuilder part=new StringBuilder();
    for(int i=0;i<exp.length();i++){
        char ch = exp.charAt(i);
        switch(ch){
            case ' ':

            break;
            case '-':
            case '+':
                if(part.length()>0&&offset>0){tokens.add(part.toString());
                tokens.add(String.valueOf(exp.charAt(i)));
                part=new StringBuilder();
                }else{
                    part.append(ch);
                }
            break;
            case '/':
            case '*':
            case '(':
            case ')':
                if(part.length()>0)tokens.add(part.toString());
                tokens.add(String.valueOf(exp.charAt(i)));
                part=new StringBuilder();
                offset=0;
            break;
            default:
            part.append(ch);
            break;
        }
        offset+=1;
    }
    return tokens.toArray(new String[tokens.size()]);
}


    
}
