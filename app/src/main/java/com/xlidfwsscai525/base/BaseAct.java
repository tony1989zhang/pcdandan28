package com.xlidfwsscai525.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.app.App;
import com.xlidfwsscai525.view.CProDlg;
import com.xlidfwsscai525.view.TitleView;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class BaseAct extends Activity {
    protected final static String LAST_ACTIVITY_NAME = "LAST_ACTIVITY_NAME";
    private CProDlg mProgress;
    private View toastView;
    private Toast toast;
    public WeakReference<Activity> WriActivity = new WeakReference<Activity>(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        App.getInstance().aliveActivitys.add(WriActivity);
        mProgress = CProDlg.createDialog(this);
        toastView = LayoutInflater.from(this).inflate(R.layout.toast_view, null);
        toast = Toast.makeText(this,null,Toast.LENGTH_LONG);
        toast.setView(toastView);
        initView();
    }

    //    @Override
//    public void onCreate(Bundle savedInstanceStat) {
//        super.onCreate(savedInstanceState);
//        x.view().inject(this);
//        App.getInstance().aliveActivitys.add(WriActivity);
//        mProgress = CProDlg.createDialog(this);
//        toastView = LayoutInflater.from(this).inflate(R.layout.toast_view, null);
//        toast = Toast.makeText(this, null, Toast.LENGTH_LONG);
//        toast.setView(toastView);
//        initView();
//    }
    protected void initView() {
    }

    public void setTitleV(TitleView title_v, String title) {
        setTitleV(title_v, title, null);
    }

    public void setTitleV(TitleView title_v, String title, String rightText) {
        if (title_v == null) return;
        title_v.setTitle(title);
        title_v.setTitleBackVisibility(View.VISIBLE);
        title_v.setTitleRightText(rightText);
    }

    public void showProgressDialog() {
        showProgressDialog("请稍后");
    }

    public void showProgressDialog(String strMessage) {
        showProgressDialog(strMessage, false);
    }

    public void showProgressDialog(String strMessage, boolean isCancelAble) {
        if (null == mProgress) {
            return;
        }
        if (isCancelAble) {
            mProgress.setCancelable(true);
            mProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mProgress.dismiss();
                }
            });
        }
        mProgress.setMessage(strMessage);
        mProgress.show();
    }

    public void dismissProgressDialog() {
        if (null != mProgress && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    public void showToast(String hint) {
        showToast(hint, Toast.LENGTH_LONG);
    }

    public void showToast(String hint, int duration) {
        if (null == toast || null == toastView)
            return;
        TextView tv = (TextView) toastView.findViewById(R.id.toastMessage);
        tv.setText(hint);
        toast.setDuration(duration);
        toast.show();
    }

    @Event(value = {R.id.title_back, R.id.title_right})
    private void titleClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                break;
            default:
                break;
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (null != intent) {
            intent.putExtra(LAST_ACTIVITY_NAME, this.getClass().getName());
        }
        super.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.view_null);
        mProgress = null;
        App.getInstance().aliveActivitys.remove(WriActivity);
    }

}
