package io.shmilyhe.convert;

import java.util.List;

import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.ast.expression.AssignmentExpression;

import io.shmilyhe.convert.ast.expression.CallExpression;
import io.shmilyhe.convert.ast.expression.Expression;
import io.shmilyhe.convert.ast.expression.Identifier;

import io.shmilyhe.convert.ast.parser.VRLParser;
import io.shmilyhe.convert.ast.statement.EachStatement;
import io.shmilyhe.convert.ast.statement.ExpressionStatement;
import io.shmilyhe.convert.ast.statement.FunctionStatement;
import io.shmilyhe.convert.ast.statement.IfStatement;
import io.shmilyhe.convert.ast.statement.Statement;
import io.shmilyhe.convert.ast.token.CalleeToken;
import io.shmilyhe.convert.impl.BaseConvertor;
import io.shmilyhe.convert.impl.ComplexConvertor;

import io.shmilyhe.convert.impl.EachConvertor;
import io.shmilyhe.convert.impl.FunctionConvertor;
import io.shmilyhe.convert.impl.IfConvertor;
import io.shmilyhe.convert.impl.Setter;
import io.shmilyhe.convert.system.SystemFunction;
import io.shmilyhe.convert.tools.DEBUG;
import io.shmilyhe.convert.tools.ExpEnv;

/**
 * AST 生成转换器
 */
public class AstConvertorFactory {
    SystemFunction systemfunction=new SystemFunction();

    /**
     * AST 生成转换器
     * @param commands 脚本
     * @return 转换器
     */
    public IConvertor getConvertor(String commands){
        Statement stat =parse(commands);
        BaseConvertor convertor = new ComplexConvertor().setName("root");
        for(Statement s :stat.getBody()){
            getConvertor(s,convertor);
        }
        return convertor;
    }

    /**
     * 解析语法
     * @param commands  脚本
     * @return 段落
     */
    private Statement parse(String commands){
        VRLParser vrl = new VRLParser();
        Statement stat = vrl.parse(commands);
        stat.clearParent();
        //System.out.println("=========================================================");
        //System.out.println(Json.asJsonString(stat));
        //System.out.println("=========================================================");
        return stat;
    }

    /**
     * 转换
     * @param stat 代码块
     * @param parent 父转换器
     */
    private void getConvertor(Statement stat,BaseConvertor parent){
        //System.out.println("type:"+stat.getType());
        if(stat.isCallee()){
         parent.addConvertor(calleeStatement(stat));
        }else if(stat.isExpression()){

         parent.addConvertor(expStatement(stat));
        }else if(stat.isEach()){
         parent.addConvertor(eachStatement(stat));
        }else if(stat.isBlock()){
         parent.addConvertor(blockStatement(stat));
        }else if(stat.isIf()){
         parent.addConvertor(ifStatement(stat));
        }else if(stat.isFuntion()){
            func(stat,parent);
         //parent.addConvertor(ifStatement(stat));
        }

    }

    public void func(Statement stat,BaseConvertor parent){
        FunctionStatement fun = (FunctionStatement)stat;
            //System.out.println("function:"+fun.getName());
            IConvertor re=null;
            CalleeToken ctk =  fun.getCall();
            List<Statement> exps = fun.getBody();
            //System.out.println("zixe:"+exps.size());
            FunctionConvertor comx = new FunctionConvertor();
            comx.setCallee(ctk);
            for(Statement s:exps){
                if(s instanceof ExpressionStatement){
                    ExpressionStatement exp=(ExpressionStatement)s;
                    if(exp.getExperssion()!=null&&exp.getExperssion().isReturns()){
                        IGet get =SystemFunction.getExp(exp.getExperssion());
                        re=(data,env)->{
                            return get.get(data, env);
                        };
                        comx.setReturns(re);
                    }else{
                        getConvertor(s,comx);
                    }
                    
                }else{
                    getConvertor(s,comx);
                }
            }
            parent.registry(fun.getName(), (data,env)->{
                //System.out.println("call:"+fun.getName());
                ExpEnv e =  comx.callEnv(data, env,parent);
                return comx.convert(comx, e);
            });
    }


