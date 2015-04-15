package com.open.young.soul.common.framework.logic;

import java.util.ArrayList;
import java.util.List;

import com.open.young.soul.common.util.BPLogger;

import android.os.Handler;
import android.os.Message;

/**
 * 业务层的logic类的父类，持有activity的handler，全局发布消息
 * @author Young
 *
 */
public class BPBaseLogic implements BPILogic
{
    private static final String TAG = BPBaseLogic.class.getSimpleName();
    
    private Handler myHandler = new MyHandler();
    
    private List<Handler> mHandlerList = new ArrayList<Handler>();

    @Override
    public void addHandler(Handler handler)
    {
        if (null != handler)
        {            
            mHandlerList.add(handler);
        }
        else
        {
            BPLogger.w(TAG, "addHandler and handler is null");
        }
    }

    @Override
    public void removeHandler(Handler handler)
    {
        if (null != handler)
        {            
            mHandlerList.remove(handler);
        }
        else
        {
            BPLogger.w(TAG, "removeHandler and handler is null");
        }

    }
    
    public void sendEmptyMessage(int what)
    {
        Message msg = new Message();
        msg.what = what;
        Message myMsg = myHandler.obtainMessage();
        myMsg.obj = msg;
        myHandler.sendMessage(myMsg);
    }
    
    public void sendMessage(Message msg)
    {
        Message myMsg = myHandler.obtainMessage();
        myMsg.obj = msg;
        myHandler.sendMessage(myMsg);
    }
    
    public void sendEmptyMessageDelayed(int what, long delayMillis)
    {
        Message msg = new Message();
        msg.what = what;
        Message myMsg = myHandler.obtainMessage();
        myMsg.obj = msg;
        myHandler.sendMessageDelayed(myMsg, delayMillis);
    }
    
    public void sendMessageDelayd(Message msg, long delayMillis)
    {
        Message myMsg = myHandler.obtainMessage();
        myMsg.obj = msg;
        myHandler.sendMessageDelayed(myMsg, delayMillis);
    }
    
    /**
     * 内部类，用于分发{@link BPBaseLogic#myHandler}}的消息
     * 因为消息可能需要延迟或者处理runnable，所以需要此Handler来处理
     * @author Young
     *
     */
    public class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            for(Handler handler : mHandlerList)
            {
                handler.sendMessage((Message) msg.obj);
            }
        }
    }

}
