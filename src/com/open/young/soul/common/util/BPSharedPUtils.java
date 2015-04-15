package com.open.young.soul.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BPSharedPUtils {
	private static final String SP_NAME = "bp_sp_storage";
	
	public static String getString(Context context, String key, String defValue)
	{
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}
	
	public static void putString(Context context, String key, String value)
	{
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static boolean getBoolean(Context context, String key, boolean defValue)
	{
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}
	
	public static void putBoolean(Context context, String key, boolean value)
	{
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static int getInt(Context context, String key, int defValue)
	{
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getInt(key, defValue);
	}
	
	public static void putInt(Context context, String key, int value)
	{
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static float getFloat(Context context, String key, float defValue)
	{
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getFloat(key, defValue);
	}
	
	public static void putFloat(Context context, String key, float value)
	{
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

    public static boolean contains(Context context, String key)  
    {  
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,  
                Context.MODE_PRIVATE);  
        return sp.contains(key);  
    }  
    
	public static void remove(Context context, String key)
	{
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}
	
	public static void clear(Context context)
	{
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}
}
