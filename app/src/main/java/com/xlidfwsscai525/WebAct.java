package com.xlidfwsscai525;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.xlidfwsscai525.base.BaseAct;
import com.xlidfwsscai525.entity.OpenEntity;
import com.xlidfwsscai525.http.CM;
import com.xlidfwsscai525.http.ComCb;
import com.xlidfwsscai525.impl.OnOperationListener;
import com.xlidfwsscai525.tools.PictureUtil;
import com.xlidfwsscai525.tools.SPUtil;
import com.xlidfwsscai525.tools.StringUtils;
import com.xlidfwsscai525.tools.XgoLog;
import com.xlidfwsscai525.view.CusDlg;
import com.xlidfwsscai525.view.CustomDialog;
import com.xlidfwsscai525.view.CustomPopWindow;
import com.xlidfwsscai525.view.TitleView;
import com.xlidfwsscai525.zxing.DecodeImage;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@ContentView(R.layout.act_web)
public class WebAct extends BaseAct implements View.OnLongClickListener {

    /***
     * zhang cn update
     */
    @ViewInject(R.id.title_view)
    TitleView mTitleView;
    @ViewInject(R.id.activty_wb_content)
    WebView mWebView;
    @ViewInject(R.id.load_faild)
    TextView mLoadFailed;
    @ViewInject(R.id.loading)
    View mLoading;
    @ViewInject(R.id.tv5)
    private TextView tv5;

    public static final String WEB_EXT_URL = "ext_url";
    public static final String WEB_EXT_TITLE = "ext_title";
    private String mUrl = "";
    private boolean isHome;
    private boolean isHide;
    private File file;
    private boolean isQR;//判断是否为二维码
    private ArrayAdapter<String> adapter;

    private Result result;//二维码解析结果
    private CustomDialog mCustomDialog;
    private long firstBack;

