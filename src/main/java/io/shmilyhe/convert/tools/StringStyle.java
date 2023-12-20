package io.shmilyhe.convert.tools;

public class StringStyle {
    
    public static String camelCase(String str){
        if(str==null)return null;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<str.length();i++){
            char c =str.charAt(i);
            if(c=='_'){
                if(i<str.length()){
                    i++;
                    sb.append(upper(str.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String upperCamelCase(String str){
        if(str==null)return null;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<str.length();i++){
            char c =str.charAt(i);
            if(c=='_'){
                if(i<str.length()){
                    i++;
                    sb.append(upper(str.charAt(i)));
                }
            }else{
                if(i==0){
                    sb.append(upper(c));
                }else{
                    sb.append(c);
                }  
            }
        }
        return sb.toString();
    }

    public static String unixLike(String str){
        if(str==null)return null;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<str.length();i++){
            char c =str.charAt(i);
            if(isUpper(c)){
                sb.append('_').append(lower(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

     private static boolean isLower(char c){
        return c>96&&c<123;
     }
     private static boolean isUpper(char c){
        return c>64&&c<91;
     }

    private static char lower(char c){
        if(isUpper(c)){
            return (char)(c+32);
        }
        return c;
    }

    private static char upper(char c){
        if(isLower(c)){
            return (char)(c-32);
        }
        return c;
    }
    public static void main(String[] args) {
        String text="ab_cd_abb_zfb_Baa_10_er_中k";
        String camal=camelCase(text);
        System.out.println(camal);
        System.out.println(upperCamelCase(text));

        String text2="ab_cd_abb_zfb_baa_er_k";
        String camal2=camelCase(text2);
        String test =unixLike(camal2);
        System.out.println(test);
        System.out.println(text2);
        System.out.println(test.equals(text2));
    }
}
