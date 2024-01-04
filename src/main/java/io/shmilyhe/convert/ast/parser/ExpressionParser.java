package io.shmilyhe.convert.ast.parser;

import java.util.List;
import java.util.Stack;

import io.shmilyhe.convert.ast.expression.AssignmentExpression;
import io.shmilyhe.convert.ast.expression.BinaryExpression;
import io.shmilyhe.convert.ast.expression.CallExpression;
import io.shmilyhe.convert.ast.expression.Expression;
import io.shmilyhe.convert.ast.expression.Identifier;
import io.shmilyhe.convert.ast.expression.Literal;
import io.shmilyhe.convert.ast.token.BracketToken;
import io.shmilyhe.convert.ast.token.CalleeToken;
import io.shmilyhe.convert.ast.token.ITokenizer;
import io.shmilyhe.convert.ast.token.Token;
import io.shmilyhe.convert.tools.DEBUG;


/**
 * 表达式解析
 */
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
        c.setMinus(call.minus());
        return c;
    }

    static Expression getBracketExpression(BracketToken bracket){
          return  getExpression(bracket.getTokens()).setMinus(bracket.minus());
    }

    public static Expression getExpression(ITokenizer tks) throws RuntimeException {
        tks=BracketParser.parsebracket(tks);
        tks=MinusParser.parseMinus(tks);
        boolean isReturns=false;
        /* 
        System.out.println("==================1");
        for(;tks.hasNext();){
            System.out.println(tks.next());
        }
        System.out.println("==================2");
        tks.reset();
        */
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
        int content =0;
        for(;tks.hasNext();){
            Token temp=tks.next();
            //DEBUG.debug(temp);
            //System.out.println(temp+"|"+temp.getType());
            if("return".equals(temp.getRaw())){
                isReturns=true;
                continue;
            }
            if(temp.getType()==Token.SPACE||temp.getType()==Token.COMMONS||";".equals(temp.getRaw())||temp.isNewline())continue;
            content++;
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
                        Expression exp=null;
                        if("=".equals(b.getRaw())){
                            exp = new AssignmentExpression()
                            .setLeft(a2)
                            .setRight(a1)
                            .setOperater(b.getRaw())
                            .setStart(a1.getStart()).setEnd(a2.getEnd())
                            .setLine(b.getLine());
                        }else{
                            exp = new BinaryExpression()
                            .setLeft(a2)
                            .setRight(a1)
                            .setOperater(b.getRaw())
                            .setStart(a1.getStart()).setEnd(a2.getEnd())
                            .setLine(b.getLine());
                        }
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
                    get = new Literal(temp.getRaw()).setValueType(temp.getValueType());
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
                get.setMinus(temp.minus());
                number.push(get);
                //System.out.println("数字栈更新："+temp);
            }
        }
        if(content==0)return null;
        Token l=null;
        Expression last =null;
        while (operator.peek() != null) {// 遍历结束后，符号栈数字栈依次弹栈计算，并将结果压入数字栈
            Token b = operator.pop();
            //DEBUG.debug("2:",b,b.getClass());
            //IGet a1 = number.pop();
            //IGet a2 = null;
            Expression  a1 = number.pop();
            Expression a2 = null;
            if(number.size()>0){
                a2=number.pop();
            }else{
                System.out.println("xxxxx:"+b);
                System.out.println("xxxxxl:"+l);
                number.push(a1);
                last=a1;
                break;
            }
            l=b;
            
            /*BinaryExpression exp= new BinaryExpression();
                exp.setLeft(a2);
                exp.setRight(a1);
                exp.setOperater(b.getRaw());
                exp.setStart(a1.getStart()).setEnd(a2.getEnd())
                .setLine(b.getLine());*/
            Expression exp=null;
            if("=".equals(b.getRaw())){
                exp = new AssignmentExpression()
                .setLeft(a2)
                .setRight(a1)
                .setOperater(b.getRaw())
                .setStart(a1.getStart()).setEnd(a2.getEnd())
                .setLine(b.getLine());
            }else{
                exp = new BinaryExpression()
                .setLeft(a2)
                .setRight(a1)
                .setOperater(b.getRaw())
                .setStart(a1.getStart()).setEnd(a2.getEnd())
                .setLine(b.getLine());
            }
            //DEBUG.debug("1:",exp);
            last=exp;
            number.push(exp);
            //number.push(new ExpGeter(a2, a1, OperatorType.find(b)));
            // System.out.println("数字栈更新："+number);
        }
        //IGet get =number.pop();
        //DEBUG.debug("LAST:",last);
        Expression  a1 = number.pop();
        if(a1!=null){
            a1.setReturns(isReturns);
        }
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
