package io.shmilyhe.convert.ast.expression;

import java.util.regex.Pattern;

public class Literal extends Expression {
    public final static int V_NUMBER=1;
    public final static int V_STRING=0;
    public final static int V_BOOLEAN=2;
    public final static int V_NULL=3;

    protected int valueType;
    


    public int getValueType() {
        return valueType;
    }

    public Literal setValueType(int valueType) {
        this.valueType = valueType;
        return this;
    }

    public Literal(String name){
        this.setRaw(name);
    }

    @Override
    public String getType() {
        return TYPE_LIT;
    }

    protected Object value;
    
    protected String raw;

    public String getRaw() {
        return raw;
    }

    public Literal setRaw(String raw) {
        this.raw = raw;
        return this;
    }


    public Object getValue() {
        return initValue(raw);
    }

    public Literal setValue(Object value) {
        this.value = value;
        return this;
    }
    
    protected Object initValue(String raw){
        if(getValueType()==V_NUMBER){
            try{
            if(isInt(raw)){
                if(raw.length()<8){
                    return this.isMinus()?-Integer.valueOf(raw):Integer.valueOf(raw);
                }
                return this.isMinus()?-Long.valueOf(raw):Long.valueOf(raw);
            }
            if(isDouble(raw))return Double.valueOf(raw);
            }catch(Exception e){
                throw new RuntimeException("Syntax error wrong number, at line:"+getLine()+" near "+getRaw());
            }
        }else if(getValueType()==V_STRING){
            return unwrap(raw);
        }else if(getValueType()==V_BOOLEAN){
            return this.isMinus()?!Boolean.valueOf(raw):Boolean.valueOf(raw);
        }else if(getValueType()==V_NULL){
            return null;
        }
        throw new RuntimeException("Syntax error unkown value:"+getRaw()+" type:"+getValueType()+", at line:"+getLine());
    }

    Pattern intPattern =Pattern.compile("[0-9]+");
    private boolean isInt(String v){
        if(v==null)return false;
        if(v.length()>18)return false;
        return intPattern.matcher(v).matches();
    }

    Pattern doublePattern =Pattern.compile("[0-9]+\\.[0-9]+");
    private boolean isDouble(String v){
        if(v==null)return false;
        return doublePattern.matcher(v).matches();
    }
    private String unwrap(String str){
        if(str==null)return null;
        int off=str.length()-1;
        if(
            str.charAt(0)=='"'&&str.charAt(off)=='"'
            ||str.charAt(0)=='\''&&str.charAt(off)=='\''
            ){
            return str.substring(1, off)
            .replaceAll("\\\\\"", "\"").replaceAll("\\\\'", "'");
        }
        return str;
    }
    
}
