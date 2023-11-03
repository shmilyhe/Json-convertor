import java.util.Map;

import io.shmilyhe.convert.Json;
import io.shmilyhe.convert.ext.HTTP;
import io.shmilyhe.convert.ext.HttpFunction;

public class TestHttp {

    public static void main(String[] args){
        HTTP http = new HTTP();
        String str  = http.url("http://127.0.0.1:37002/1231").get().asString();
        System.out.println(str);
        System.out.println(http.getResponseHeader("Content-Type"));
        System.out.println(http.getResponseCode());
        System.out.println(http.getErrorMessage());
        Map m = HttpFunction.httpget("https://www.baidu.com");
        System.out.println(Json.asJsonString(m));
    }
    
} 
