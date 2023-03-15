import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import io.shmilyhe.convert.impl.ArrayDataAccess;


public class TestArraAccess {
    
    @Test
    public void test(){

        ArrayList ar =new ArrayList();
        ar.add("0");
        ar.add("1");
        ar.add("2");
        ar.add("3");

        ar.set(1, "p");

        ArrayDataAccess aa3 = new ArrayDataAccess(3,true);
        ArrayDataAccess aa4 = new ArrayDataAccess(4,true);
        ArrayDataAccess aa5 = new ArrayDataAccess(5,true);
        String data=UUID.randomUUID().toString();

        aa3.set(data, ar);
        aa4.set(data, ar);
        aa5.set(data, ar);
        System.out.println("==--------==");
        for(Object o:ar){
            System.out.println(o);
        }
        System.out.println("==------==");
        System.out.println(ar.get(3));
        System.out.println("====");
        System.out.println(aa3.get(ar));
        Assert.assertEquals("ar3 not eq",data, aa3.get(ar));
        Assert.assertEquals(data, aa5.get(ar));
        Assert.assertEquals(data, ar.get(3));
        Assert.assertEquals(data, ar.get(5));

    }
}
