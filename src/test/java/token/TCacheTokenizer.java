package token;

import io.shmilyhe.convert.ast.token.CacheTokenizer;
import io.shmilyhe.convert.ast.token.Token;

public class TCacheTokenizer {
    public static void main(String[] args) {
        CacheTokenizer c= new CacheTokenizer();
        for(int i=0;i<10;i++){
            c.add(new Token(String.valueOf(i)));
        }
        c.next();
        c.next();
        c.next();
        c.back();
        for(;c.hasNext();){
            System.out.println(c.next().getRaw());
        }
    }
}
