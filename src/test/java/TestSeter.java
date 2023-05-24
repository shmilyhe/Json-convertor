import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.shmilyhe.convert.impl.Setter;
import io.shmilyhe.convert.tools.JsonString;

public class TestSeter {
    
    @Test
    public void test(){
        Map data= new HashMap();
        Setter seter = new Setter("aab");
        
        seter.set(data,"erqr");
        seter.set(data,"ooo");
        Setter seter2 = new Setter("aa.qwe");
        seter2.set(data, "123");

        Setter seter3 = new Setter("aa.oo");
        seter3.set(data, "pppp");
        System.out.println(JsonString.asJsonString(data));
        Setter seter4 = new Setter("aa.oo.oo.oo");
        seter4.set(data, "ppp");
        long bt =System.currentTimeMillis();
        Setter seter5 = new Setter("aa.bb[0][3][1]");
        //for(int i=0;i<10000000;i++){
            seter3.set(data, "pppp");
            seter4.set(data, "ppp");
            seter5.set(data, "a1");
        //}
        long et =System.currentTimeMillis();
        System.out.println(et-bt);
        
        System.out.println(JsonString.asJsonString(data));
        
    }
}
