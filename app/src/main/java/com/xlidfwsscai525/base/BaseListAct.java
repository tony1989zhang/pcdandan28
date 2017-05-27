package com.xlidfwsscai525.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.app.App;
import com.xlidfwsscai525.http.CUtil;
import com.xlidfwsscai525.view.CProDlg;
import com.xlidfwsscai525.view.DividerItemDecoration;
import com.xlidfwsscai525.view.TitleView;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by 张灿能 on 2016/8/6.
 * 作用:封装列表式数据
 */
@ContentView(R.layout.list_normal_hastitle)
public abstract class BaseListAct<T> extends Activity implements SwipeRefreshLayout.OnRefreshListener, Callback.CommonCallback<T> {
    protected final static String LAST_ACTIVITY_NAME = "LAST_ACTIVITY_NAME";
    @ViewInject(R.id.title_view)
    protected TitleView mTitleView;
    @ViewInject(R.id.recycler_view)
    protected RecyclerView mViewList;
    @ViewInject(R.id.empty_propt)
    private TextView mEmptyTips;
    @ViewInject(R.id.tv_submit_ok)
    public TextView m_tv_submit_ok;

    @ViewInject(R.id.include_btn_submit)
    public View m_include_btn_submit;

    SwipeRefreshLayout mSwipeRefreshLayout;


    BasePageAdapter mAdapter;
    private Cancelable cancelable;

