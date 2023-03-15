package io.shmilyhe.tools;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
/**
 * 获取真正的客户端IP
 * @author eric
 *
 */

public class ClientIp {
	
	/**
	 * 获取真正的客户端IP
	 * @param request request
	 * @return host
	 */
	public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if(ip!=null){
        	if(ip.indexOf(',')>-1){
        		String[] ips =ip.split(",");
        		ip=ips[0];
        	}
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
	public static int ipv4toint(String ip) {
	    if (ip==null||ip.length()<4) {
	        return -1;
	    }
	    String[] ipArr = ip.split("\\.");
	    if (ipArr.length != 4) {
	        return -1;
	    }
	    int x = 0;
	    for (int i = 0; i < ipArr.length; i++) {
	        x = ((x << 8) | Integer.valueOf(ipArr[i]));
	    }
	    return x;
	}
	
	public static String inttoipv4(int ip) {
	    String[] ipArr = new String[4];
	    for (int i = 0; i < ipArr.length; i++) {
	        int n = (ip >>> (8 * i)) & (0xFF);
	        ipArr[3 - i] = String.valueOf(n);
	    }
	    return String.join(".", ipArr);
	}
	
	/**
	* 将 IPv6 地址转为 long 数组，只支持冒分十六进制表示法
	 */
	/**
	 * 
	 * @param ipString ip
	 * @return 十六进制表示法
	 */
	public static long[] ip2Longs(String ipString) {
	 	if (ipString == null || ipString.isEmpty()) {
	        throw new IllegalArgumentException("ipString cannot be null.");
	    }
	    String[] ipSlices = ipString.split(":");
	    if (ipSlices.length != 8) {
	        throw new IllegalArgumentException(ipString + " is not an ipv6 address.");
	    }
	    long[] ipv6 = new long[2];
	    for (int i = 0; i < 8; i++) {
	        String slice = ipSlices[i];
	        // 以 16 进制解析
	        long num = Long.parseLong(slice, 16);
	        // 每组 16 位
	        long right = num << (16 * i);
	        // 每个 long 保存四组，i >> 2 = i / 4
	        ipv6[i >> 2] |= right;
	    }
	    return ipv6;
	}
	
	/**
     * 将 long 数组转为冒分十六进制表示法的 IPv6 地址
     */
	/**
	 * 
	 * @param numbers numbers
	 * @return ip
	 */
    public static String longs2Ip(long[] numbers) {
        if (numbers == null || numbers.length != 2) {
            throw new IllegalArgumentException(Arrays.toString(numbers) + " is not an IPv6 address.");
        }
        StringBuilder sb = new StringBuilder(32);
        for (long numSlice : numbers) {
            // 每个 long 保存四组
            for (int j = 0; j < 4; j++) {
                // 取最后 16 位
                long current = numSlice & 0xFFFF;
                sb.append(Long.toString(current, 16)).append(":");
                // 右移 16 位，即去除掉已经处理过的 16 位
                numSlice >>= 16;
            }
        }
        // 去掉最后的 :
        return sb.substring(0, sb.length() - 1);
    }

}
