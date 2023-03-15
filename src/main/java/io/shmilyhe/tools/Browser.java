package io.shmilyhe.tools;

import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 浏览器判断
 * @author eshore
 *
 */

public class Browser {
	static final String SET_COOKIE="Set-Cookie";

	public static boolean isChrome(String userAgent) {
		return check(userAgent,"Chrome");
	}
	
	/**
	 * 判断是否chrome/80版本以上，因为这个版本的cookie 默认设置禁止第三方cookies
	 * 
	 * @param userAgent userAgent
	 * @return 是否chrome/80版本以上
	 */
	public static boolean aboveChrome80(String userAgent) {
		int begin=userAgent.indexOf("Chrome");
		if(begin<0)return false;
		String version=userAgent.substring(begin+7);
		begin=version.indexOf('.');
		if(begin>-1)version=version.substring(0,begin);
		try {
			return Integer.parseInt(version)>=80;
		}catch(Exception e) {}
		return false;
	}
	
	public static void setCookiesWithSamsite(HttpServletResponse resp) {
		
	}
	public static void addSameSiteCookieAttribute(HttpServletResponse response) {
        Collection<String> headers = response.getHeaders(SET_COOKIE);
        boolean firstHeader = true;
        //Strict
        // there can be multiple Set-Cookie attributes
        for (String header : headers) {
            if (firstHeader) {
                response.setHeader(SET_COOKIE, String.format("%s; %s", header, "SameSite=None; Secure"));
                firstHeader = false;
                continue;
            }
            response.addHeader(SET_COOKIE, String.format("%s; %s", header, "SameSite=None; Secure"));
        }
    }
	
	
	private static boolean check(String src,String check) {
		if(src==null)return false;
		return src.indexOf(check)>-1;
	}
	
	public static void main(String[] args) {
		String ag="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36";
		System.out.println(aboveChrome80(ag));
	}
	
}