    private Dialog mLoadingDialog;
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
        mProgress = CProDlg.createDialog(this);
        toastView = LayoutInflater.from(this).inflate(R.layout.toast_view, null);
        toast = Toast.makeText(this, null, Toast.LENGTH_LONG);
        toast.setView(toastView);
        initViews();
    }


    protected void initViews() {
//        mTitleView = (TitleView) findViewById(R.id.title_view);
//        mViewList = (RecyclerView) findViewById(R.id.recycler_view);
        mViewList.setLayoutManager(initLayoutManager());


        mAdapter = initAdapter();
        mAdapter.init(mViewList, isPageEnabled());
        mViewList.setItemAnimator(new DefaultItemAnimator());
        mViewList.addItemDecoration(new DividerItemDecoration(mViewList.getContext(), LinearLayoutManager.VERTICAL));
       // mEmptyTips = (TextView) findViewById(R.id.empty_propt);
        mEmptyTips.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (CUtil.isNetworkConnected(mViewList.getContext())) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    requestData(0, false);
                } else {
//                    Toast.makeText(mTitleView.getContext(), R.string.network_not_connection, Toast.LENGTH_SHORT).show();
                    showToast(getResources().getString(R.string.network_not_connection));
                }

            }
        });

        mAdapter.setPagingableListener(new BasePageAdapter.Pagingable() {

            @Override
            public void onLoadMoreItems() {
                // TODO Auto-generated method stub
                if (mAdapter.hasMoreItems()) {
                    requestData(mAdapter.getItems() == null ? 0 : mAdapter.getItems().size(), false);
                } else {
                    mAdapter.onFinishLoading(false);
                }
            }
        });

        // TitleBarMovableTouchListener touchListener = new
        // TitleBarMovableTouchListener(this.getActivity().findViewById(R.id.activity_title));
        // mViewList.setOnTouchListener(touchListener);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(isSwipeRefreshLayoutEnabled());
        mSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_refrsh_color1, R.color.swipe_refrsh_color2,
                R.color.swipe_refrsh_color3, R.color.swipe_refrsh_color4);
        mViewList.setAdapter(mAdapter);
        mHandler.sendEmptyMessageDelayed(MSG_REQUEST_DATA, FIRST_INIT_DELAY);

    }

    public void showEmpty() {
        mViewList.setVisibility(View.GONE);
        mEmptyTips.setVisibility(View.VISIBLE);
    }

    public void hideEmpty() {
        mViewList.setVisibility(View.VISIBLE);
        mEmptyTips.setVisibility(View.GONE);
    }

    protected void requestData(int start, boolean showloading) {
        if (cancelable != null) {
            cancelable.cancel();
        }
        cancelable = initRequest(start);
        hideEmpty();
        if (showloading) {
            showLoading();
        }

    }

    /**
     * 解析Json，得到List,在子线程中运行
     */
    protected abstract List convertToBeanList(T t);

    protected RecyclerView.LayoutManager initLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mViewList.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    /**
     * 初始化Adapter
     */
    protected abstract BasePageAdapter initAdapter();

    /**
     * 返回是否需要下拉刷新
     */
    protected abstract boolean isSwipeRefreshLayoutEnabled();

    /**
     * 返回每一页的数量
     */
    protected abstract int getSizeInPage();

    /**
     * 获取Request并执行网络请求
     */
    protected abstract Cancelable initRequest(int start);

    protected abstract boolean isPageEnabled();

    protected abstract boolean isDataGot();

    private static final int MSG_REQUEST_DATA = 1;
    private static final long FIRST_INIT_DELAY = 50;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REQUEST_DATA: {
                    mSwipeRefreshLayout.setRefreshing(true);
                    requestData(mAdapter.getItems() == null ? 0 : mAdapter.getItems().size(), false);
                    break;
                }
            }
        }
    };


    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        if (CUtil.isNetworkConnected(mViewList.getContext())) {
            reloadData();
        } else {
//            Toast.makeText(mTitleView.getContext(), R.string.network_not_connection, Toast.LENGTH_SHORT).show();
            showToast(getResources().getString(R.string.network_not_connection));
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public void reloadData() {
        if (mAdapter != null) {
            mAdapter.clearData();
        }
        requestData(0, false);
    }

    private class GetBeanListTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
            return convertToBeanList((T)params[0]);
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            onBeanListGot(result == null ? null : (List<T>) result);
        }
    }

    @Override
    public void onSuccess(T result) {
        new GetBeanListTask().execute(result == null ? "" : result);
    }


    private void onBeanListGot(List<T> listResult) {
        cancelable = null;
        dismissLoading();
        mSwipeRefreshLayout.setRefreshing(false);
        hideEmpty();
        if (listResult == null) {
            mAdapter.onFinishLoading(true);
            checkShowEmpty(mAdapter);
            return;
        }

        List<T> items = mAdapter.getItems();
        if (items == null) {
            items = listResult;
        } else {
            items.addAll(listResult);
        }
        mAdapter.setItems(items);
        if (listResult.size() < getSizeInPage()) {
            mAdapter.onFinishLoading(false);
        } else {
            mAdapter.onFinishLoading(true);
        }
        checkShowEmpty(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void checkShowEmpty(BasePageAdapter adapter) {
        final List items = adapter.getItems();
        if ((items == null || items.size() == 0) && !isDataGot()) {
            showEmpty();
        }
    }

    protected void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new ProgressDialog(mViewList.getContext());
            mLoadingDialog.setCancelable(false);
        }
        mLoadingDialog.show();
    }

    protected void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
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

        TextView tv = (TextView) toastView.findViewById(R.id.toastMessage);
        tv.setText(hint);
        toast.setDuration(duration);
        toast.show();
    }

    @Event(value = {R.id.title_back, R.id.title_right}, type = View.OnClickListener.class)
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

    public void setTitleV(TitleView title_view, String title) {
        setTitleV(title_view, title, null);
    }

    public void setTitleV(TitleView title_view, String title, String rightText) {
        if (title_view == null)
            return;

        title_view.setTitle(title);
        title_view.setTitleBackVisibility(View.VISIBLE);
        if (null != rightText && !("").equals(rightText)) {
            title_view.setTitleRightText(rightText);
        } else {
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
        if (cancelable != null && !cancelable.isCancelled()) {
            cancelable.cancel();
        }
        mProgress = null;
        App.getInstance().aliveActivitys.remove(WriActivity);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
    }

    @Override
    public void onCancelled(CancelledException cex) {
    }

    @Override
    public void onFinished() {
    }
    @Event(R.id.tv_submit_ok)
    private void submitOk(View v){
        submitOnClick();
    }

    public void submitOnClick(){
    }
}
