package com.xlidfwsscai525.bean;

/**
 * Created by 张灿能 on 2016/8/9.
 * 作用：
 */
public class BaseBean {
    public int errCode;
    public String resMsg;

    @Override
    public String toString() {
        return "BaseBean{" +
                "errCode=" + errCode +
                ", resMsg='" + resMsg + '\'' +
                '}';
    }
}
