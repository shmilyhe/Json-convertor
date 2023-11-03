package io.shmilyhe.convert.ext;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.shmilyhe.convert.Json;
import io.shmilyhe.convert.api.IConvertor;
import io.shmilyhe.convert.api.IGet;
import io.shmilyhe.convert.impl.Setter;
import io.shmilyhe.convert.tools.ExpCalculate;

public class HttpFunction {
    static Pattern p =Pattern.compile(" *(.+) *= *httpget *\\((.*)\\) *| *httpget *\\((.+)\\) *");
    static IHttpGetCache cache;
    public static void setCache(IHttpGetCache c){
        cache=c;
    }
    private static String removeRootString(String s){
        if(s==null)return null;
        s=s.trim();
        if(s.startsWith("."))return s.substring(1);
        return s;
    }
    
    public static IConvertor getHttpGetFuncion(String line,int num){
        if(line==null)return null;
        Matcher m = p.matcher(line);
            if(m.matches()){
                String g1=m.group(1);
                String g2=m.group(2);
                String g3=m.group(3);
                if(g3!=null){
                    final IGet get = ExpCalculate.getExpression(g3, num);
                   return (data,env)->{
                    httpget(String.valueOf(get.get(data, env)));
                    return data;
                   };
                }else{
                    final Setter set = new Setter(removeRootString(g1));
                    if(!g1.startsWith(".")){
                        set.setVar(true);
                    }
                    final IGet get = ExpCalculate.getExpression(g2, num);
                    
                    return (data,env)->{
                        Map res =httpget(String.valueOf(get.get(data, env)));
                        if(set.isVar()){
                            set.set(env, res);
                        }else{
                            set.set(data, res);
                        }
                        return data;
                    };
                }
            }else{
                return null;
            }
    }


   static void cache(String key,Map v){
    if(v==null)return;
    if(cache!=null)cache.cache(key, v);
   }
   static  Map getCache(String key){
    if(key==null||cache==null)return null;
    return cache.getCache(key);
   }

    public static Map httpget(String url){
        try{
            Map rest = getCache(url);
            if(rest!=null)return rest;
            HTTP http = new HTTP();
            String resp = http.url(url).get().asString();
            //System.out.println("rrr:"+resp);
            int code=http.getResponseCode();
            rest = new HashMap();
            rest.put("code", code);
            if(code!=200&&code!=201){
                rest.put("data", http.getErrorMessage());
                cache(url,rest);
                return rest;
            }
            String ctype = http.getResponseHeader("Content-Type");
            if(ctype==null||ctype.indexOf("json")<0){
                resp=resp.replaceAll("[\r\n]+", "\\\\n");
                resp=resp.replaceAll("\"", "\\\\\"");
                rest.put("data", resp);
            }else{
                Json j= Json.parse(resp);
                rest.put("data", j.getRaw());
                //System.out.println(j.Q("data.summary"));
            }
            cache(url,rest);
            return rest;
        }catch(Exception e){
            return null;
        }
    }
}
