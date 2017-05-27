package com.xlidfwsscai525.http;

import com.xlidfwsscai525.tools.XgoLog;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;

/**
 * 作者：V先生 on 2016/7/30 16:40
 * 作用：网络请求返回结果
 */
public abstract class ComCb<ResultType> implements CommonCallback<ResultType> {
     String errStr = "";
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        errStr = "";
        XgoLog.e("ex_onError:" + ex.toString() + "isOnCallBack:" + isOnCallback);
        if (ex instanceof HttpException){
            //网络错误
            HttpException httpEx = (HttpException)ex;
            int code = httpEx.getCode();
            String msg = httpEx.getMessage();
            String result = httpEx.getResult();
            errStr = "错误码：" + code + "解析:" + msg;
        }else if(isOnCallback){
            errStr = "网络错误";
        }else{
            errStr = "数据处理出错";
        }
        XgoLog.e("errStr:" + errStr);
    }
    @Override
    public void onCancelled(CancelledException cex) {
        XgoLog.e("ex_onError:" + cex.toString());
    }
    @Override
    public void onFinished() {
        //ToastManager.getManager().show(errStr);
    }

    @Override
    public void onSuccess(ResultType result) {
         XgoLog.e("ResultType-result:" + result);
    }
}
