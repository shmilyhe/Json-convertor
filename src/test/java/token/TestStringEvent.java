package token;

import io.shmilyhe.convert.tokenizer.DefaultParser;
import io.shmilyhe.convert.tokenizer.StringTokenizer;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestStringEvent {
    public static void main(String []args){
        StringTokenizer str = new StringTokenizer(ResourceReader.read("testfile/test5.script"));
        new DefaultParser().parse(str, new TestParserEvent());
        System.out.println();
    }
}
