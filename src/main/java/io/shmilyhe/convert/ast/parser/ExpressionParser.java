package io.shmilyhe.convert.ast.parser;

import java.util.List;
import java.util.Stack;


import io.shmilyhe.convert.ast.expression.BinaryExpression;
import io.shmilyhe.convert.ast.expression.CallExpression;
import io.shmilyhe.convert.ast.expression.Expression;
import io.shmilyhe.convert.ast.expression.Identifier;
import io.shmilyhe.convert.ast.expression.Literal;
import io.shmilyhe.convert.ast.token.BracketToken;
import io.shmilyhe.convert.ast.token.CalleeToken;
import io.shmilyhe.convert.ast.token.ITokenizer;
import io.shmilyhe.convert.ast.token.Token;


public class ExpressionParser {
    
    public static Expression parseCallee(ITokenizer tks){
        return getExpression(tks);
    }


    private static void print(ITokenizer tks){
        StringBuilder b = new StringBuilder();
        while(tks.hasNext()){
            b.append("_").append(tks.next());
        }
        tks.reset();
        System.out.println(b);
    }

    static CallExpression getCallExpression(CalleeToken call){
        CallExpression c = new CallExpression();
        c.setCallee(new Identifier(call.getRaw()));
        List<ITokenizer> args =call.getArguments();
        if(args!=null&&args.size()>0){
            for(ITokenizer it:args){
               c.addArgument(getExpression(it));
            }
        }
        return c;
    }

    static Expression getBracketExpression(BracketToken bracket){
          return  getExpression(bracket.getTokens());
    }

    public static Expression getExpression(ITokenizer tks) throws RuntimeException {
        tks=BracketParser.parsebracket(tks);
        /* 数字栈 */
        //Stack<IGet> number = new Stack<IGet>();
        Stack<Expression> number = new Stack<Expression>();
        /* 符号栈 */
        Stack<Token> operator = new Stack<Token>();
        operator.push(null);// 在栈顶压人一个null，配合它的优先级，目的是减少下面程序的判断
       //String[] tks = tokenizer(expr);
        /*for(String k:tks){
            System.out.print(k+"_");
        }*/
        //System.out.println();
        for(;tks.hasNext();){
            Token temp=tks.next();
            //System.out.println(temp+"|"+temp.getType());
            if(temp.getType()==Token.SPACE||temp.getType()==Token.COMMONS||";".equals(temp.getRaw()))continue;
        //for (String temp : tks) {
            // System.out.println("split:"+temp);

            //if (temp.matches("[+\\-*/()%=><\\|\\!&]")) {// 遇到符号
            //    System.out.println(temp+":"+(operate.indexOf(temp)));
            //if(operate.indexOf(temp)>-1){
            if(temp.getType()==Token.SYMBOL){               
                    while (true) {
                        if(operator.size()<=1){
                            break;
                        }
                        Token opt = operator.peek();
                        String p=null;
                        if(opt!=null)p=opt.getRaw();
                        
                        boolean cal = getPriority(temp.getRaw()) <= getPriority(p);
                        if(!cal)break;
                        //IGet a1 = number.pop();
                        //System.out.println("excp:"+temp);
                        //IGet a2 = number.pop();
                        Expression  a1 = number.pop();
                        Expression a2 = number.pop();
                        Token b = operator.pop();
                         BinaryExpression exp= new BinaryExpression();
                        exp.setLeft(a2);
                        exp.setRight(a1);
                        exp.setOperater(b.getRaw());
                        exp.setStart(a1.getStart()).setEnd(a2.getEnd())
                        .setLine(b.getLine());
                        number.push(exp);
                        // System.out.println("符号栈更新："+operator);
                        // System.out.println("数字栈更新："+number);
                        // System.out.println("计算"+a2+b+a1);
                        //number.push(new ExpGeter(a2, a1, OperatorType.find(b)).setExpression(temp).setLine(line));
                        // System.out.println("数字栈更新："+number);
                    }
                    operator.push(temp);
                    // System.out.println("符号栈更新："+operator);
               // }
            } else {// 遇到数字，直接压入数字栈
                //IGet get = null;
                Expression get=null;
                if(temp.getType()==Token.BRACKET){
                   BracketToken bt = (BracketToken)temp;
                    get = getBracketExpression(bt);
                }else if(temp.getType()==Token.IDENTIFIER){
                    get = new Identifier(temp.getRaw());
                    get.setStart(temp.getStart()).setEnd(temp.getEnd())
                    .setLine(temp.getLine());
                }else if(temp.getType()==Token.LITERAL){
                    get = new Literal(temp.getRaw());
                    get.setStart(temp.getStart()).setEnd(temp.getEnd())
                    .setLine(temp.getLine());
                }else if(temp.getType()==Token.CALLEE){
                    //System.out.println("callee:"+temp);
                    get = getCallExpression((CalleeToken)temp);
                    get.setStart(temp.getStart()).setEnd(temp.getEnd())
                    .setLine(temp.getLine());
                }else{
                    System.out.println("ppppp:"+temp);
                }
                number.push(get);
                //System.out.println("数字栈更新："+temp);
            }
        }

        while (operator.peek() != null) {// 遍历结束后，符号栈数字栈依次弹栈计算，并将结果压入数字栈
            Token b = operator.pop();
            //IGet a1 = number.pop();
            //IGet a2 = null;
            Expression  a1 = number.pop();
            Expression a2 = null;
            if(number.size()>0){
                a2=number.pop();
            }else{
                number.push(a1);
                break;
            }
            
            
            // System.out.println("符号栈更新："+operator);
            // System.out.println("数字栈更新："+number);
            // System.out.println("计算"+a2+b+a1);
            BinaryExpression exp= new BinaryExpression();
                exp.setLeft(a2);
                exp.setRight(a1);
                exp.setOperater(b.getRaw());
                exp.setStart(a1.getStart()).setEnd(a2.getEnd())
                .setLine(b.getLine());
                number.push(exp);
            //number.push(new ExpGeter(a2, a1, OperatorType.find(b)));
            // System.out.println("数字栈更新："+number);
        }
        //IGet get =number.pop();
        Expression  a1 = number.pop();
        return a1;
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
