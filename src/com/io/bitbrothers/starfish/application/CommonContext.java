package com.io.bitbrothers.starfish.application;

import io.bitbrothers.starfish.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.io.bitbrothers.common.util.Logger;

import android.content.Context;
import android.util.Log;

/**
 * 当前应用的上下文，用于保存一些和application无关但和业务有关的常量或者变量
 * 
 * @author Young
 * 
 */
public class CommonContext
{
    /**
     * 日志标签
     */
    private static final String TAG = CommonContext.class.getSimpleName();

    private static CommonContext sInstance;

    /**
     * 是否初始化完成
     */
    private static boolean mHasInited = false;

    /**
     * 服务器地址
     */
    public static String mServerUrl = "";

    /**
     * 是否打印日志到SD卡
     */
    public static boolean mIsLogToSD = true;

    /**
     * 是否打印日志到DDMS
     */
    public static boolean mIsLogToDDMS = true;
    
    /**
     * 是否打印日志到DDMS
     */
    public static String mLogPath = "";

    /**
     * 单例模式的构造方法，需要设为私有
     */
    private CommonContext()
    {

    }

    /**
     * 单例模式下获取静态实例
     * 
     * @return 静态实例
     */
    public synchronized static CommonContext getInstance()
    {
        if (null == sInstance)
        {
            sInstance = new CommonContext();
        }
        return sInstance;
    }

    public static void init(Context context)
    {
        if (mHasInited)
        {
            return;
        }
        //读取配置文件中的信息
        Properties properties = new Properties();
        try
        {
            properties.load(context.getResources().openRawResource(R.raw.system));
            mServerUrl = properties.getProperty("server_host_url", "");
            mIsLogToSD = Boolean.getBoolean(properties.getProperty(
                    "is_write_sd_log", "true"));
            mIsLogToDDMS = Boolean.getBoolean(properties.getProperty(
                    "is_write_ddms_log", "true"));
            mLogPath = properties.getProperty("log_path", "/Starfish/log/");
            Log.i(TAG, "init() getProperty mServerUrl = " + mServerUrl
                    + " mIsLogToSD = " + mIsLogToSD + " mIsLogToDDMS = "
                    + mIsLogToDDMS + " log_path = " + mLogPath);
        }
        catch (FileNotFoundException e)
        {
            Log.e(TAG, "init() happens FileNotFoundException e = " + e);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.e(TAG, "init() happens IOException e = " + e);
            e.printStackTrace();
        }
    }
}
