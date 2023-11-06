package token;

import io.shmilyhe.convert.tokenizer.StringTokenizer;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestStringTokenizer {
    public static void main(String []args){
        StringTokenizer str = new StringTokenizer(ResourceReader.read("testfile/test5.script"));
        char last=0;
        while(str.hasNext()){
             last=str.next();
             if(last=='\r'){
                System.out.print("|");
             }else if(last=='\n'){
                System.out.print("|");
             }else{
                System.out.print(last);
             }
                 
        }
        
    }
}
