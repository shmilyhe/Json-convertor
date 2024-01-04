package io.shmilyhe.convert.ast.parser;

import java.util.List;

import io.shmilyhe.convert.ast.statement.BlockStatement;
import io.shmilyhe.convert.ast.statement.EachStatement;
import io.shmilyhe.convert.ast.statement.ExpressionStatement;
import io.shmilyhe.convert.ast.statement.FunctionStatement;
import io.shmilyhe.convert.ast.statement.IfStatement;
import io.shmilyhe.convert.ast.statement.Statement;
import io.shmilyhe.convert.ast.token.CacheTokenizer;
import io.shmilyhe.convert.ast.token.CalleeToken;
import io.shmilyhe.convert.ast.token.ITokenizer;
import io.shmilyhe.convert.ast.token.Token;
import io.shmilyhe.convert.ast.token.Tokenizer;
import io.shmilyhe.convert.tokenizer.StringTokenizer;

public class VRLParser {

    public Statement parse(String str){
        ITokenizer tks= new Tokenizer(new StringTokenizer(str));
        tks = CalleeParser.parseCallee(tks);
        Statement root = new Statement().setType(Statement.TYPE_ROOT);
        Statement curr=root;
        Statement last=null;
        CacheTokenizer exptks= new CacheTokenizer();
        while(tks.hasNext()){
            Token t =tks.next();
            if("function".equals(t.getRaw())){
                FunctionStatement fun = getFunctionStatement(t,tks);
                curr.addBody(fun);
                fun.setLine(t.getLine());
                curr=fun;
                exptks=new CacheTokenizer();
            }else if(t.getType()==Token.CALLEE){
                if("if".equals(t.getRaw())){
                    IfStatement ifs = getIfStatement(t);
                    curr.addBody(ifs);
                    ifs.setLine(t.getLine());
                    curr=ifs;
                    exptks=new CacheTokenizer();
                }else if("each".equals(t.getRaw())){
                    EachStatement each= getEachStatement(t);
                    each.setLine(t.getLine());
                    curr.addBody(each);
                    curr=each;
                    exptks=new CacheTokenizer();
                }else{
                    exptks.add(t);
                }
            }else if(t.getType()==Token.SYMBOL&&"}".equals(t.getRaw())){
                    if(exptks!=null&&exptks.size()>0){
                        ExpressionStatement exps = new ExpressionStatement();
                        exps.setLine(t.getLine());
                        exps.setExperssion(ExpressionParser.parseCallee(exptks));
                        exptks=new CacheTokenizer();
                        curr.addBody(exps);
                    }
                    last=curr;
                    curr=curr.getParent();
                    exptks=new CacheTokenizer();
            }else if(t.getType()==Token.SYMBOL&&"{".equals(t.getRaw())){
                    
            }else if(t.getType()==Token.NEWLINE||";".equals(t.getRaw())){
                    if(exptks!=null&&exptks.size()>0){
                        ExpressionStatement exps = new ExpressionStatement();
                        exps.setLine(t.getLine());
                        //System.out.println("6666:"+exptks);
                        exps.setExperssion(ExpressionParser.parseCallee(exptks));
                        exptks=new CacheTokenizer();
                        curr.addBody(exps);
                    }
            }else if("else".equals(t.getRaw())){
                    
                    if(last==null||!Statement.TYPE_IF.equals(last.getType())){
                        //System.out.println(last.get);
                        throw new RuntimeException("Syntax error at line:"+t.getLine()+" near "+t.getRaw());
                    }
                    IfStatement ifs =(IfStatement)last;
                    Token t1=tks.next();
                    if(t1.getType()==Token.SPACE){
                        t1=tks.next();
                    }
                    if(t1.getType()==Token.CALLEE&&"if".equals(t1.getRaw())){
                        IfStatement al = getIfStatement(t1);
                        al.setLine(t1.getLine());
                        ifs.setAlternate(al);
                        al.setParent(ifs.getParent());
                        curr=al;
                    }else{
                        tks.back();
                        BlockStatement bs = new BlockStatement();
                        bs.setParent(ifs.getParent());
                        ifs.setAlternate(bs);
                        curr=bs;
                    }
            }else if(t.getType()==Token.SPACE||t.getType()==Token.COMMONS){

            }else{
                    exptks.add(t);
            }
        }
        if(exptks!=null&&exptks.size()>0){
            ExpressionStatement exps = new ExpressionStatement();
            exps.setExperssion(ExpressionParser.parseCallee(exptks));
            exptks=new CacheTokenizer();
            curr.addBody(exps);
        }
        return root;
    }


    private FunctionStatement getFunctionStatement(Token t,ITokenizer tks){
        if(!tks.hasNext()){}
        Token tk=tks.next();
        if(tk.getType()==Token.SPACE||tk.getType()==Token.NEWLINE){
            if(tks.hasNext()){
                tk=tks.next();
            }
        }
        if(tk.getType()!=Token.CALLEE){
            throw new RuntimeException("Syntax error at line:"+t.getLine()+" near "+t.getRaw());
        }
        CalleeToken call=(CalleeToken)tk;
        FunctionStatement fs = new FunctionStatement();
        fs.setLine(call.getLine());
        fs.setName(call.getRaw());
        fs.setCall(call);
        return fs;
    }

    private IfStatement getIfStatement(Token t){
        CalleeToken ct = (CalleeToken)t;
        IfStatement ifs = new IfStatement();
        List<ITokenizer> args= ct.getArguments();
        ITokenizer test =null;
        if(args!=null&&args.size()>0){
            test=args.get(0);
        }
        if(test ==null){
            throw new RuntimeException("Syntax error at line:"+t.getLine()+" near "+t.getRaw());
        }
        ifs.setTest(ExpressionParser.parseCallee(test));
        return ifs;
    }

    private EachStatement getEachStatement(Token t){
        CalleeToken ct = (CalleeToken)t;
        EachStatement each = new EachStatement();
        List<ITokenizer> args= ct.getArguments();
        ITokenizer target =null;
        if(args!=null&&args.size()>0){
            target=args.get(0);
        }
        if(target ==null){
            throw new RuntimeException("Syntax error at line:"+t.getLine()+" near "+t.getRaw());
        }
        each.setTarget(ExpressionParser.parseCallee(target));
        return each;
    }
    
}
