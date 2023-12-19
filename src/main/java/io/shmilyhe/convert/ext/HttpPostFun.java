package io.shmilyhe.convert.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.ExpEnv;

public class HttpPostFun implements IFunction {

    @Override
    public Object call(List args, ExpEnv env) {
        String url =null;
        Map param=null;
        if(args!=null&&args.size()>1){
            url=String.valueOf(args.get(0));
            param=(Map) args.get(1);
        }
        if(url==null)return null;
        if(param ==null)param= new HashMap();
        return HttpFunction.httpPost(url,param,env);
    }
    
}
