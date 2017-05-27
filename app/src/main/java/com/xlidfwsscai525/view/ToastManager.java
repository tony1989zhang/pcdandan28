package com.xlidfwsscai525.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xlidfwsscai525.R;

import org.xutils.x;


/**
 * Toast工具类
 */
public class ToastManager {
	private static ToastManager manager;
	private View toastView;
	private Context context;
	private Toast toast;
	private TextView tvToast;

	public ToastManager() {
		init();
	}

	public static ToastManager getManager() {
		if (manager == null)
			synchronized (ToastManager.class) {
				manager = new ToastManager();
			}
		return manager;
	}

	private void init() {
		context = x.app().getBaseContext();
		toastView = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
		tvToast = (TextView) toastView.findViewById(R.id.toastMessage);
		toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
		toast.setView(toastView);
	}

	public void show(int resId) {
		show(resId, Toast.LENGTH_SHORT);
	}

	public void show(String hint) {
		show(hint, Toast.LENGTH_SHORT);
	}

	public void show(int resId, int duration) {
		toast.setDuration(duration);
		tvToast.setText(resId);
		toast.show();
	}

	public void show(String hint, int duration) {
		toast.setDuration(duration);
		tvToast.setText(hint);
		toast.show();
	}

	public void onDestroy() {
		toast.cancel();
		toast = null;
		manager = null;
	}
}
