import org.junit.Test;

import io.shmilyhe.convert.tools.JsonString;
import io.shmilyhe.convert.tools.SimpleJson;

public class TestJson {

    @Test
    public void test(){
        String v="{\\\"eric\\\":2342}";
        v= v.replaceAll("\\\\\"","\"");
        System.out.println(v);
        System.out.println(1.0+2.0);
        String json="{\"data\" : {\"cc\" : {\"ii\" : \"test\"},\r\n \"t1\" : {\"b\" : [1,true,null,1.2,2.33456,\"test\"]}},\"code\":200}";
        SimpleJson sj = SimpleJson.parse(json);
        System.out.println(JsonString.asJsonString(sj.getRoot()));
    }
    
}
