package com.io.bitbrothers.starfish.model;

import com.google.gson.annotations.SerializedName;

/**
 * 服务器响应报文中可能存在的extra数据，用于实现自动登录更新本地鉴权信息
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
