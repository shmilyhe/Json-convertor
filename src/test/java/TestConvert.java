import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.shmilyhe.convert.ConvertorFactory;
import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.impl.Setter;
import io.shmilyhe.tools.JsonString;

public class TestConvert {
    @Test
    public void test(){
        ConvertorFactory cf = new ConvertorFactory();
        Map data = new HashMap();
        Setter seter = new Setter("aa.bb");
        Setter seter2 = new Setter("aa.p1");
        seter.set(data, "test");
        seter2.set(data, "90");
        {
        IConvertor c =cf.exp("move($.aa.bb,$.cc.ii)");
        c.convert(data);
        System.out.println(JsonString.asJsonString(data));
        }
        {
            IConvertor c =cf.exp("set($.t1.b[3],$.cc.ii)");
            c.convert(data);
            System.out.println(JsonString.asJsonString(data));
        }
        {
            IConvertor c =cf.exp("remove($.aa)");
            c.convert(data);
            System.out.println(JsonString.asJsonString(data));
        }

        {
            IConvertor c =cf.exp("move($,$.data)");
            data = (Map) c.convert(data);
            System.out.println(JsonString.asJsonString(data));
        }

    }

    @Test
    public void test2(){
        ConvertorFactory cf = new ConvertorFactory();
        Map data = new HashMap();
        Setter seter = new Setter("aa.bb");
        Setter seter2 = new Setter("aa.p1");
        seter.set(data, "test");
        seter2.set(data, "90");
        String commands="move($.aa.bb,$.cc.ii)\r\n"
        +"set($.t1.b[3],$.cc.ii)\r\n"
        +"remove($.aa)\r\n"
        +"move($,$.data)\r\n";
        {
        IConvertor c =cf.getConvertor(commands);
        data = (Map) c.convert(data);
        System.out.println(JsonString.asJsonString(data));
        }
    }
}
