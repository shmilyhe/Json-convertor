package com;

public class Test1 {
    public static void main(String[] args) {
        String format = String.format("%s用户来自%s，年龄 %d，性别 %s.....",new Object[]{"eric","china","90","man"});
        System.out.println(format);
        
    }
}
