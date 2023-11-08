package token;

import io.shmilyhe.convert.ast.parser.CalleeParser;
import io.shmilyhe.convert.ast.token.ITokenizer;
import io.shmilyhe.convert.ast.token.Tokenizer;
import io.shmilyhe.convert.tokenizer.StringTokenizer;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestCalleeParser {
    public static void main(String[] args) {
        String commands =ResourceReader.read("testfile/callee.script");
        StringTokenizer stks = new StringTokenizer(commands);
        ITokenizer tks= new Tokenizer(stks);
        tks = CalleeParser.parseCallee(tks);

        for(;tks.hasNext();){
            System.out.print(tks.next());
        }
        System.out.println();
    }  
}
