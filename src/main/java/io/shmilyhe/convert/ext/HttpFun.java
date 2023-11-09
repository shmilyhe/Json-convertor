package io.shmilyhe.convert.ext;

import java.util.List;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.ExpEnv;

public class HttpFun implements IFunction {

    @Override
    public Object call(List args, ExpEnv env) {
        String url =null;
        if(args!=null&&args.size()>0){
            url=String.valueOf(args.get(0));
        }
        if(url==null)return null;
        return HttpFunction.httpget(url, env);
    }
    
}
