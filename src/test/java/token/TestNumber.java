package token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestNumber {
    public static void main(String[] args) {
        System.out.println(isNumber("0.1"));
        System.out.println(isNumber("1000"));
        System.out.println(isNumber("0.1.1"));
        System.out.println(isNumber("0001"));
        System.out.println(isNumber("0.1"));
        System.out.println(isNumber("0a.1"));

    }

    static Pattern number=Pattern.compile("[0-9.]+");

    private static boolean isNumber(String str){
        if(str==null||str.length()==0)return false;
        Matcher m= number.matcher(str);
        return m.matches();
    }
}
