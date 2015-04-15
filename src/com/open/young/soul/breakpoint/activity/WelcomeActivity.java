package com.open.young.soul.breakpoint.activity;

import com.open.young.soul.R;
import com.open.young.soul.common.framework.ui.BaseGroupActivity;

import android.os.Bundle;
import android.util.Log;


/**
 * 欢迎页
 * 
 * @author Young
 * 
 */
public class WelcomeActivity extends BaseGroupActivity
{

    @Override
    protected void initLogics()
    {

    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("WelcomeActivity", "onCreate");
    }
}
