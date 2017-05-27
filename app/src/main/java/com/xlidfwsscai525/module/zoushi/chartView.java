package com.xlidfwsscai525.module.zoushi;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xlidfwsscai525.R;
import com.xlidfwsscai525.entity.ZouShiEntity;
import com.xlidfwsscai525.http.CM;
import com.xlidfwsscai525.http.ComCb;
import com.xlidfwsscai525.view.TitleView;

import android.graphics.Color;

public class chartView extends Activity implements OnClickListener {
	private Intent intent;
	private CombinedChart mChart;
	private final int itemcount = 12;
	private List<String> months = new ArrayList<String>();
	private List<Map<String, Object>> month = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	//数据
	private String[] mTimes = new String[] { };
	private int[] arrs = {};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.chartview);
		TitleView titleView = (TitleView) findViewById(R.id.title_view);
		titleView.setTitleBackVisibility(View.VISIBLE);
		ImageView mTitleBack = (ImageView) titleView.findViewById(R.id.title_back);
		mTitleBack.setOnClickListener(this);
		titleView.setTitle("PC蛋蛋走势图");
		mTitleBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		CM.getInstance().zoushi(new ComCb<ZouShiEntity>() {
			@Override
			public void onSuccess(ZouShiEntity result) {
				super.onSuccess(result);
				mTimes = new String[result.items.size()];
				arrs = new int[result.items.size()];
				for (int i = 0;i < result.items.size();i++)
				{
					ZouShiEntity.ItemsBean itemsBean = result.items.get(i);
					mTimes[i] = itemsBean.period;
					arrs[i] = Integer.valueOf(itemsBean.ball);
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						initCharView();
					}
				});
			}
		},0);

	}

	private void initCharView() {
		intent = getIntent();
		mChart = (CombinedChart) findViewById(R.id.chart1);
		mChart.setDescription("");
		//设置背景颜色
		mChart.setBackgroundColor(Color.WHITE);
		mChart.setDrawGridBackground(true);
		mChart.setDrawBarShadow(true);
		mChart.setMaxVisibleValueCount(100);
		mChart.getAxisRight().setEnabled(false);

		// draw bars behind lines
		mChart.setDrawOrder(new DrawOrder[] { DrawOrder.BAR, DrawOrder.BUBBLE,
				DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER });

		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setDrawGridLines(false);

		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setDrawGridLines(false);

		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		Map<String, Object> map = null;
		for (int i = 0; i < mTimes.length; i++) {
			map = new HashMap<String, Object>();
			map.put("addDay",mTimes[i]);
			month.add(map);
		}

		Map<String, Object> dataMap = null;
		for (int i = 0; i < arrs.length; i ++) {
			dataMap = new HashMap<String, Object>();
			dataMap.put("learnCount", arrs[i]);
			data.add(dataMap);
		}

		// 横向显示的时间 12/3 12/4
		for (int index = 0; index < month.size(); index++) {
			Map<String, Object> cdata = month.get(index);

			months.add(cdata.get("addDay").toString());
		}

		CombinedData data = new CombinedData(months);

		data.setData(generateLineData());
		// data.setData(generateBarData());
		// //data.setData(generateBubbleData());
		// data.setData(generateScatterData());
		// data.setData(generateCandleData());

		mChart.setData(data);
		mChart.invalidate();
	}

	private LineData generateLineData() {

		LineData d = new LineData();

		ArrayList<Entry> entries = new ArrayList<>();
		// 纵向显示的数据
		for (int index = 0; index < data.size(); index++) {
			Map<String, Object> cdata = data.get(index);

			entries.add(new Entry(Integer.parseInt(cdata.get("learnCount")
					.toString()), index));
		}
		LineDataSet set = new LineDataSet(entries,
				intent.getStringExtra("days") + "PC蛋蛋走势图");
		set.setColor(Color.rgb(40, 40, 40));
		set.setLineWidth(2.5f);
		set.setCircleColor(Color.rgb(40, 40, 40));
		set.setCircleSize(5f);
		set.setFillColor(Color.rgb(40, 40, 40));
		set.setDrawCubic(true);
		set.setDrawValues(true);
		set.setValueTextSize(10f);
		set.setValueTextColor(Color.rgb(40, 40, 40));

		set.setAxisDependency(YAxis.AxisDependency.LEFT);

		d.addDataSet(set);

		return d;
	}

	private BarData generateBarData() {

		BarData d = new BarData();

		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

		for (int index = 0; index < itemcount; index++)
			entries.add(new BarEntry(getRandom(15, 30), index));

		BarDataSet set = new BarDataSet(entries, "Bar DataSet");
		set.setColor(Color.rgb(60, 220, 78));
		set.setValueTextColor(Color.rgb(60, 220, 78));
		set.setValueTextSize(10f);
		d.addDataSet(set);

		set.setAxisDependency(YAxis.AxisDependency.LEFT);

		return d;
	}

	protected ScatterData generateScatterData() {

		ScatterData d = new ScatterData();

		ArrayList<Entry> entries = new ArrayList<Entry>();

		for (int index = 0; index < itemcount; index++)
			entries.add(new Entry(getRandom(20, 15), index));

		ScatterDataSet set = new ScatterDataSet(entries, "Scatter DataSet");
		set.setColor(Color.GREEN);
		set.setScatterShapeSize(7.5f);
		set.setDrawValues(false);
		set.setValueTextSize(10f);
		d.addDataSet(set);

		return d;
	}

	protected CandleData generateCandleData() {

		CandleData d = new CandleData();

		ArrayList<CandleEntry> entries = new ArrayList<CandleEntry>();

		for (int index = 0; index < itemcount; index++)
			entries.add(new CandleEntry(index, 20f, 10f, 13f, 17f));
		CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
		set.setColor(Color.rgb(80, 80, 80));
		set.setBodySpace(0.3f);
		set.setValueTextSize(10f);
		set.setDrawValues(false);
		d.addDataSet(set);
		return d;
	}

	protected BubbleData generateBubbleData() {
		BubbleData bd = new BubbleData();
		ArrayList<BubbleEntry> entries = new ArrayList<BubbleEntry>();
		for (int index = 0; index < itemcount; index++) {
			float rnd = getRandom(20, 30);
			entries.add(new BubbleEntry(index, rnd, rnd));
		}
		BubbleDataSet set = new BubbleDataSet(entries, "Bubble DataSet");
		set.setColors(ColorTemplate.VORDIPLOM_COLORS);
		set.setValueTextSize(10f);
		set.setValueTextColor(Color.WHITE);
		set.setHighlightCircleWidth(1.5f);
		set.setDrawValues(true);
		bd.addDataSet(set);
		return bd;
	}
	private float getRandom(float range, float startsfrom) {
		return (float) (Math.random() * range) + startsfrom;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
}
