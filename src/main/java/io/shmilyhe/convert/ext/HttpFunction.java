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
import io.shmilyhe.convert.tools.ExpEnv;
import io.shmilyhe.convert.tools.JsonString;

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
                    httpget(String.valueOf(get.get(data, env)),env);
                    return data;
                   };
                }else{
                    final Setter set = new Setter(removeRootString(g1));
                    if(!g1.startsWith(".")){
                        set.setVar(true);
                    }
                    final IGet get = ExpCalculate.getExpression(g2, num);
                    
                    return (data,env)->{
                        Map res =httpget(String.valueOf(get.get(data, env)),env);
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
    if(cache!=null)cache.cache(key, v);
   }
   static  Map getCache(String key){
    if(key==null||cache==null)return null;
    return cache.getCache(key);
   }



   public static Map httpPost(String url,Map param,ExpEnv env){
    Json jenv =new Json();
        jenv.wrap(env);
        Boolean cache=jenv.Q("http.config.cache").asBoolean();
        Integer readTimeout=jenv.Q("http.config.readTimeout").asInt();
        Integer connectTimeout=jenv.Q("http.config.connectTimeout").asInt();
        if(cache==null)cache=false;
        String key=null;
        try{
            Map rest =null;
            if(cache){
                key=url+JsonString.asJsonString(param);
                rest = getCache(key);
            }
            if(rest!=null)return rest;
            HTTP http = new HTTP();
            http.setConnectTimeout(connectTimeout)
            .setReadTimeout(readTimeout);
            Map headers =(Map)jenv.Q("http.config.headers").getRaw();
            if(headers!=null){
                if(headers.get("Content-Type")==null){
                    headers.put("Content-Type", "application/json");
                    http.header(headers);
                }
            }else{
                http.header("Content-Type", "application/json");
            }
            String resp = http.url(url).param(param).post().asString();
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
            }
            if(cache)cache(key,rest);
            return rest;
        }catch(Exception e){
            return null;
        }

   }

    public static Map httpget(String url,ExpEnv env){
        Json jenv =new Json();
        jenv.wrap(env);
        Boolean cache=jenv.Q("http.config.cache").asBoolean();
        Integer readTimeout=jenv.Q("http.config.readTimeout").asInt();
        Integer connectTimeout=jenv.Q("http.config.connectTimeout").asInt();
        if(cache==null)cache=true;
        try{
            Map rest =null;
            if(cache)rest = getCache(url);
            if(rest!=null)return rest;
            HTTP http = new HTTP();
            http.setConnectTimeout(connectTimeout)
            .setReadTimeout(readTimeout);
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
            }
            if(cache)cache(url,rest);
            return rest;
        }catch(Exception e){
            return null;
        }
    }
}
