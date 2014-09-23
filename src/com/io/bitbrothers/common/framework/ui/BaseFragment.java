package com.io.bitbrothers.common.framework.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.io.bitbrothers.common.framework.logic.BaseLogicBuilder;
import com.io.bitbrothers.common.framework.logic.ILogic;

/**
 * Fragment的基类，继承自{@link Fragment}.
 * 
 * @author Young
 * 
 */
public abstract class BaseFragment extends Fragment
{
    /**
     * 日志标签
     */
    private static final String TAG = BaseFragment.class.getSimpleName();

    /**
     * 用于接受消息的handler
     */
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreateView()");
        // 如果BaseLogicBuilder没有初始化完成，则无法进行其他工作，故抛出提示
        if (!BaseLogicBuilder.isInited())
        {
            throw new RuntimeException(
                    "Fragment onCreate must be after BaseLogicBuilder inited");
        }
        BaseLogicBuilder.getInstance().removeHandlerFromAllLogics(mHandler);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 通过调用{@link BaseLogicBuilder#getLogicByClass(Class)} 获取logic实例 供activity持有
     * {@link ILogic} 使用，通常在{@link BaseFragment#initLogics()}方法中获取实例
     * 
     * @param paramClass logic的类
     * @return {@link ILogic} 实例
     */
    protected ILogic getLogicByInterfaceClass(Class<?> paramClass)
    {
        return BaseLogicBuilder.getInstance().getLogicByClass(paramClass);
    }

    /**
     * 在页面销毁时，需要当前页面的handler移除，否则会持有引用导致内存得不到释放
     */
    public void onDestroy()
    {
        removeHandler();
        super.onDestroy();
    }

    /**
     * 为了解耦，故UI层不应该直接通过{@link BaseLogicBuilder}获取实例
     * 
     * @return {@link BaseLogicBuilder}实例
     */
    protected BaseLogicBuilder getLogicBuilder()
    {
        return BaseLogicBuilder.getInstance();
    }

    /**
     * 获取当前页面的handler，可以使用这个给自己发消息
     * 
     * @return {@link BaseFragment#mHandler}
     */
    protected Handler getHandler()
    {
        return mHandler;
    }

    /**
     * 将{@link BaseFragment#mHandler}从{@link BaseLogicBuilder}中移除
     */
    private void removeHandler()
    {
        if (null != BaseLogicBuilder.getInstance())
        {
            BaseLogicBuilder.getInstance().removeHandlerFromAllLogics(mHandler);
        }
    }

    /**
     * 子类复写此方法，用于获取{@link ILogic} 实例
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
