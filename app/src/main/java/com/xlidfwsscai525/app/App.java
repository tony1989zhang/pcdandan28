package com.xlidfwsscai525.app;


import android.app.Activity;
import android.app.Application;

import com.xlidfwsscai525.BuildConfig;
import com.xlidfwsscai525.tools.XgoLog;
import com.xlidfwsscai525.view.ToastManager;
import com.google.gson.Gson;

import org.xutils.DbManager;
import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class App extends Application {
    private static  App mApp = null;
    public List<WeakReference<Activity>> aliveActivitys = new ArrayList<>();
    private  DbManager.DaoConfig daoConfig;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }

    public static App getInstance(){
        if (mApp == null)
        {
            mApp = new App();
        }
        return mApp;
    }

    private void initDb(){
        daoConfig = new DbManager.DaoConfig().setDbName("xbc_cart_team_db").setDbVersion(1).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                //XgoLog.e("数据库更新" + newVersion);
            }
        });//数据库更新操作

    }

    /**
     * 获取到数据库管理器
     * */
    public DbManager getDbManager(){
        if (null == daoConfig)initDb();
        return  x.getDb(daoConfig);
    }
    public <T>T getBeanFromJson(String ret,Class<T> c){
        T bean = null;
        try {
            bean = new Gson().fromJson(ret, c);
        }catch(Exception e)
        {
            ToastManager.getManager().show("Gson解析错误:" + e.toString());
            XgoLog.e("Gson解析错误");
        }
        return bean;
    }

    public void finishAllActivity(){
        for (int i = 0;i < aliveActivitys.size();i++)
        {
            if (aliveActivitys.get(i) != null)
            {
                aliveActivitys.get(i).get().finish();
            }
        }
    }
    public Activity getTopActivity(){
        return aliveActivitys.get(aliveActivitys.size() - 1).get();
    }
}
