package com.io.bitbrothers.starfish.activity;

import io.bitbrothers.starfish.R;
import android.os.Bundle;
import android.util.Log;

import com.io.bitbrothers.common.framework.ui.BaseGroupActivity;

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
