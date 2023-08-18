package io.shmilyhe.convert.tools;

/**
 * 
 */
public abstract class DEBUG {
    private static boolean isDebug;
    static {
        String db = System.getenv("DEBUG");
        if("true".equalsIgnoreCase(db))isDebug=true;
        System.out.println("isDebug:"+isDebug);
    }

    public static void debug(Object ...o){
        StringBuilder sb =new StringBuilder();
        sb.append("[DEBUG]:");
        for(Object a:o){
            sb.append(a);
        }
        System.out.println(sb.toString());
    }

}
