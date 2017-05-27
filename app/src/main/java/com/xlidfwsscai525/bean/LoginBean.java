package com.xlidfwsscai525.bean;

import com.xlidfwsscai525.entity.LoginEntity;
import com.xlidfwsscai525.http.JRParser;

import org.xutils.http.annotation.HttpResponse;


/**
 * 作者：V先生 on 2016/8/1 17:30
 * 作用：基类
 */
@HttpResponse(parser = JRParser.class)
public class LoginBean extends BaseBean{
    public LoginEntity resData;

    @Override
    public String toString() {
        return "LoginBean_parser{" +
                "errCode=" + errCode +
                ", resMsg='" + resMsg + '\'' +
                ", resData=" + resData +
                '}';
    }
}
