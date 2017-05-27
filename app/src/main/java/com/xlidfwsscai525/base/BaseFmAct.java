package com.xlidfwsscai525.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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



public class BaseFmAct extends FragmentActivity {
    private static final String TextView = null;
    private CProDlg mProgress;
    private View toastView;
    private Toast toast;

    public WeakReference<Activity> WriActivity = new WeakReference<Activity>(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        App.getInstance().aliveActivitys.add(WriActivity);

//		SystemBarTintManager tintManager = new SystemBarTintManager(this);
//		tintManager.setStatusBarTintEnabled(true);
//		tintManager.setNavigationBarTintEnabled(true);
//		tintManager.setTintColor(getResources().getColor(R.color.title_bg_color));

        mProgress = CProDlg.createDialog(this);
        toastView = LayoutInflater.from(this).inflate(R.layout.toast_view, null);
        toast = Toast.makeText(this, null, Toast.LENGTH_LONG);
        toast.setView(toastView);

    }

    public void showProgressDailog() {
        showProgressDailog("请稍后");
    }

    public void showProgressDailog(String strMessage) {
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

        android.widget.TextView tv = (TextView) toastView.findViewById(R.id.toastMessage);
        tv.setText(hint);
        toast.setDuration(duration);
        toast.show();
    }

    @Event(value = { R.id.title_back, R.id.title_right }, type = View.OnClickListener.class)
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

    public void setTitleView(TitleView title_view, String title){
        setTitleView(title_view, title, null);
    }

    public void setTitleView(TitleView title_view, String title, String rightText){
        if(title_view == null)
            return;

        title_view.setTitle(title);
        title_view.setTitleBackVisibility(View.VISIBLE);
        if(null != rightText && !("").equals(rightText)){
            title_view.setTitleRightText(rightText);
        }else{

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.view_null);
        App.getInstance().aliveActivitys.remove(WriActivity);
    }
}
