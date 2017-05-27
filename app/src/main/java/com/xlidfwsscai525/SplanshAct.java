package com.xlidfwsscai525;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xlidfwsscai525.base.BaseAct;
import com.xlidfwsscai525.entity.OpenEntity;
import com.xlidfwsscai525.http.CM;
import com.xlidfwsscai525.http.ComCb;
import com.xlidfwsscai525.tools.Constants;
import com.xlidfwsscai525.tools.SPUtil;
import com.xlidfwsscai525.tools.StringUtils;
import com.xlidfwsscai525.tools.XgoLog;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
@ContentView(R.layout.layout_spl)
public class SplanshAct  extends BaseAct{

    private long splanshTime = 1000;
    @ViewInject(R.id.iv)
    private ImageView mIv;
    @Override
    protected void initView() {
        super.initView();
        overridePendingTransition(R.anim.zoomin, 0);//activity 进入动画
        x.task().postDelayed(new Runnable() {
            @Override
            public void run() {

                //放广告页面，暂不存放东西
              //  showAdverSplan();
            }
        }, 1000);

        doJump2();
    }

    public void showAdverSplan() {


        ImageOptions options = new ImageOptions.Builder()
                //设置加载过程中的图片
                .setLoadingDrawableId(R.mipmap.icon_img_null_bg)
                //设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.icon_img_null_bg)
                //设置使用缓存
                .setUseMemCache(true)
                //设置显示圆形图片
                .setCircular(false)
                //设置支持gif
                .setIgnoreGif(false)
                .build();
        //https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1472088194&di=db4dca288e8967cf60d7a5971703f4b1&src=http://img.zcool.cn/community/01313f56c3d5546ac7256cb094ff05.jpg
        //http://7xlh8k.com1.z0.glb.clouddn.com/%E8%BD%A6%E9%98%9F%EF%BC%8D%E5%B9%BF%E5%91%8A.png
        x.image().loadDrawable("https://baike.baidu.com/pic/北京赛车系统/8255640/0/1b4c510fd9f9d72a8b8e7e2ddd2a2834349bbb1c?fr=lemma&ct=single#aid=0&pic=1b4c510fd9f9d72a8b8e7e2ddd2a2834349bbb1c", options, new ComCb<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {

                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                ViewGroup.LayoutParams layoutParams = mIv.getLayoutParams();
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                mIv.setLayoutParams(layoutParams);
                mIv.setMaxWidth(dm.widthPixels);
                mIv.setMaxHeight((int)(dm.widthPixels*5));

                mIv.setImageDrawable(result);
                mIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://img4.imgtn.bdimg.com/it/u=250883362,3555496764&fm=11&gp=0.jpg"));
                        SplanshAct.this.startActivity(intent);
                    }
                });
            }
        });
    }

    class SplanshComCb extends ComCb<OpenEntity>{
        public void onError(Throwable ex, boolean isOnCallback) {
            super.onError(ex, isOnCallback);
            System.out.print("ex:" + ex.toString());
            XgoLog.e("ex:" + ex.toString());
        }

        @Override
        public void onSuccess(OpenEntity result) {
            super.onSuccess(result);
            Intent intent = null;
            if (result.msg.open == 1){ //默认1
                 intent = new Intent(SplanshAct.this, WebAct.class);
                intent.putExtra(WebAct.WEB_EXT_TITLE,"");
                intent.putExtra(WebAct.WEB_EXT_URL,result.msg.links);

            }else{
                 intent = new Intent(SplanshAct.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        }
    }
    private void doJump2(){

        x.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                CM.getInstance().open(new SplanshComCb());
            }

        }, splanshTime);
    }
    private void doJump() {
        final boolean isFristStart = (boolean) SPUtil.getInstant(this).get(Constants.IS_FRIST_START_APP, false);
        x.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                Class activity = null;
                if (!isFristStart) {
                    activity = MainActivity.class;
                    SPUtil.getInstant(SplanshAct.this).save(Constants.IS_FRIST_START_APP, true);
                } else {
                    if (StringUtils.isEmpty(SPUtil.getInstant(SplanshAct.this).get(Constants.USER_GID, ""))) {
                        activity = MainActivity.class;
                    } else {
                        //判断是否填写完毕，填写完毕跳转主页
                        activity = MainActivity.class;
                        //如果没有填写完毕，则跳转信息填写的页面
                    }
                }
                startActivity(new Intent(SplanshAct.this, activity));
                finish();
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        }, splanshTime);
    }

}
