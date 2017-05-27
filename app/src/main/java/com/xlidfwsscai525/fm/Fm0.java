package com.xlidfwsscai525.fm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import com.xlidfwsscai525.MainActivity;
import com.xlidfwsscai525.R;
import com.xlidfwsscai525.base.BaseFm;
import com.xlidfwsscai525.entity.KaiJiangEntity;
import com.xlidfwsscai525.http.CM;
import com.xlidfwsscai525.http.ComCb;
import com.xlidfwsscai525.module.lottory.LottoTrendActivity;
import com.xlidfwsscai525.module.news.NewsAct;
import com.xlidfwsscai525.module.plan.PlanAct;
import com.xlidfwsscai525.module.suoshui.TwoStartAct;
import com.xlidfwsscai525.module.suoshui.WebAct;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.xlidfwsscai525.module.zoushi.chartView;
import com.xlidfwsscai525.tools.StringUtils;
import com.xlidfwsscai525.tools.XgoLog;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

@ContentView(R.layout.layout_fm0)
public class Fm0 extends BaseFm {
    @ViewInject(R.id.tv_1)
    private TextView m_tv_1; //第926期最新开奖;
    @ViewInject(R.id.checkbox_tags_view_group)
    private com.xlidfwsscai525.module.suoshui.CheckBoxTagViewGroup3 m_checkbox_tags_view_group;
    @ViewInject(R.id.tv_2)
    private TextView m_tv_2; //第926期开奖时间剩余;
    @ViewInject(R.id.time)
    private TextView m_time;
    //    private com.xlidfwsscai525.view.TimeView m_time;
    private static final String M_RULE_LSSC = "http://www.zjt-cp.com/html/rule/rule_Lssc.html?os=android&appVersion=6.2.1705&appName=Android_ssczs&visitFrom=outside";
    private static final String M_LOTTERYCATEGORY = "http://www.zjt-cp.com/html/chengben.html?lotteryCategory=Lssc&os=android&appVersion=6.2.1705&appName=Android_ssczs&visitFrom=outside";
    private long recLen;
    SimpleDateFormat formatter;
    MediaPlayer mMediaPlayer = null;
    Vibrator vibrator = null;
    NotificationManager m_Manager = null;
    PendingIntent m_PendingIntent;
    Notification m_Notification = null;

    @Override
    public void initView(View view) {
        super.initView(view);
        if (formatter == null) {
            formatter = new SimpleDateFormat("mm:ss");
        }
        showProgressDailog();
        CM.getInstance().kaijiang(new FmComCb());
    }

    class FmComCb extends ComCb<KaiJiangEntity> {
        @Override
        public void onSuccess(final KaiJiangEntity result) {
            super.onSuccess(result);
            setFmComCb(result);
        }
    }

    private void setFmComCb(KaiJiangEntity result) {
        String result1 = result.items.current.result;
        String[] split = result1.split(",");
        ArrayList<String> arrayList = new ArrayList<>();
        for (String s : split) {
            arrayList.add(s);
        }
        m_checkbox_tags_view_group.update(arrayList);
        m_tv_1.setText("第" + result.items.current.period + "期最新开奖");
        m_tv_2.setText("第" + result.items.next.period + "期开奖时间剩余");

           /* long l = Long.valueOf(result.items.time);
            String nextTimeStr = result.items.next.date + " " + result.items.next.time;
            long l1 = StringUtils.dateToStamp(nextTimeStr);
            long l2 = l1 - l;
            XgoLog.e("l2:" + l2);//l2/1000*/
            recLen = result.items.next.interval/1000;//(l2 / 1000);
            timer.schedule(task, 1000, 1000);
            dismissProgressDialog();

    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            recLen--;
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);

        }
    };

    Handler handler =  new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    long  min = recLen/60;
                    long  sec = recLen - min*60;
                    if (recLen > 0) {
                        m_time.setText("" + min + ":" + sec);
                    }else{
                        m_time.setText("请求网络中");
                    }
                    System.out.println( min + ":" + sec);
                    if (recLen >0 && recLen  < 5) {
                        showProgressDailog();
                        CM.getInstance().kaijiang(new FmComCb());
                    }
                    break;
            }
        }
    };

    public void showToast() {
        // 使用来电铃声的铃声路径
        // 如果为空，才构造，不为空，说明之前有构造过
        try {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.setDataSource(getContext(), uri);
            mMediaPlayer.setLooping(true); //循环播放
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            vibrator = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
            // 等待3秒，震动3秒，从第0个索引开始，一直循环
            vibrator.vibrate(new long[]{1000, 1000}, 0);

            m_Manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            //m_Manager.cancel(1023); //取消
            m_PendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent(getActivity(), MainActivity.class), 0);
            m_Notification = new Notification();
            m_Notification.icon = R.mipmap.ic_launcher; //设置图标
            m_Notification.tickerText = "要开奖啦。。准备好";
            m_Manager.notify(1023, m_Notification); // 这个notification 的 id 设为1023
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Event({R.id.ll_00, R.id.ll_01, R.id.ll_02, R.id.ll_03,
            R.id.ll_plan, R.id.ll_cbsc, R.id.ll_knowledge, R.id.ll_donar, R.id.ll_news})
    private void OnFm0OnClick(View v) {

        switch (v.getId()) {
            case R.id.ll_00:
                startActivity(new Intent(getActivity(), TwoStartAct.class));
                break;
            case R.id.ll_01:
                startActivity(new Intent(getActivity(), NewsAct.class));
                break;
            case R.id.ll_02:
                startActivity(new Intent(getActivity(), LottoTrendActivity.class));
                break;
            case R.id.ll_03:
                break;
            case R.id.ll_plan:
                startActivity(new Intent(getActivity(), PlanAct.class));
                break;
            case R.id.ll_cbsc:
                WebAct.onNewInstance(getActivity(), M_LOTTERYCATEGORY, getResources().getString(R.string.layout_fm0_tv_text_05));
                break;
            case R.id.ll_knowledge:
                WebAct.onNewInstance(getActivity(), M_RULE_LSSC, getResources().getString(R.string.layout_fm0_tv_title_06));
                break;
            case R.id.ll_donar:
                startActivity(new Intent(getActivity(), chartView.class));
//            startActivity(new Intent(getActivity(), RealmDatabaseActivityLine.class));
                break;
            case R.id.ll_news:
                startActivity(new Intent(getActivity(), NewsAct.class));
                break;
        }

    }
}
