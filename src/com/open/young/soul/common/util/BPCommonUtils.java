package com.open.young.soul.common.util;

public class BPCommonUtils {
	public static boolean isNullOrEmpty(String source)
	{
		return null == source || "".equals(source);
	}
	
	public static boolean isNullOrEmptyWithTrim(String source)
	{
		return null == source || "".equals(source.trim());
	}
}
