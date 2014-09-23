package com.io.bitbrothers.starfish.model;

import com.google.gson.annotations.SerializedName;

/**
 * 服务器返回的父类，包含必要信息
 * @author Young
 *
 */
public class CommonResp
{
    /**
     * 错误码
     */
    @SerializedName("errcode")
    private int errcode;
    
    /**
     * 错误信息
     */
    @SerializedName("errmsg")
    private String errmsg;
    
    /**
     * 可能存在的自动登录数据，只有服务器完成自动登录才返回
     */
    @SerializedName("extra")
    private Extra extra;

    public int getErrcode()
    {
        return errcode;
    }

    public void setErrcode(int errcode)
    {
        this.errcode = errcode;
    }

    public String getErrmsg()
    {
        return errmsg;
    }

    public void setErrmsg(String errmsg)
    {
        this.errmsg = errmsg;
    }

    public Extra getExtra()
    {
        return extra;
    }

    public void setExtra(Extra extra)
    {
        this.extra = extra;
    }
    
    
}
