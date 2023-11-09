package cal;

import java.util.HashMap;

import io.shmilyhe.convert.AstConvertorFactory;
import io.shmilyhe.convert.Json;
import io.shmilyhe.convert.JsonConvertor;
import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.tools.ExpEnv;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestVRL6 {
    public static void main(String[] args) {
        String json =ResourceReader.read("testfile/test1.json");
        String commands =ResourceReader.read("testfile/test6.script");
        Object data =new HashMap<>();//JsonConvertor.toData(json);
        IConvertor convert= new AstConvertorFactory().getConvertor(commands);
        ExpEnv env= new ExpEnv(null);
        env.put("title", "这是一从外部带来的参数测试!");
        data =convert.convert(data, env);
        System.out.println(Json.asJsonString(data));
    }
}
