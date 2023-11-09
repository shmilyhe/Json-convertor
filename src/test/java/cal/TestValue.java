package cal;

import java.util.regex.Pattern;

public class TestValue {
    public static void main(String[] args) {
        String text ="123456789";
        System.out.println(text.substring(1,text.length()-1));
        TestValue tv = new TestValue();
        System.out.println(tv.isDouble("192.01"));
        System.out.println(tv.isDouble("192"));
        System.out.println(tv.isDouble("192."));
        System.out.println(tv.isInt("192.01"));
        System.out.println(tv.isInt("192"));
        System.out.println(tv.unwrap("\"192"));
        System.out.println(tv.unwrap("\"192\""));
        System.out.println(tv.unwrap("'192'"));
    }

    public static Object getValue(String str){
        if(str==null)return null;
        
        return null;
    }

    Pattern intPattern =Pattern.compile("[0-9]+");
    private boolean isInt(String v){
        if(v==null)return false;
        return intPattern.matcher(v).matches();
    }

    Pattern doublePattern =Pattern.compile("[0-9]+\\.[0-9]+");
    private boolean isDouble(String v){
        if(v==null)return false;
        return doublePattern.matcher(v).matches();
    }

    private boolean isBoolean(String v){
        if(v==null)return false;
        return "true".equals(v)||"false".equals(v);
    }

    private String unwrap(String str){
        if(str==null)return null;
        int off=str.length()-1;
        if(
            str.charAt(0)=='"'&&str.charAt(off)=='"'
            ||str.charAt(0)=='\''&&str.charAt(off)=='\''
            ){
            return str.substring(1, off);
        }
        return str;
    }


}
