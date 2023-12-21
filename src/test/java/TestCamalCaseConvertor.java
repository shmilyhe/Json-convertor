

import io.shmilyhe.convert.JsonConvertor;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestCamalCaseConvertor{

    public static void main(String []args){
        String json =ResourceReader.read("testfile/camal.json");
        String commands =ResourceReader.read("testfile/camal.script");
        JsonConvertor jc = new JsonConvertor(commands);
        String dest = jc.convert(json);
        System.out.println(dest);
    }
}