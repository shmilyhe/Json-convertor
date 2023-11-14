

import io.shmilyhe.convert.JsonConvertor;
import io.shmilyhe.convert.tools.ExpEnv;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestExitJsonConvertor{

    public static void main(String []args){
        String json =ResourceReader.read("testfile/test1.json");
        //String commands =ResourceReader.read("testfile/test1.script");
        Object data =JsonConvertor.toData(json);
        String[] cmds = new String[]{
            ResourceReader.read("testfile/test3.script"),
            ResourceReader.read("testfile/test1.script"),
            ResourceReader.read("testfile/test2.script")
        };
        ExpEnv env= new ExpEnv(null);
        env.put("title", "这是一从外部带来的参数测试!");
        //env.put("notExists", 1);
        JsonConvertor j = new JsonConvertor(cmds);
        data= j.convert(data,env);
        System.out.println(env.isExited());
        System.out.println(env.getExitCode());
        System.out.println(JsonConvertor.toJsonString(data));
    }
}