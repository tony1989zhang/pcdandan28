package com.xlidfwsscai525.module.lottory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.xlidfwsscai525.R;
import com.xlidfwsscai525.base.BaseAct;
import com.xlidfwsscai525.entity.NumberStatEntity;
import com.xlidfwsscai525.http.CM;
import com.xlidfwsscai525.http.ComCb;
import com.xlidfwsscai525.view.TitleView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;


@ContentView(R.layout.activity_lotto_trend)
public class LottoTrendActivity extends BaseAct implements DDTrendChart.ISelectedChangeListener {

    @ViewInject(R.id.title_view)
    private TitleView mTitleView;
    @ViewInject(R.id.ltv_trendView)
    private LottoTrendView mTrendView;
    final int maxSignleNum = 9;
    private DDTrendChart mTrendChart;

    @Override
    protected void initView() {
        super.initView();
        initViews();
        CM.getInstance().numberStat(new LottoTrendComCb());
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            LottoTrendActivity.this.mTrendChart.updateData("01", (ArrayList) paramMessage.obj);
        }
    };

    private void initViews() {
        this.mTrendChart = new DDTrendChart(this, this.mTrendView);
        mTrendView.setChart(this.mTrendChart);
        this.mTrendChart.setShowYilou(true);
        this.mTrendChart.setDrawLine(true);
        this.mTrendChart.setSelectedChangeListener(this);
        mTitleView.setTitle("号码统计");
        mTitleView.setTitleBackVisibility(View.VISIBLE);
    }

    class LottoTrendComCb extends ComCb<NumberStatEntity>
    {
        @Override
        public void onSuccess(NumberStatEntity result) {
            super.onSuccess(result);
            a(result);
        }
    }


    protected void a(NumberStatEntity numberStatEntity ){
        ArrayList arrayList = new ArrayList();
        Collection arrayList2 = new ArrayList();
        TrendData r0;
        for (int i = 0;i < numberStatEntity.items.size();i++) {
            NumberStatEntity.ItemsBean itemsBean = numberStatEntity.items.get(i);
            TrendData trendData = new TrendData();
                    trendData.setType("row");
            String s = itemsBean.date.replaceAll("-", "");
            trendData.setPid(s);
                    trendData.setRed(itemsBean.data);
//                    trendData.setBlue(newPullParser.getAttributeValue(null, "blue"));
//                    trendData.setBalls(newPullParser.getAttributeValue(null, "balls"));
//                    trendData.setOes(newPullParser.getAttributeValue(null, "oe"));
//                    trendData.setBss(newPullParser.getAttributeValue(null, "bs"));
//                    trendData.setOne(newPullParser.getAttributeValue(null, "one"));
//                    trendData.setTwo(newPullParser.getAttributeValue(null, "two"));
//                    trendData.setThree(newPullParser.getAttributeValue(null, "three"));
//                    trendData.setCodes(newPullParser.getAttributeValue(null, "codes"));
//                    trendData.setSum(newPullParser.getAttributeValue(null, "sum"));
//                    trendData.setSpace(newPullParser.getAttributeValue(null, "space"));
//                    trendData.setNum(newPullParser.getAttributeValue(null, "num"));
//                    trendData.setTimes(newPullParser.getAttributeValue(null, "times"));
//                    trendData.setForm(newPullParser.getAttributeValue(null, "form"));
                    arrayList.add(trendData);
        }
        arrayList.addAll(arrayList2);
        mHandler.sendMessage(mHandler.obtainMessage(120, arrayList));
    }


    protected void onDestroy() {
        super.onDestroy();
    }

    public void onSelectedChange(TreeSet<Integer> treeSet, TreeSet<Integer> treeSet2) {

    }
}
