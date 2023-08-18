

import io.shmilyhe.convert.JsonConvertor;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestJsonConvertor{

    public static void main(String []args){
        String json =ResourceReader.read("testfile/test1.json");
        String commands =ResourceReader.read("testfile/test1.script");
        JsonConvertor jc = new JsonConvertor(commands);
        String dest = jc.convert(json);
        System.out.println(dest);
    }
}