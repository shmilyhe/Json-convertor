package token;

import io.shmilyhe.convert.tokenizer.DefaultParser;
import io.shmilyhe.convert.tokenizer.DefaultParserEvent;
import io.shmilyhe.convert.tokenizer.StringTokenizer;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestDefaultEvent {
    public static void main(String []args){
        StringTokenizer str = new StringTokenizer(ResourceReader.read("testfile/test5.script"));
        DefaultParserEvent evnet = new DefaultParserEvent();
        new DefaultParser().parse(str, evnet);
        System.out.println(evnet.getBlock());
    }
}
