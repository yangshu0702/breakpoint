package com.io.bitbrothers.common.framework.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * logic的构建类，使用map保存所以logic类的实例（单例），外界可以通过Class来获取logic实例
 * @author Young
 * 
 */
public class BaseLogicBuilder implements ILogicBuiider
{
    /**
     * 日志标签
     */
    private static final String TAG = "BaseLogicBuilder";

    /**
     * 单例
     */
    private static BaseLogicBuilder sInstance;

    /**
     * map容器，保存logic类实例
     */
    private Map<String, ILogic> mLogicCache = new HashMap<String, ILogic>();

    /**
     * 自身持有的handler，目前没有在此对消息接受进行处理
     */
    private Handler myHandler = new MyHandler();

    private BaseLogicBuilder()
    {

    }

    /**
     * 需要将上层业务需要的所有logic类注册进来
     * {@link BaseLogicBuilder#registerLogic(Class, ILogic)}
     * 
     * @param array 所以logic组成的数组，进行注册
     */
    public static void init(ILogic[] array)
    {
        if (null == array)
        {
            Log.w(TAG, "init but array is null");
            return;
        }
        if (null != sInstance)
        {
            Log.w(TAG, "BaseLogicBuilder has inited");
            return;
        }
        sInstance = new BaseLogicBuilder();
        for (ILogic logic : array)
        {
            try
            {
                sInstance.registerLogic(logic.getClass(), logic.getClass()
                        .newInstance());
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        sInstance.initAllLogics();
    };

    /**
     * 将myHandler添加到各个logic的handlerlist中
     */
    protected void initAllLogics()
    {
        Iterator<Entry<String, ILogic>> iterator = mLogicCache.entrySet()
                .iterator();
        while (iterator.hasNext())
        {
            ((ILogic) iterator.next().getValue()).addHandler(myHandler);
        }
    }

    public static BaseLogicBuilder getInstance()
    {
        if (null == sInstance)
        {
            throw new NullPointerException(
                    "BaseLogicBuilder getInstance() must be used after inited");
        }
        return sInstance;
    }

    /**
     * 是否已经初始化完成，如果没有实例的话，UI层不可以进行业务流程的处理 故需要在应用启动时进行初始化
     * 
     * @return 是否完成，true 完成
     */
    public static boolean isInited()
    {
        return null != sInstance;
    }

    /**
     * 注册logic，根据className作为key,根据class实例作为value，并且是单例
     * 
     * @param paramClass class参数
     * @param logic 实例对象
     */
    public void registerLogic(Class<?> paramClass, ILogic logic)
    {
        if (!mLogicCache.containsKey(paramClass.getSimpleName()))
        {
            mLogicCache.put(paramClass.getSimpleName(), logic);
            Log.i(TAG, "registerLogic " + paramClass.getSimpleName()
                    + "successfully");
        }
        else
        {
            Log.w(TAG, "registerLogic but " + paramClass.getSimpleName()
                    + " has been registerd");
        }
    }

    @Override
    public void addHandlerToAllLogics(Handler handler)
    {
        Iterator<Entry<String, ILogic>> iterator = mLogicCache.entrySet()
                .iterator();
        while (iterator.hasNext())
        {
            ((ILogic) iterator.next().getValue()).addHandler(handler);
        }
    }

    @Override
    public void removeHandlerFromAllLogics(Handler handler)
    {
        Iterator<Entry<String, ILogic>> iterator = mLogicCache.entrySet()
                .iterator();
        while (iterator.hasNext())
        {
            ((ILogic) iterator.next().getValue()).removeHandler(handler);
        }

    }

    @Override
    public ILogic getLogicByClass(Class<?> paramClass)
    {
        return mLogicCache.get(paramClass.getSimpleName());
    }

    /**
     * 预留的handler，可以让BaseLogicBuilder收到消息
     * 
     * @author Young
     * 
     */
    public class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            handleStateMessage(msg);
        }
    }

    /**
     * 可以捕获到所以发送的消息，但是暂时不作处理，看业务需要
     * 
     * @param msg
     */
    public void handleStateMessage(Message msg)
    {

    }

}
