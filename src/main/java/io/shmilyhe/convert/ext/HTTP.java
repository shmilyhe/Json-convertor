package io.shmilyhe.convert.ext;

import javax.net.ssl.*;

import io.shmilyhe.convert.Json;
import io.shmilyhe.convert.tools.JsonString;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * http请求工具类
 *
 * @author shixf
 * @since 2021-12-13
 */
public class HTTP {
    private static final Charset UTF_8 = Charset.forName("utf-8");

    private static final int DEFAULT_CONNECT_TIMEOUT = 1000;

    private static final int DEFAULT_READ_TIMEOUT = 6000;


    private HttpURLConnection conn;

    private String url;

    private HashMap<String, String> headers = new HashMap<String, String>();

    private HashMap<String, String> param = new HashMap<String, String>();

    private OutputStream out;

    private InputStream in;

    private boolean error = false;

    private String errMsg = "";

    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;

   

    private int readTimeout = DEFAULT_READ_TIMEOUT;

    private static X509Certificate[] certs;

    public static X509Certificate[] getCerts() {
        return certs;
    }

    public static void setCerts(X509Certificate[] certs) {
        HTTP.certs = certs;
    }

    public HTTP(String url) {
        this.url = url;
    }

    public HTTP() {
    }

    public HTTP url(String url) {
        this.url = url;
        return this;
    }

    public HTTP postForm(Object param) {
        this.header("Content-Type", "application/x-www-form-urlencoded");
        this.post(FormString.asForm(param));
        return this;
    }

    public HTTP get() {
        try {
            StringBuilder sb = new StringBuilder();
            if (param != null && param.size() > 0) {
                boolean isFirst = true;
                for (Entry<String, String> e : param.entrySet()) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        sb.append("&");
                    }
                    sb.append(e.getKey()).append("=").append(e.getValue());
                }
                if (url.indexOf('?') > -1) {
                    url += "&" + sb;
                } else {
                    url += "?" + sb;
                }
            }

