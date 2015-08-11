package com.open.young.soul.common.core;

import java.util.Stack;

import android.app.Activity;

/**
 * App页面栈的管理类
 * @author yangshu
 *
 */
public class BPAppManager {
	private static BPAppManager sInstance;

	private Stack<Activity> activityStack;

	private BPAppManager() {
		activityStack = new Stack<Activity>();
	}

	public synchronized static BPAppManager getInstance() {
		if (null == sInstance) {
			sInstance = new BPAppManager();
		}
		return sInstance;
	}

	public void addActivity(Activity activity) {
		activityStack.add(activity);
	}

	public void finishActivity(Activity activity) {
		if (null != activity) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	public void finishAllActivity() {
		for (int i = 0; i < activityStack.size(); i++) {
			finishActivity(activityStack.get(i));
		}
		activityStack.removeAllElements();
	}

	public Activity currentActivity() {
		return activityStack.lastElement();
	}

}
