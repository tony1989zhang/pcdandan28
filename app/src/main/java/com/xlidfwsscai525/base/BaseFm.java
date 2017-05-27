package com.xlidfwsscai525.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.view.CProDlg;

import org.xutils.x;


/**
 * 
 * 作用：片段的基类
 */
public class BaseFm extends Fragment {
    private boolean injected = false;
    private View toastView;
    private Toast toast;
    private CProDlg mProgress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!injected){
            x.view().inject(this,this.getView());
            mProgress = CProDlg.createDialog(getActivity());
            toastView = LayoutInflater.from(getActivity()).inflate(R.layout.toast_view, null);
            toast = Toast.makeText(getActivity(), null, Toast.LENGTH_LONG);
            toast.setView(toastView);
        }
        initView(view);
    }

    public void initView(View view) {
    }

    public void showProgressDailog() {
        showProgressDailog("请稍后");
    }

    public void showProgressDailog(String strMessage) {
        showProgressDialog(strMessage, false);
    }

    public void showProgressDialog(String strMessage, boolean isCancelAble) {

        if (null == mProgress) {
            mProgress = CProDlg.createDialog(getActivity());
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
}
