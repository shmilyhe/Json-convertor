package com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestIsHttpFunction {
    public static void main(String[] args){
        String [] texts= new String[]{
            "aaa=httpget(\"123\")",
            "aaa = httpget ( \"123\"   )",
            " httpget (\"123\")",
            " httpget ( \"123\"+123*123   )",
            " ahttpget ( \"123\"+123*123   )",
            " .a.e[1] =httpget (.server.host+\"?a=\"+123+\"&b=\"+c  )",
            " .a.e[1] =httpget (.server.host+\"?a=\"+123+\"&b=\"+c)"
        };
        Pattern p =Pattern.compile(" *(.+) *= *httpget *\\((.*)\\) *| *httpget *\\((.+)\\) *");
        for(String s :texts){
            Matcher m = p.matcher(s);
            if(m.matches()){
                String g1=m.group(1);
                String g2=m.group(2);
                String g3=m.group(3);
                if(g3!=null){
                    System.out.println(g3);
                }else{
                    System.out.println(g1+"|"+g2);
                }
            }else{
                System.out.println("not matcher:"+s);
            }
        }
    }
}
