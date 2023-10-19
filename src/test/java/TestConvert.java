import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.shmilyhe.convert.ConvertorFactory;
import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.impl.Setter;
import io.shmilyhe.convert.tools.ExpEnv;
import io.shmilyhe.convert.tools.JsonString;

public class TestConvert {
    @Test
    public void test(){
        ConvertorFactory cf = new ConvertorFactory();
        ExpEnv env = new ExpEnv(null);
        Map data = new HashMap();
        Setter seter = new Setter("aa.bb");
        Setter seter2 = new Setter("aa.p1");
        seter.set(data, "test");
        seter2.set(data, "90");
        {
        IConvertor c =cf.func("move(.aa.bb,.cc.ii)",1);
        c.convert(data,env);
        System.out.println(JsonString.asJsonString(data));
        }
        {
            IConvertor c =cf.func("set(.t1.b[3],.cc.ii)",1);
            c.convert(data,env);
            System.out.println(JsonString.asJsonString(data));
        }
        {
            IConvertor c =cf.func("remove(.aa)",1);
            c.convert(data,env);
            System.out.println(JsonString.asJsonString(data));
        }

        {
            IConvertor c =cf.func("move(.,.data)");
            data = (Map) c.convert(data,env);
            System.out.println(JsonString.asJsonString(data));
        }

    }

    @Test
    public void test2(){
        ExpEnv env = new ExpEnv(null);
        ConvertorFactory cf = new ConvertorFactory();
        Map data = new HashMap();
        Setter seter = new Setter("aa.bb");
        Setter seter2 = new Setter("aa.p1");
        seter.set(data, "test");
        seter2.set(data, 90);
        String commands="move(.aa.bb,.cc.ii)\r\n"
        +"set(.t1.b[3],.cc.ii)\r\n"
        +".t1.b[4]=.cc.ii\r\n"
        +".t3=-(.aa.p1*2+(7*2))\r\n"
        +"remove(.aa)\r\n"
        +"move(.,.data)\r\n"
        +"setNotExists(.name,4rt)\r\n"
        ;
        {
        IConvertor c =cf.getConvertor(commands);
        data = (Map) c.convert(data,env);
        System.out.println(JsonString.asJsonString(data));
        }
    }
}
