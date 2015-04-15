package com.open.young.soul.common.framework.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.open.young.soul.common.framework.logic.BPBaseLogicBuilder;
import com.open.young.soul.common.framework.logic.BPILogic;

/**
 * FragmentActivity的基类，继承自{@link FragmentActivity}.
 * 
 * @author Young
 * 
 */
public abstract class BaseFragmentActivity extends FragmentActivity
{
    /**
     * 日志标签
     */
    private static final String TAG = BaseFragmentActivity.class
            .getSimpleName();

    /**
     * 用于接受消息的handler
     */
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        // 如果BaseLogicBuilder没有初始化完成，则无法进行其他工作，故抛出提示
        if (!BPBaseLogicBuilder.isInited())
        {
            throw new RuntimeException(
                    "activity onCreate must be after BaseLogicBuilder inited");
        }
        BPBaseLogicBuilder.getInstance().removeHandlerFromAllLogics(mHandler);

    }

    /**
     * 通过调用{@link BPBaseLogicBuilder#getLogicByClass(Class)} 获取logic实例 供activity持有
     * {@link BPILogic} 使用，通常在{@link BaseFragmentActivity#initLogics()}方法中获取实例
     * 
     * @param paramClass logic的类
     * @return {@link BPILogic} 实例
     */
    protected BPILogic getLogicByInterfaceClass(Class<?> paramClass)
    {
        return BPBaseLogicBuilder.getInstance().getLogicByClass(paramClass);
    }

    /**
     * 在页面销毁时，需要当前页面的handler移除，否则会持有引用导致内存得不到释放
     */
    @Override
    public void onDestroy()
    {
        removeHandler();
        super.onDestroy();
    }

    /**
     * 在页面结束时，需要当前页面的handler移除，否则会持有引用导致内存得不到释放
     */
    @Override
    public void finish()
    {
        removeHandler();
        super.finish();
    }

    /**
     * 为了解耦，故UI层不应该直接通过{@link BPBaseLogicBuilder}获取实例
     * 
     * @return {@link BPBaseLogicBuilder}实例
     */
    protected BPBaseLogicBuilder getLogicBuilder()
    {
        return BPBaseLogicBuilder.getInstance();
    }

    /**
     * 获取当前页面的handler，可以使用这个给自己发消息
     * 
     * @return {@link BaseFragmentActivity#mHandler}
     */
    protected Handler getHandler()
    {
        return mHandler;
    }

    /**
     * 将{@link BaseFragmentActivity#mHandler}从{@link BPBaseLogicBuilder}中移除
     */
    private void removeHandler()
    {
        if (null != BPBaseLogicBuilder.getInstance())
        {
            BPBaseLogicBuilder.getInstance().removeHandlerFromAllLogics(mHandler);
        }
    }

    /**
     * 子类复写此方法，用于获取{@link BPILogic} 实例
     */
    protected abstract void initLogics();

    /**
     * 子类Activity复写此方法，从而对全局消息进行处理。 注意：只需要处理自己关注的消息，其他忽略
     * 
     * @param msg
     */
    public void handStateMessage(Message msg)
    {

    }

    protected final void postRunnable(Runnable paramRunnable)
    {
        if (this.mHandler != null)
            this.mHandler.post(paramRunnable);
    }

    protected final void sendEmptyMessage(int paramInt)
    {
        if (this.mHandler != null)
            this.mHandler.sendEmptyMessage(paramInt);
    }

    protected final void sendEmptyMessageDelayed(int paramInt, long paramLong)
    {
        if (this.mHandler != null)
            this.mHandler.sendEmptyMessageDelayed(paramInt, paramLong);
    }

    protected final void sendMessage(Message paramMessage)
    {
        if (this.mHandler != null)
            this.mHandler.sendMessage(paramMessage);
    }

    protected final void sendMessageDelayed(Message paramMessage, long paramLong)
    {
        if (this.mHandler != null)
            this.mHandler.sendMessageDelayed(paramMessage, paramLong);
    }

    /*
     * UIhandler，用于全局接受消息
     */
    public class UIHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            handStateMessage(msg);
        }
    }
}
