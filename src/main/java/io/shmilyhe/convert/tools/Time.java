package io.shmilyhe.convert.tools;

public class Time {
	
	
	public static int getUnixTimestamp() {
		return (int) (System.currentTimeMillis() / 1000);
	}

}
