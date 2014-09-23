package com.io.bitbrothers.starfish.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import android.content.Context;

/**
 * 当前应用的上下文，用于保存一些和application无关但和业务有关的常量或者变量
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
    private String mServerUrl = "";
    
    /**
     * 是否打印日志到SD卡
     */
    private boolean mIsLogToSD = true;

    /**
     * 是否打印日志到DDMS
     */
    private boolean mIsLogToDDMS = true;
    
    /**
     * 单例模式的构造方法，需要设为私有
     */
    private CommonContext()
    {
        
    }
    
    /**
     * 单例模式下获取静态实例
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
        Properties properties = new Properties();
        try
        {
            properties.load(context.openFileInput("system.properties"));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
