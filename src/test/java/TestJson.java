import org.junit.Test;

import io.shmilyhe.tools.JsonString;
import io.shmilyhe.tools.SimpleJson;

public class TestJson {

    @Test
    public void test(){

        String json="{\"data\" : {\"cc\" : {\"ii\" : \"test\"},\r\n \"t1\" : {\"b\" : [1,true,null,1.2,2.33456,\"test\"]}},\"code\":200}";
        SimpleJson sj = SimpleJson.parse(json);
        System.out.println(JsonString.asJsonString(sj.getRoot()));
    }
    
}
