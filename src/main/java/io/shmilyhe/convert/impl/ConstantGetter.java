package io.shmilyhe.convert.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.shmilyhe.convert.api.IGet;

public class ConstantGetter implements IGet {

    private Object v;

    public ConstantGetter (String s){
        v=valueOf(s);
    }

    @Override
    public Object get(Object data) {
        return v;
    }

    Pattern pd =Pattern.compile("-?[123456789]\\.[0123456789]*");
    Pattern pl =Pattern.compile("-?[123456789][0123456789]*");
    
    private Object valueOf(String v){
		if(v==null||v.trim().length()==0||"null".equals(v))return null;
		v=v.trim();
		if("true".equalsIgnoreCase(v))return Boolean.TRUE;
		if("false".equalsIgnoreCase(v))return Boolean.FALSE;
		if(isFloat(v)){
			return Double.parseDouble(v);
		}
        if(isInt(v)){
			return Long.parseLong(v);
		}
		return v;
	}
    private boolean isInt(String s){
        return pl.matcher(s).matches();
    }

    private boolean isFloat(String s){
        return pd.matcher(s).matches();
    }
    

}
