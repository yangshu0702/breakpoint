package com.io.bitbrothers.common.framework.logic;

import android.os.Handler;

public interface ILogicBuiider
{
    void addHandlerToAllLogics(Handler handler);
    
    void removeHandlerFromAllLogics(Handler handler);
    
    ILogic getLogicByClass(Class<?> paramClass);
}
