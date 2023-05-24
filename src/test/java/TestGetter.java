import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import io.shmilyhe.convert.impl.Getter;
import io.shmilyhe.convert.impl.Setter;
import io.shmilyhe.convert.tools.JsonString;

public class TestGetter {
    
    @Test
    public void test(){
        Map data= new HashMap();
        {
            Setter seter = new Setter("aab");
            Getter geter = new Getter("aab");
            String v =UUID.randomUUID().toString();
            seter.set(data,v);
            Assert.assertEquals("not eq",geter.get(data), v);
            System.out.println(geter.get(data));
        }
        
        {
            String path="aa.cc";
            Setter seter = new Setter(path);
            Getter geter = new Getter(path);
            String v =UUID.randomUUID().toString();
            seter.set(data,v);
            Assert.assertEquals("not eq",geter.get(data), v);
            System.out.println(geter.get(data));
        }

        {
            String path="aa.cc[1]";
            Setter seter = new Setter(path);
            Getter geter = new Getter(path);
            String v =UUID.randomUUID().toString();
            seter.set(data,v);
            Assert.assertEquals("not eq",geter.get(data), v);
            System.out.println(geter.get(data));
            System.out.println(JsonString.asJsonString(data));
        }
        {
            String path="aa.cc[2]";
            Setter seter = new Setter(path);
            Getter geter = new Getter(path);
            String v =UUID.randomUUID().toString();
            seter.set(data,v);
            Assert.assertEquals("not eq",geter.get(data), v);
            System.out.println(geter.get(data));
            System.out.println(JsonString.asJsonString(data));
        }

        {
            String path="aa.cc[3][1]";
            Setter seter = new Setter(path);
            Getter geter = new Getter(path);
            String v =UUID.randomUUID().toString();
            seter.set(data,v);
            Assert.assertEquals("not eq",geter.get(data), v);
            System.out.println(geter.get(data));
            System.out.println(JsonString.asJsonString(data));
        }

        System.out.println("========Getter passed all of test case =========");
      
    }
}
