package token;

import io.shmilyhe.convert.ast.token.Token;
import io.shmilyhe.convert.ast.token.Tokenizer;
import io.shmilyhe.convert.tokenizer.StringTokenizer;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestTokenizer {
    public static void main(String[] args) {
         StringTokenizer str = new StringTokenizer(ResourceReader.read("testfile/test5.script"));
        Tokenizer tks = new Tokenizer(str);
        while(tks.hasNext()){
            Token t =tks.next();
            if(t.getType()==Token.COMMONS||
            t.getType()==Token.SPACE)continue;
            System.out.print(t.getRaw()+"["+t.getType()+"]");
        }
         System.out.println();
    }
}
