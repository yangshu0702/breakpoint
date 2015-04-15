package com.open.young.soul.breakpoint.model;

import com.google.gson.annotations.SerializedName;

/**
 * ��������Ӧ�����п��ܴ��ڵ�extra��ݣ�����ʵ���Զ���¼���±��ؼ�Ȩ��Ϣ
 * @author Young
 *
 */
public class Extra
{
    @SerializedName("remember_token")
    private String rememberToken;
    
    @SerializedName("session_key")
    private String sessionKey;
    
    @SerializedName("user_id")
    private String userId;

    public String getRememberToken()
    {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken)
    {
        this.rememberToken = rememberToken;
    }

    public String getSessionKey()
    {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey)
    {
        this.sessionKey = sessionKey;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    
}
