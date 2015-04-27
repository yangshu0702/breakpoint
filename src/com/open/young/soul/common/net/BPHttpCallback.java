package com.open.young.soul.common.net;

public interface BPHttpCallback {
	public void onError(int errorCode);
	public void onResponse(String response);
}
