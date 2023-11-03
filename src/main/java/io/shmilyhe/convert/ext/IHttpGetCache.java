package io.shmilyhe.convert.ext;

import java.util.Map;


public interface IHttpGetCache {
    void cache(String key,Map v);
    Map getCache(String key);
}
