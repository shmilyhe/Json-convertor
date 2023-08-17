package io.shmilyhe.convert.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 读取文件
 */
public class ResourceReader {
    static Map<String,String> res = new HashMap<String,String> ();

    //file:/Users/yolinzhong/code/nhsc/nhdhCluster/nhdh-test/target/nhdh-test-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/
    //Users/yolinzhong/code/nhsc/nhdhCluster/nhdh-test/target/classes/
    //Users/yolinzhong/code/nhsc/nhdhCluster


    public static String read(String file){
        //String cfoder = ResourceReader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String text = res.get(file);
        if(text!=null)return text;
        InputStream in =asInputStream(file);// //ResourceReader.class.getClassLoader().getResourceAsStream(file);
        byte[] bs =asBytes(in);
        if(bs!=null){
            text=new String(bs);
        }
        res.put(file, text);
        return text;
    }

    protected static byte[] asBytes(InputStream in){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len=0;
        try{
            while((len=in.read(b))>0){
                bos.write(b, 0, len);
            }
            return bos.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                in.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static String [] loadPath= new String[]{
        System.getProperty("user.dir"),
        getCodeFoder(),
        getCodeFoder()+"classes"
    };
    protected static InputStream asInputStream(String file){
        for(String base:loadPath){
            File f = new File(base,file);
            //log.info("test:{}",f.getAbsolutePath());
            if(f.exists()&&f.isFile()){
                
                try {
                    return new FileInputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return ResourceReader.class.getClassLoader().getResourceAsStream(file);
    }

    public static byte[] asBytes(File f){
        if(f==null||!f.exists()||f.isDirectory())return null;
        try {
            FileInputStream in = new FileInputStream(f);
            return asBytes(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public static String asString(File f){
        byte[] bs = asBytes(f);
        return bs==null?null:new String(bs);
    }

    public static List<File> lsFiles(String path){
        List<File> fl = new ArrayList<>();
        for(int i=loadPath.length-1;i>= 0;i--){
            File f = new File(loadPath[i],path);
            if(f.exists()&&f.isDirectory()){
               fl.addAll(Arrays.asList(f.listFiles(e->{ return e.isFile();})));
            }
        }
        return fl;
    }


    protected static String getCodeFoder(){
        String cf = ResourceReader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        //String cf ="file:/Users/yolinzhong/code/nhsc/nhdhCluster/nhdh-test/target/nhdh-test-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/";
        if(cf.startsWith("file:")){
            cf=cf.substring(0, cf.indexOf('!'));
            cf=cf.substring(0, cf.lastIndexOf('/'));
            cf=cf.substring(5);
            cf+="/";
        }
        return cf;
    }

    public static String getResource(String path,Map<String,String> param){
        String res =read(path);
        if(res==null) return null;
        ReplaceString rs = new ReplaceString(res);
        param.entrySet().stream().forEach(e->{
            rs.replace(e.getKey(),e.getValue());
        }); 
        return rs.toString();
    }

    public static void main(String[] args){
        HashMap<String,String> param = new HashMap<String,String>();
        param.put("suid", "132.122.78.6");
        String xml = getResource("soap/sc_login_ack.xml",param);
        System.out.println(xml);
        System.out.println(getCodeFoder());
        System.out.println(System.getProperty("user.dir"));
    }

}
class ReplaceString{
    static HashMap<String,String>names = new HashMap<String,String>();

    String string;
    public ReplaceString(String str){
        string=str;
    }

    public void replace(String k,String v){
        String kn = names.get(k);
        if(kn==null){
            kn="#\\{"+k+"\\}";
            names.put(k,kn);
        }
        string=string.replaceAll(kn,v);
    }
    public String toString(){
        return string;
    }
}