    /**
     * 调用代码块
     * @param stat
     * @return
     */
    private IConvertor calleeStatement(Statement stat){
       
        ExpressionStatement estat = (ExpressionStatement)stat;
        CallExpression callee=(CallExpression)estat.getExperssion();
        String fname=((Identifier)callee.getCallee()).getName();
        List<Expression> args = callee.getArguments();
         //System.out.println("call:"+fname+" args:"+args.size());
        return systemfunction.func(fname, args,estat.getLine());
    }

    /**
     * 表达式块
     * @param stat
     * @return
     */
    private IConvertor expStatement(Statement stat){
        //System.out.println("exp:"+stat.getType());
        ExpressionStatement es = (ExpressionStatement)stat;
        if(es.getExperssion()==null||!es.getExperssion().isAssignment()){
            return null;
        }
        AssignmentExpression ae =(AssignmentExpression)es.getExperssion();
        String a=((Identifier)ae.getLeft()).getName();
        final IGet get = getExp(ae.getRight());
        if(".".equals(a)){
            return (data,env)->{ return get.get(data,env);};
        }
        final Setter set =new Setter(SystemFunction.removeRootString(a));
        set.setVar(!a.startsWith("."));
        return (data,env)->{ 
            set.set(set.isVar()?env:data, get.get(data,env));
            return data;
        };

    }

    private IConvertor returnStatement(Statement stat){
        //System.out.println("exp:"+stat.getType());
        ExpressionStatement es = (ExpressionStatement)stat;
        if(es.getExperssion()==null||!es.getExperssion().isAssignment()){
            return null;
        }
        AssignmentExpression ae =(AssignmentExpression)es.getExperssion();
        String a=((Identifier)ae.getLeft()).getName();
        final IGet get = getExp(ae.getRight());
        if(".".equals(a)){
            return (data,env)->{ return get.get(data,env);};
        }
        final Setter set =new Setter(SystemFunction.removeRootString(a));
        set.setVar(!a.startsWith("."));
        return (data,env)->{ 
            return get.get(data,env);
        };

    }


    private IGet getExp(Expression exp){
        return SystemFunction.getExp(exp);
    }


    /**
     * IF块
     * @param stat
     * @return
     */
    private IConvertor ifStatement(Statement stat){
        //System.out.println("if:"+stat.getType());
        IfStatement is = (IfStatement)stat;
        IfConvertor bc=(IfConvertor) new IfConvertor(this.getExp(is.getTest())).setName("if");
        if(is.getBody()!=null){
            for(Statement s :is.getBody()){
                getConvertor(s,bc);
            }
        }
        if(is.getAlternate()!=null){
           Statement alter =  is.getAlternate();
           if(alter.isIf()){
            bc.setAlternate(ifStatement(alter));
           }else if(alter.isEach()){
            bc.setAlternate(eachStatement(alter));
           }else if(alter.isBlock()){
            bc.setAlternate(blockStatement(alter));
           }

        }
        return bc;
    }


    /**
     * Each 块
     * @param stat
     * @return
     */
    private IConvertor eachStatement(Statement stat){
        //System.out.println("each:"+stat.getType());
        EachStatement es =(EachStatement)stat;
        Identifier id =(Identifier)es.getTarget();
        BaseConvertor eac =new EachConvertor(id.getName()).setName("each");
        if(es.getBody()!=null){
            for(Statement s :stat.getBody()){
                getConvertor(s,eac);
            }
        }
        return eac;
    }


    /**
     * 块
     * @param stat
     * @return
     */
    private IConvertor blockStatement(Statement stat){
        //System.out.println("block:"+stat.getType());
        BaseConvertor block = new ComplexConvertor().setName("block");
        for(Statement s :stat.getBody()){
            getConvertor(s,block);
        }
        return block;
    }


    
}
