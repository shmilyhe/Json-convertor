import java.util.HashMap;
import java.util.Map;

import io.shmilyhe.convert.Json;
import io.shmilyhe.convert.ext.IHttpGetCache;

public class TestHttpGetCache implements IHttpGetCache {
    HashMap<String,Map> cache= new HashMap();
    @Override
    public void cache(String key, Map v) {
        System.out.println("=========================================");
        System.out.println("缓存"+key+":"+Json.asJsonString(v));
        cache.put(key, v);
    }

    @Override
    public Map getCache(String key) {
        Map r =cache.get(key);
        System.out.println("从缓存中获取数据："+key+":"+Json.asJsonString(r));
        return r;
    }
    
}
