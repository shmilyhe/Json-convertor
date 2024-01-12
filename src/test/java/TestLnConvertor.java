

import io.shmilyhe.convert.JsonConvertor;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestLnConvertor{

    public static void main(String []args){
        String json =ResourceReader.read("testfile/ln.json");
        String json1 =ResourceReader.read("testfile/ln1.json");
        String json2 =ResourceReader.read("testfile/ln2.json");
        String json3 =ResourceReader.read("testfile/ln3.json");
        String commands =ResourceReader.read("testfile/ln.script");
        String commands3 =ResourceReader.read("testfile/ln3.script");
        //JsonConvertor jc = new JsonConvertor(commands);
        JsonConvertor jc = new JsonConvertor(new String[]{commands,commands3});
        String dest = jc.convert(json);
        System.out.println(dest);
        //System.out.println(jc.convert(json1));
        //System.out.println(jc.convert(json2));
        //System.out.println(jc.convert(json3));
    }
}