package token;

import io.shmilyhe.convert.ast.parser.BracketParser;
import io.shmilyhe.convert.ast.token.ITokenizer;
import io.shmilyhe.convert.ast.token.Tokenizer;
import io.shmilyhe.convert.tokenizer.StringTokenizer;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestBracketParser2 {
    public static void main(String[] args) {
        String commands =ResourceReader.read("testfile/callee.script");
        StringTokenizer stks = new StringTokenizer(commands);
        ITokenizer tks= new Tokenizer(stks);
        tks = BracketParser.parsebracket(tks);

        for(;tks.hasNext();){
            System.out.print(tks.next());
        }
        System.out.println();
    }  
}
