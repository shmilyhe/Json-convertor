import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.shmilyhe.convert.JsonConvertor;
import io.shmilyhe.convert.impl.Setter;
import io.shmilyhe.convert.tools.JsonString;

public class TestJsonConvertor{

    private Object testData(){
        Map data = new HashMap();
        {
            Setter seter = new Setter("name");
            seter.set(data, "eric");
        }
        {
            Setter seter = new Setter("id");
            seter.set(data, 1);
        }
        {
            Setter seter = new Setter("age");
            seter.set(data, 23);
        }
        {
            Setter seter = new Setter("addr.contry");
            seter.set(data, "china");
        }
        {
            Setter seter = new Setter("addr.province");
            seter.set(data, "gd");
        }
        {
            Setter seter = new Setter("addr.ctiy");
            seter.set(data, "gz");
        }
        {
            Setter seter = new Setter("group");
            seter.set(data,new Object[]{"g1","g2"});
        }
        return data;
    }

    @Test
    public void test(){
        String json =JsonString.asJsonString(testData());
        System.out.println(json);
        String commands ="set(.age,12)\r\n"
        +"set(.group[4],1)\r\n"
        +".ext=.addr\r\n"
        +"move(.addr.contry,.contry)\r\n"
        +"remove(.age)";
        JsonConvertor jc = new JsonConvertor(commands);
        String dest = jc.convert(json);
        System.out.println(dest);
    }
}