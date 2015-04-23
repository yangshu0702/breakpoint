package com.open.young.soul.common.util;

import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

/**
 * Android设备的工具类，比如获取Android_ID，deviceID，Macaddress等
 * @author breakpoint
 *
 */
public class BPDevice {
	
	/**
	 * 获取deviceID,只有现实设备才可以获取到这个值，模拟器没有该值
	 * 它会根据不同的手机设备返回IMEI，MEID或者ESN码，但在使用的过程中有以下问题：
	 * 需要READ_PHONE_STATE权限
	 * @param context 上下文
	 * @return 设备的唯一标示
	 */
	public static String getDeviceId(Context context)
	{
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = manager.getDeviceId();
		if (BPCommonUtils.isNullOrEmptyWithTrim(deviceID))
		{
			return "";
		}
		return deviceID;
	}
	
	/**
	 * 获取Android_ID，wipe时会重新生成，可以作为唯一标示
	 * @param context 上下文
	 * @return Android_id
	 */
	public static String getAndroidId(Context context)
	{
		String androidID = android.provider.Settings.System.getString(
				context.getContentResolver(), Secure.ANDROID_ID);
		if (BPCommonUtils.isNullOrEmptyWithTrim(androidID))
		{
			return "";
		}
		return androidID;
	}

}
