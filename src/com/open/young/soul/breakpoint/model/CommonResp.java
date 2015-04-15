package com.open.young.soul.breakpoint.model;

import com.google.gson.annotations.SerializedName;

/**
 * ���������صĸ��࣬���Ҫ��Ϣ
 * @author Young
 *
 */
public class CommonResp
{
    /**
     * ������
     */
    @SerializedName("errcode")
    private int errcode;
    
    /**
     * ������Ϣ
     */
    @SerializedName("errmsg")
    private String errmsg;
    
    /**
     * ���ܴ��ڵ��Զ���¼��ݣ�ֻ�з���������Զ���¼�ŷ���
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
