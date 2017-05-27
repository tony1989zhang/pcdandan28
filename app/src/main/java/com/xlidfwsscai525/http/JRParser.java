package com.xlidfwsscai525.http;

import com.xlidfwsscai525.bean.BaseBean;
import com.xlidfwsscai525.tools.XgoLog;
import com.google.gson.Gson;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Field;
import java.lang.reflect.Type;


/**
 * 作者：V先生 on 2016/8/1 16:26
 * 作用：结果解析
 */
public class JRParser implements ResponseParser {
    //检测服务端头部
    @Override
    public void checkResponse(UriRequest request) throws Throwable {
        XgoLog.e("checkResponse:" + request);
    }
    /**
     * 转换result 为resultType类型的数据
     * resultType 返回值类型
     * resultClass 返回值的类型
     * result 字符串类型
     * */
    @Override
    public Object parse(Type resultType, Class<?> resultClass1, String result) throws Throwable {
        Class<?> resultClass = resultClass1;
        XgoLog.e("class:" + resultClass + ","+"result:" + result);
        Gson gson = new Gson();
        Object o = null;
        try {
             o = gson.fromJson(result, resultClass1);
            return o;
        }catch (Exception e){
            BaseBean baseBean = gson.fromJson(result, BaseBean.class);
            Object o1 = resultClass.newInstance();
            Field resData = resultClass.getDeclaredField("resData");
            resData.setAccessible(true);
            resData.set(o1, null);
            Field errCode = null;
            Field resMsg = null;
              for (; resultClass != Object.class; resultClass = resultClass.getSuperclass())
              {
                  try {
                      errCode = resultClass.getDeclaredField("errCode");
                      resMsg = resultClass.getDeclaredField("resMsg");
                  }catch (Exception a)
                  {

                  }
              }

            errCode.setAccessible(true);
            errCode.set(o1, baseBean.errCode);

            errCode.setAccessible(true);
            resMsg.set(o1,baseBean.resMsg);
            return o1;
        }


    }
}