            open("GET");
            conn.setDoOutput(false);
        } catch (Exception e) {
            error = true;
            errMsg = "can not access :" + url + " msg:" + e.getMessage();
            //log.error("{}", e.getMessage(), e);
        }
        return this;
    }

    public HTTP post() {
        post(param);
        return this;
    }

    public HTTP post(File o) {

        return this;
    }

    public HTTP post(Object o) {
        if (o != null)
            post(Json.asJsonString(o));
        return this;
    }

    public HTTP post(String o) {
        if (o != null)
            post(o.getBytes(UTF_8));
        return this;
    }

    public HTTP post(byte[] bytes) {
        if (bytes != null)
            try {
                if (isHTTPS(url)&&!sslIint) {
                    HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustCerts, new SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                }
                URL uri = new URL(url);
                conn = (HttpURLConnection) uri.openConnection();
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setConnectTimeout(this.connectTimeout);
                conn.setReadTimeout(this.readTimeout);
                conn.setRequestMethod("POST");
                setHeaders(conn, this.headers);
                if (headers == null || !headers.containsKey("Content-Type"))
                    conn.setRequestProperty("Content-Type", "application/json");
                conn.connect();
                OutputStream out = conn.getOutputStream();
                out.write(bytes);
                out.close();
                int status = conn.getResponseCode();
            } catch (Exception e) {
                error = true;
                errMsg = "can not access :" + url + " nmsg:" + e.getMessage();
                //log.error("{}", e.getMessage(), e);
            }
        return this;
    }
    private HTTP doRequest(byte[] bytes,String method) {
        if (bytes != null)
            try {
                if (isHTTPS(url)&&!sslIint) {
                    HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustCerts, new SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                }
                URL uri = new URL(url);
                conn = (HttpURLConnection) uri.openConnection();
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setConnectTimeout(this.connectTimeout);
                conn.setReadTimeout(this.readTimeout);
                conn.setRequestMethod(method);
                setHeaders(conn, this.headers);
                if (headers == null || !headers.containsKey("Content-Type"))
                    conn.setRequestProperty("Content-Type", "application/json");
                conn.connect();
                OutputStream out = conn.getOutputStream();
                out.write(bytes);
                out.close();
                int status = conn.getResponseCode();
            } catch (Exception e) {
                error = true;
                errMsg = "can not access :" + url + " nmsg:" + e.getMessage();
                //log.error("{}", e.getMessage(), e);
            }
        return this;
    }

    private static void setHeaders(HttpURLConnection conn, Map<String, String> header) {
        if (header == null)
            return;
        for (Object o : header.entrySet()) {
            Entry<String, String> entry = (Entry<String, String>) o;
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    public HTTP param(Map param) {
        this.param.putAll(param);
        return this;
    }

    public HTTP header(Map param) {
        headers.putAll(param);
        return this;
    }

    public HTTP param(String k, String v) {
        param.put(k, v);
        return this;
    }

    public HTTP header(String k, String v) {
        headers.put(k, v);
        return this;
    }

    public String asString() {
        return asString(UTF_8);
    }

    public String asString(Charset charset) {
        if (this.error)
            return "{code:\"-1\",msg:\"" + errMsg + "\"}";
        return new String(asBytes(), charset);
    }
    public byte[] asBytes() {
        try {
            in = conn.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) > 0) {
                bos.write(b, 0, len);
            }
            in.close();
            in = null;
            if (out != null) {
                out.close();
            }
            out = null;
            byte[] bs = bos.toByteArray();
            bos.close();
            return bs;
        } catch (IOException e) {
            error = true;
            errMsg = e.getMessage();
            //log.error("{}", e.getMessage(), e);
        }
        return new byte[0];
    }

    public byte[] errorAsBytes() {
        try {
            in = conn.getErrorStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) > 0) {
                bos.write(b, 0, len);
            }
            in.close();
            in = null;
            if (out != null) {
                out.close();
            }
            out = null;
            byte[] bs = bos.toByteArray();
            bos.close();
            return bs;
        } catch (IOException e) {
            error = true;
            errMsg = e.getMessage();
            //log.error("{}", e.getMessage(), e);
        }
        return new byte[0];
    }

    public int getResponseCode(){
        try {
            return conn.getResponseCode();
        } catch (IOException e) {
            //e.printStackTrace();
            return 100;
        }
    }

    public String getErrorMessage(){
        try {
            return new String(errorAsBytes());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    static boolean sslIint=false;
    private void open(String method) throws Exception {
        URL httpurl = null;
        if (isHTTPS(url)&&!sslIint) {
            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            sslIint=true;
        }
        httpurl = new URL(url);
        conn = (HttpURLConnection) httpurl.openConnection();
        conn.setRequestMethod(method);// POST GET PUT DELETE
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        if (headers != null && headers.size() > 0) {
            for (Entry<String, String> e : headers.entrySet()) {
                conn.setRequestProperty(e.getKey(), e.getValue());
            }
        }
    }

    public String getResponseHeader(String k) {
        return conn.getHeaderField(k);
    }

    public boolean isHTTPS(String url) {
        if (url.toLowerCase().startsWith("https"))
            return true;
        return false;
    }

    public HTTP setConnectTimeout(Integer timeout) {
        if(timeout==null)return this;
        connectTimeout = timeout;
        return this;
    }

    public HTTP setReadTimeout(Integer timeout) {
        if(timeout==null)return this;
        readTimeout = timeout;
        return this;
    }

    public HTTP request(Map param){
        Map header=(Map)param.get("headers");
        this.header(param);
        String method=(String)param.get("method");
        if(method==null)method="GET";
        method=method.toUpperCase();
        Map query=(Map)param.get("query");
        String url=(String)param.get("url");
        this.url(getUrl(url,query));
        if(method.equals("GET")){
            this.get();
            return this;
        }
        Object body=param.get("body");
        if(body instanceof String){
            this.doRequest(body.toString().getBytes(UTF_8), method);
            return this;
        }else if(body instanceof byte[]){
            this.doRequest((byte[])body, method);
            return this;
        }else{
            String type=(String)param.get("type");
            if(type==null||"json".equals(type)){
                this.doRequest(JsonString.asJsonString(body).getBytes(UTF_8), method);
            }else{
                this.doRequest(FormString.asForm(body).getBytes(UTF_8), method);
            }
            return this;
        }

    }

    public String getUrl(String url,Map query){
        if(query==null||query.size()==0)return url;
        StringBuilder sb = new StringBuilder();
        if(!url.toLowerCase().startsWith("http")){
            sb.append("http://");
        }
        sb.append(url);
        if(url.indexOf('?')<0){
            sb.append('?');
        }
        boolean first=true;
        for(Object o: query.entrySet()){
            Map.Entry e =(Map.Entry )o;
            if(first){
                first=false;
            }else{sb.append('&');}
            sb.append(e.getKey()).append('=').append(e);
        }
        return sb.toString();
    }


    static TrustManager[] trustCerts = new TrustManager[]{new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return certs;
        }
    }};

    public class NullHostNameVerifier implements HostnameVerifier {

        public boolean verify(String arg0, SSLSession arg1) {
            System.out.println("----------");
            System.out.println(arg0);
            //if("tywloa.iot.ctc.com".equals(arg0))return false;
            System.out.println(arg1);
            return true;
        }
    }

    public Map getHeader(){
        return headers;
    }
}
