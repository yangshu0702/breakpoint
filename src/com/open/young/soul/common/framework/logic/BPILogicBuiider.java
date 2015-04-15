package com.open.young.soul.common.framework.logic;

import android.os.Handler;

public interface BPILogicBuiider
{
    void addHandlerToAllLogics(Handler handler);
    
    void removeHandlerFromAllLogics(Handler handler);
    
    BPILogic getLogicByClass(Class<?> paramClass);
}
