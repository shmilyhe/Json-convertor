package token;

import io.shmilyhe.convert.tokenizer.Block;
import io.shmilyhe.convert.tokenizer.IParserEvent;

public class TestParserEvent implements IParserEvent{

    

    @Override
    public Block getBlock() {
        return null;
    }

    @Override
    public void nextLine() {
        System.out.println();
    }

    @Override
    public void text(String str, int line, int col) {
        System.out.print("__"+str);
    }

    @Override
    public void symbol(String str, int line, int col) {
        System.out.print("_"+str);
    }

    @Override
    public void function(String str, int line, int col) {
        System.out.print("_"+str+"(");
    }
    
}