    @Override
    protected void onDestroy() {
        super.onDestroy();
//		mTitleView = null;
//		mWebView = null;
//		mLoadFailed = null;
//		mLoading = null;
        setContentView(R.layout.view_null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUrl = this.getIntent().getStringExtra(WEB_EXT_URL);
        String title = this.getIntent().getStringExtra(WEB_EXT_TITLE);

        mTitleView.setTitle(title);
        mTitleView.setTitleBackVisibility(View.VISIBLE);


        mWebView.setInitialScale(50);
        mWebView.setWebViewClient(new LCHWebViewClient());
        mWebView.setWebChromeClient(new CusWebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.loadDataWithBaseURL("about:blank", "<span style=\"color:#FF0000\">网页加载失败</span>", "text/html", "utf-8", null);

        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setBlockNetworkImage(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setOnLongClickListener(this);
        isHome = true;
        mWebView.loadUrl(mUrl);
    }

    boolean isBase64 = false;

    @Override
    public boolean onLongClick(View v) {
        final WebView.HitTestResult htr = mWebView.getHitTestResult();
        if (htr.getType() == WebView.HitTestResult.IMAGE_TYPE) {
            XgoLog.e("htr.getExtra()" + htr.getExtra());
            String extra = htr.getExtra();
            if (extra.startsWith("data:image/png;base64,")) {
                isBase64 = true;
                String[] split = extra.split(",");
                XgoLog.e("substring:" + split[1]);
                // 获取到图片地址后做相应的处理
                MyAsyncTask mTask = new MyAsyncTask();
                mTask.execute(split[1]);
                showDialog();
            } else {
                isBase64 = false;
                // 获取到图片地址后做相应的处理
                MyAsyncTask mTask = new MyAsyncTask();
                mTask.execute(htr.getExtra());
                showDialog();
            }
        }
        return false;
    }

    private class LCHWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (null == url) {
                return false;
            }
            if (TextUtils.equals(url, mUrl))
                isHome = true;
            else
                isHome = false;

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mLoading.setVisibility(View.GONE);
            mWebView.getSettings().setBlockNetworkImage(false);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mLoading.setVisibility(View.VISIBLE);
            mLoadFailed.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            mLoadFailed.setVisibility(View.VISIBLE);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            mWebView.requestFocus();
            mWebView.requestFocusFromTouch();
        }
    }

    class CusWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            // setDlg(message,result);
            XgoLog.e("onJsAlert:url" + url + "message:" + message);
            setDlg(message, result);
            result.cancel();
            return true;
        }


        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//            setDlg(message,result);
            XgoLog.e("onJsAlert:url" + url + "message:" + message);
            return super.onJsConfirm(view, url, message, result);
        }

        public void setDlg(String msg, final JsResult result) {
            final CusDlg cusDlg2 = new CusDlg(WebAct.this);
            cusDlg2.setButtonText("确定");
            cusDlg2.setTitle("提示");
            cusDlg2.setMessage(msg);
            cusDlg2.setOperationListener(new OnOperationListener() {
                @Override
                public void onLeftClick() {
//                    result.cancel();
                    cusDlg2.cancel();
                }

                @Override
                public void onRightClick() {
//                    result.cancel();
                    cusDlg2.cancel();
                }
            });
            cusDlg2.show();
        }
    }

    @Event({R.id.load_faild, R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5})
    private void webViewOnClick(View v) {

        switch (v.getId()) {
            case R.id.load_faild:

                mWebView.reload();
            case R.id.tv1:
                if (StringUtils.isNotEmpty(mUrl) && !isHome) {
                    mWebView.loadUrl(mUrl);
                    isHome = true;
                }
                break;
            case R.id.tv2:
                XgoLog.e("mWebViewgoback");
                if (mWebView.canGoBack())
                    mWebView.goBack();
                else Toast.makeText(this, "不能再后退", Toast.LENGTH_LONG).show();
                break;
            case R.id.tv3:
                if (mWebView.canGoForward())
                    mWebView.goForward();
                else Toast.makeText(this, "不能再前进", Toast.LENGTH_LONG).show();
                break;
            case R.id.tv4:
                mWebView.reload();
                break;
            case R.id.tv5:
                showPopTop();
                break;
            default:
                break;

        }
    }

    class ShareComCb extends ComCb<OpenEntity> {
        @Override
        public void onSuccess(OpenEntity result) {
            super.onSuccess(result);
            String msg = "热门彩种，专业权威数据分析，足不出户，掌握最新动态";
            share(result.msg.links);
        }
    }

    private void showPopTop() {
        View inflate = getLayoutInflater().inflate(R.layout.pop_layout2, null);

        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(inflate)
                .create();
        popWindow.showAsDropDown(tv5, 0, -(tv5.getHeight() + popWindow.getHeight()));
        //popWindow.showAtLocation(mButton1, Gravity.NO_GRAVITY,0,0);
        popupwindowOnClick(inflate, popWindow);
    }

    private void popupwindowOnClick(View inflate, CustomPopWindow popWindow) {
        inflate.findViewById(R.id.tv1).setOnClickListener(new CusPopWindowOnClickListener(popWindow));
        inflate.findViewById(R.id.tv2).setOnClickListener(new CusPopWindowOnClickListener(popWindow));
        inflate.findViewById(R.id.tv3).setOnClickListener(new CusPopWindowOnClickListener(popWindow));
    }


    class CusPopWindowOnClickListener implements OnClickListener {

        CustomPopWindow mPopWindow;

        public CusPopWindowOnClickListener(CustomPopWindow popWindow) {
            mPopWindow = popWindow;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv1:
                    CM.getInstance().share(new ShareComCb());
                    break;
                case R.id.tv2:
                    showDLg();
                    break;
                case R.id.tv3:
                    mPopWindow.dissmiss();
                    showProgressDialog("正清除缓存");
                    XgoLog.e("正清除缓存");
                    clearWebViewCache();
                    break;
                default:
                    break;
            }
        }

    }

    private void share(String msg) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "时时中彩票");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        XgoLog.e("isHome:" + isHome);
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mWebView.canGoBack() && event.getRepeatCount() == 0 && !isHome) {
                    mWebView.goBack();
                    return true;
                } else {
                    //弹框是否退出
                    final CusDlg cusDlg = new CusDlg(this);
                    cusDlg.setButtonsText("取消", "确定");
                    cusDlg.setTitle("");
                    cusDlg.setMessage("是否确认退出");
                    cusDlg.setOperationListener(new OnOperationListener() {
                        @Override
                        public void onLeftClick() {
                            cusDlg.cancel();
                        }

                        @Override
                        public void onRightClick() {
                            finish();
                        }
                    });
                    cusDlg.show();
                }
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void clearWebViewCache() {
        // 清除cookie即可彻底清除缓存
      /*  CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();*/
        dismissProgressDialog();
        showToast("缓存已清除完毕");
        mWebView.loadUrl(mUrl);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
    }


    public Bitmap getBitmap(String sUrl) {
        XgoLog.e("getBitmap:" + sUrl);
        try {
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                saveMyBitmap(bitmap, "code");//先把bitmap生成jpg图片
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            XgoLog.e("e:" + e.toString());
        }
        return null;
    }


    /**
     * bitmap 保存为jpg 图片
     *
     * @param mBitmap 图片源
     * @param bitName 图片名
     */
    public void saveMyBitmap(Bitmap mBitmap, String bitName) {
        file = new File(Environment.getExternalStorageDirectory() + "/" + bitName + ".jpg");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (isQR) {
                handler.sendEmptyMessage(0);
            }


        }

        @Override
        protected String doInBackground(String... params) {
          /*  XgoLog.e("params:" + params[0]);
           decodeImage(params[0]);
            getBitmap(params[0]);*/
            decodeImage(params[0]);
            return null;
        }
    }

    /**
     * 判断是否为二维码
     * param url 图片地址
     * return
     */
    private boolean decodeImage(String sUrl) {
        if (isBase64) {
            Bitmap bitmap = PictureUtil.base64ToBitmap(sUrl);
            result = DecodeImage.handleQRCodeFormBitmap(bitmap);
            saveMyBitmap(bitmap, "code");//先把bitmap生成jpg图片

        } else {
            result = DecodeImage.handleQRCodeFormBitmap(getBitmap(sUrl));
        }
        if (result == null) {
            isQR = false;
        } else {
            isQR = true;
        }
        return isQR;
    }

    /**
     * 是二维码时，才添加为识别二维码
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            isQR  = false;//暂放
            if (msg.what == 0) {
                if (isQR) {
                    adapter.add("识别图中二维码");
                }
                adapter.notifyDataSetChanged();
            }
        }

        ;
    };

    /**
     * 初始化数据
     */
    private void initAdapter() {
        adapter = new ArrayAdapter<>(this, R.layout.item_dialog);
      //  adapter.add("发送给朋友");
        adapter.add("保存到手机");
      //  adapter.add("收藏");
    }

    /**
     * 显示Dialog
     * param v
     */
    private void showDialog() {
        initAdapter();
        mCustomDialog = new CustomDialog(this) {
            @Override
            public void initViews() {
                // 初始CustomDialog化控件
                ListView mListView = (ListView) findViewById(R.id.lv_dialog);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // 点击事件
                        switch (position) {
                            case 0:
                                saveImageToGallery(WebAct.this);
                                closeDialog();
                                break;
                            case 1:
                                goIntent();
                                closeDialog();
                                break;
                        }

                    }
                });
            }
        };
        mCustomDialog.show();
    }

    /**
     * 发送给好友
     */
    private void sendToFriends() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri imageUri = Uri.parse(file.getAbsolutePath());
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }

    /**
     * 先保存到本地再广播到图库
     */
    public void saveImageToGallery(Context context) {

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), "code", null);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"
                    + file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void goIntent() {
      /*  try {
            Uri uri = Uri.parse(result.toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        catch ()*/
        try {
            goToWx(result.toString());
        } catch (Exception e) {
            Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToWx(String url) {
        XgoLog.e("url:" + url);
        // 如下方案可在非微信内部WebView的H5页面中调出微信支付
        if (url.startsWith("weixin://wxpay/")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } else if (parseScheme(url)) {
            try {
                Intent intent;
                intent = Intent.parseUri(url,
                        Intent.URI_INTENT_SCHEME);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                // intent.setSelector(null);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    public boolean parseScheme(String url) {
        if (url.contains("platformapi/startapp")) {
            return true;
        } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                && (url.contains("platformapi") && url.contains("startapp"))) {
            return true;
        } else {
            return false;
        }
    }

    public void showDLg() {

        final CusDlg cusDlg = new CusDlg(this);

        cusDlg.setButtonsText("复制", "取消");
        cusDlg.setTitle("" + SPUtil.getInstant(this).get("cn.jpush.android.NOTIFICATION_CONTENT_TITLE", "提示"));
        cusDlg.setMessage("" + SPUtil.getInstant(this).get("cn.jpush.android.ALERT", "有新消息"));
        cusDlg.setOperationListener(new OnOperationListener() {
            @Override
            public void onLeftClick() {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", "" + SPUtil.getInstant(WebAct.this).get("cn.jpush.android.ALERT", "有新消息"));
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                cusDlg.cancel();
            }

            @Override
            public void onRightClick() {
                cusDlg.cancel();
            }
        });
        cusDlg.show();
    }

}
