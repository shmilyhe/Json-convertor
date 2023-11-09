package io.shmilyhe.convert.system;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.tools.ExpEnv;
import io.shmilyhe.convert.tools.StringValue;

/**
 * 保留小数
 * 用法 round(数值,保留位数) 如 round(3.33333,2) 结果是 3.33
 */
public class RoundRfuntion implements IFunction {

    @Override
    public Object call(List args, ExpEnv env) {
        try{
            Object num =args.get(0);
            if(num==null)return null;
            Integer scale =(Integer)args.get(1);
            if(scale==null)scale=2;
            BigDecimal dec =null;
            if(num instanceof Integer){
                dec = new BigDecimal((Integer)num);
            }else if(num instanceof Long){
                dec = new BigDecimal((Long)num);
            }else if(num instanceof Double){
                dec = new BigDecimal((Double)num);
            }else if(num instanceof Float){
                dec = new BigDecimal((Float)num);
            }else {
                dec = new BigDecimal(StringValue.toDouble(String.valueOf(num)));
            }
            return dec.setScale(scale, RoundingMode.HALF_UP).doubleValue();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        } 
    }
    
}
