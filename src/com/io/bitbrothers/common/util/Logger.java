package com.io.bitbrothers.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

import android.util.Log;

/**
 * 通用日志类，代替系统日志类{@link Log}
 * 但最终调用的还是系统日志类，只是进行了一些筛选打印已经决定是否打印在SD卡中
 * @author Young
 *
 */
public class Logger
{
    /**
     * 是否在SD卡中打印日志，默认不打印
     */
    private static boolean isSDLog = false;
    
    /**
     * 是否在DDMS中打印，默认打印
     */
    private static boolean isDDMSLog = true;
    
    /**
     * 打印到SD卡的路径，默认为
     */
    private static String logDirPath = "";
    
    /**
     * 日志级别，大于等于这个级别的日志才会被打印，默认打印warn以上的日志
     */
    private static int level = Log.VERBOSE;
    
    private static String fileName = getLogFileName() + ".log";
    
    private static boolean isInited = false;
    
    /**
     * 初始化方法
     * @param isSDLog 是否在SD卡中打印日志
     * @param isDDMSLog 是否在DDMS中打印日志
     * @param logDirPath 打印到SD卡中的路径
     * @param level 日志级别，大于等于该级别才会被打印{@link Log#VERBOSE}等}
     */
    public static void init(boolean isSDLog, boolean isDDMSLog, String logDirPath, int level)
    {
        if (isInited)
        {
            return;
        }
        Logger.isSDLog = isSDLog;
        Logger.isDDMSLog = isDDMSLog;
        Logger.logDirPath = logDirPath;
        Logger.level = level;
        File file = new File(logDirPath);
        if (!file.exists())
        {
            file.mkdir();
        }
        //保留最近5个日志，否则太占用SD卡
        File[] array = file.listFiles();        
        ArrayList<File> deleteList = new ArrayList<>();
        if (null != array && array.length >= 5)
        {
            ArrayList<File> list = new ArrayList<>();
            for (File temp : array)
            {
                list.add(temp);
            }
            Collections.sort(list, new Comparator<File>()
            {

                @Override
                public int compare(File lhs, File rhs)
                {
                    if (null == lhs)
                    {
                        return -1;
                    }
                    return lhs.lastModified() > rhs.lastModified()? -1 : 1;
                }
                
            });
            deleteList.addAll(list.subList(5-1, list.size()));
            for (File temp2 : deleteList)
            {
                temp2.delete();
            }
            deleteList.clear();
            list.clear();
        }
        isInited = true;
    }
    
    public static int d(String tag, String msg)
    {
        if (level > Log.DEBUG || !isDDMSLog)
        {
            return -1;
        }
        write2File(Log.DEBUG, tag, msg);
        return Log.d(tag, msg);
    }
    
    public static int d(String tag, String msg, Throwable throwable)
    {
        if (level > Log.DEBUG || !isDDMSLog)
        {
            return -1;
        }
        write2File(Log.DEBUG, tag, msg + " throwable = " + Log.getStackTraceString(throwable));
        return Log.d(tag, msg, throwable);
    }
    
    public static int e(String tag, String msg)
    {
        if (level > Log.ERROR || !isDDMSLog)
        {
            return -1;
        }
        write2File(Log.ERROR, tag, msg);
        return Log.e(tag, msg);
    }
    
    public static int e(String tag, String msg, Throwable throwable)
    {
        if (level > Log.ERROR || !isDDMSLog)
        {
            return -1;
        }
        write2File(Log.ERROR, tag, msg + " throwable = " + Log.getStackTraceString(throwable));
        return Log.e(tag, msg, throwable);
    }
    
    public static int i(String tag, String msg)
    {
        if (level > Log.INFO || !isDDMSLog)
        {
            return -1;
        }
        write2File(Log.INFO, tag, msg);
        return Log.i(tag, msg);
    }
    
    public static int i(String tag, String msg, Throwable throwable)
    {
        if (level > Log.INFO || !isDDMSLog)
        {
            return -1;
        }
        write2File(Log.INFO, tag, msg + " throwable = " + Log.getStackTraceString(throwable));
        return Log.i(tag, msg, throwable);
    }
    
    public static int w(String tag, String msg)
    {
        if (level > Log.WARN || !isDDMSLog)
        {
            return -1;
        }
        write2File(Log.WARN, tag, msg);
        return Log.w(tag, msg);
    }
    
    public static int w(String tag, String msg, Throwable throwable)
    {
        if (level > Log.WARN || !isDDMSLog)
        {
            return -1;
        }
        write2File(Log.WARN, tag, msg + " throwable = " + Log.getStackTraceString(throwable));
        return Log.w(tag, msg, throwable);
    }
    
    public static int v(String tag, String msg)
    {
        if (level > Log.VERBOSE || !isDDMSLog)
        {
            return -1;
        }
        write2File(Log.VERBOSE, tag, msg);
        return Log.w(tag, msg);
    }
    
    public static int v(String tag, String msg, Throwable throwable)
    {
        if (level > Log.VERBOSE || !isDDMSLog)
        {
            return -1;
        }
        write2File(Log.VERBOSE, tag, msg + " throwable = " + Log.getStackTraceString(throwable));
        return Log.v(tag, msg, throwable);
    }
    
    private static void write2File(int level, String tag, String msg)
    {
        if (isSDLog)
        {            
            Log2File.getInstance().write(logDirPath + fileName, levelString(level), tag, msg);
        }
    }
    
    private static String getLogFileName()
    {
        GregorianCalendar CALENDAR_INSTANCE = new GregorianCalendar();
        CALENDAR_INSTANCE.setTimeInMillis(System.currentTimeMillis());
        int j = 1 + CALENDAR_INSTANCE.get(2);
        int k = CALENDAR_INSTANCE.get(5);
        int m = CALENDAR_INSTANCE.get(11);
        int n = CALENDAR_INSTANCE.get(12);
        int i1 = CALENDAR_INSTANCE.get(13);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(j).append('-').append(k).append(' ');
        localStringBuilder.append(m).append(':').append(n).append(':').append(i1);
        return localStringBuilder.toString();
    }
    
    private static String levelString(int paramInt)
    {
      String str;
      switch (paramInt)
      {
      case 2:
          str = "V";
          break;
      case 3:
          str = "D";
          break;
      case 4:
          str = "I";
          break;
      case 5:
          str = "W";
          break;
      case 6:
          str = "E";
          break;
      default:
          str = "D";
    }
      return str;
    }
}
