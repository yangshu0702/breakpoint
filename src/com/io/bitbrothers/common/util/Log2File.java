package com.io.bitbrothers.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Process;
import android.util.Log;

public class Log2File
{
    private static final String TAG = "Log2File";
    
    private static final GregorianCalendar CALENDAR_INSTANCE = new GregorianCalendar();
    
    private static ExecutorService executor;
    
    private static Log2File instance;
    
    private Log2File()
    {
        
    }
    
    public static Log2File getInstance()
    {
        if (null == instance)
        {
            instance = new Log2File();
        }
        return instance;
    }
    
    /**
     * 获取日志打印路径对应的文件
     * @param path 路径
     * @return 对应的文件
     */
    private static File getLogFile(String path)
    {
        File file = null;
        if(null == path && !"".equals(path))
        {
            Log.w(TAG, "getLogFile() path is null or empty");
            return null;
        }
        file = new File(path);
        if (!file.exists())
        {
            try
            {
                file.setReadable(true);
                file.setExecutable(true);
                file.setWritable(true);
                file.createNewFile();
            }
            catch (IOException e)
            {
                Log.w(TAG, "getLogFile() create file failed e = " + String.valueOf(e));
            }
        }
        return file;
        
    }
    
    private static void log2File(final String path, final String logStr)
    {
        if (null == executor)
        {
            executor = Executors.newSingleThreadExecutor();
        }
        if (null != executor)
        {
            executor.execute(new Runnable() {
                
                @Override
                public void run()
                {
                    PrintWriter writer = null;
                    File file = getLogFile(path);
                    try
                    {
                        writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                        writer.print(logStr + System.getProperty("line.separator"));
                        writer.flush();
                    }
                    catch(Exception e)
                    {
                        Log.e(TAG, String.valueOf(e));
                    }
                    finally
                    {
                        if (null != writer)
                        {                            
                            writer.close();
                        }
                    }
                }
            });
        }
    }
    
    /**
     * 外部调用的方法，用于写日志到文件中
     * @param path 路径
     * @param level 级别
     * @param tag 日志标签
     * @param logStr 日志内容
     */
    public void write(String path, String level, String tag, String logStr)
    {
        log2File(path, getLogString(level, tag, logStr));
    }
    
    private String getLogString(String paramString1, String paramString2, String paramString3)
    {
      CALENDAR_INSTANCE.setTimeInMillis(System.currentTimeMillis());
      int i = Process.myPid();
      int j = 1 + CALENDAR_INSTANCE.get(2);
      int k = CALENDAR_INSTANCE.get(5);
      int m = CALENDAR_INSTANCE.get(11);
      int n = CALENDAR_INSTANCE.get(12);
      int i1 = CALENDAR_INSTANCE.get(13);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(j).append('-').append(k).append(' ');
      localStringBuilder.append(m).append(':').append(n).append(':').append(i1);
      localStringBuilder.append('\t').append(paramString1).append('\t').append(i);
      localStringBuilder.append('\t').append('[').append(Thread.currentThread().getName()).append(']');
      localStringBuilder.append('\t').append(paramString2).append('\t').append(paramString3);
      return localStringBuilder.toString();
    }
}
