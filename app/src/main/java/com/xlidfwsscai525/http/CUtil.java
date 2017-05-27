package com.xlidfwsscai525.http;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.tools.XgoLog;

import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.Map;

/**
 * 作者：V先生 on 2016/8/1 16:05
 * 作用：网络请求工具类
 */
public class CUtil {
//    private static final String SERVER_URL = "192.168.1.66/api.php/main/";
    private static final String SERVER_URL = "m.1396mp.com/api/pk10/history?date=";
    private static CM mCM;
    private static Drawable drawableResult;
    private static ImageOptions imageOptions;

    //get方法
    public static<T>Cancelable Get(String path, Map<String,String> map, CommonCallback<T> callback){
        RequestParams params = new RequestParams(getUrl(path));
        return getCancelable(map, callback, params);
    }

    public static<T>Cancelable Get2(String path, Map<String,String> map, CommonCallback<T> callback){
        RequestParams params = new RequestParams(path);
        return getCancelable(map, callback, params);
    }


    private static <T> Cancelable getCancelable(Map<String, String> map, CommonCallback<T> callback, RequestParams params) {
        if (null != map)
        {
            for (Map.Entry<String,String> entry:map.entrySet()){
                params.addQueryStringParameter(entry.getKey(),entry.getValue());
            }
        }
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }


    public static <T>Cancelable Post(String path, Map<String,Object> map, CommonCallback<T> callback){
        XgoLog.e("map:" + map);
        RequestParams params = new RequestParams(getUrl(path));
        if (null != map)
        {
            for (Map.Entry<String,Object> entry:map.entrySet())
            {
               params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    public static <T>Cancelable UpLoadFile(String path,Map<String,Object> map,CommonCallback<T> callback){
        RequestParams params=new RequestParams(getUrl(path));
        if(null!=map){
            for(Map.Entry<String, Object> entry : map.entrySet()){
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        params.setMultipart(true);
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    public static <T>Cancelable DownLoadFile(String url, String filePath,CommonCallback<T> callback)
    {
        RequestParams params = new RequestParams(getUrl(url));
        params.setAutoRename(true);
        params.setSaveFilePath(filePath);
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    private static String getUrl(String path) {
        return "http://" + SERVER_URL + path;
    }

    public static Drawable loadImage(ImageView iv, String picUrl) {
        initImageOptions();
        x.image().bind(iv, picUrl, imageOptions, new CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                drawableResult = result;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                drawableResult = x.app().getResources().getDrawable(R.mipmap.ic_launcher);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                drawableResult = x.app().getResources().getDrawable(R.mipmap.ic_launcher);
            }

            @Override
            public void onFinished() {
                drawableResult = x.app().getResources().getDrawable(R.mipmap.ic_launcher);
            }
        });
        return drawableResult;
    }
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    private static void initImageOptions() {
        if (null == imageOptions) {
            imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
                    .setRadius(DensityUtil.dip2px(5)).setCrop(true)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setFailureDrawableId(R.mipmap.ic_launcher).build();
        }
    }
}
