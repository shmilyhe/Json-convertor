package io.shmilyhe.convert.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLutils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getHostFromUrl("http://snv.iteye.com/blog/1992991"));
		System.out.println(getHostFromUrl("https://snv.iteye.com/blog/1992991"));
		System.out.println(getHostFromUrl("http://snv.iteye.com:8080/blog/1992991"));
		System.out.println(getHostFromUrl("http://snv.iteye.com"));
		System.out.println(getHostFromUrl("http://snv.iteye.com/"));
		System.out.println(getHostFromUrl("http://snv.iteye.com:8999?qweqwe"));
		System.out.println(getHostFromUrl("https://snv.iteye.com:8999/"));
	}
	private static Pattern p = Pattern.compile("http[s]?://([^/:]*).*");
	
	/**
	 * 从URL中获取host
	 *
	 * @param url URL
	 * @return HOST
	 */
	public static String getHostFromUrl(String url) {
		if(url==null)return null;
		if(!url.startsWith("http"))return null;
		Matcher m=p.matcher(url);
		if(m.matches()) {
			return m.group(1);
		}
		return null;
	}

}
