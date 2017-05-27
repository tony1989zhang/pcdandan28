package com.xlidfwsscai525.module.plan;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.base.BaseAct;
import com.xlidfwsscai525.entity.PlanEntity;
import com.xlidfwsscai525.tools.StringUtils;
import com.xlidfwsscai525.tools.XgoLog;
import com.xlidfwsscai525.view.TitleView;
import com.xlidfwsscai525.view.ToastManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

@ContentView(R.layout.act_plan)
public class PlanAct extends BaseAct {
    @ViewInject(R.id.title_view)
    private TitleView m_title_view;
    @ViewInject(R.id.line_et)
    private LineEditText m_et_line;
    @ViewInject(R.id.line_et2)
    private LineEditText m_et2_line;
    @ViewInject(R.id.line_et3)
    private  LineEditText m_et3_line;
    @ViewInject(R.id.listview_plan)
    private ListView m_listview_plan;
    @Override
    protected void initView() {
        super.initView();
        setTitleV(m_title_view,"追号计划");
    }

    @Event(R.id.btn_plan)
    private void btnPlan(View v)
    {
        String et1String = m_et_line.getText().toString();
        String et2String = m_et2_line.getText().toString();
        String et3String = m_et3_line.getText().toString();
        if (StringUtils.isEmpty(et1String))
        {
            ToastManager.getManager().show("单注中奖奖金不能为空");
            return;
        }
        if (StringUtils.isEmpty(et2String))
        {
            ToastManager.getManager().show("最低盈利金额不能为空");
            return;
        }
        if (StringUtils.isEmpty(et3String))
        {
            ToastManager.getManager().show("单注中奖奖金不能为空");
            return;
        }
        int everyRateWin = Integer.valueOf(et1String);
        if (everyRateWin > 1000000000){
            ToastManager.getManager().show("金额太大了无法计算");
            return;
        }
        int lowestWin = Integer.valueOf(et2String);
        if (lowestWin > 1000000000){
            ToastManager.getManager().show("金额太大了无法计算");
            return;
        }
        int chaseCount = Integer.valueOf(et3String);
        if (chaseCount > 365){
            ToastManager.getManager().show("期数不能大于365");
            return;
        }
        ArrayList<PlanEntity> planEntities = setPlan(everyRateWin, lowestWin, chaseCount);
        XgoLog.e("planEntities:" + planEntities.toString());
        if (planEntities != null && planEntities.size() > 0)
        {
            m_listview_plan.setAdapter(new PlanAdapter(planEntities));
        }
    }

    private   ArrayList<PlanEntity>  setPlan(int everyRateWin,int lowestWin,int chaseCount){
        ArrayList<PlanEntity> planEntityArrayList = new ArrayList<PlanEntity>();
        int  sumCost = 0;
        for (int i = 0;i < chaseCount; i++){
            int rate = (lowestWin + sumCost)/(everyRateWin - 2)+1;
            if ((lowestWin+sumCost)%(everyRateWin-2) == 0) {
                rate--;
            }
            int  win = rate*everyRateWin - sumCost - 2*rate;
            sumCost += 2*rate;

            PlanEntity planEntity = new PlanEntity();
            planEntity.qs = i +1;
            planEntity.rate = rate;
            planEntity.bqtrate = 2* rate;
            planEntity.sumCost = sumCost;
            planEntity.win = win;
            planEntityArrayList.add(planEntity);
        }
        return planEntityArrayList;
    }

    class PlanAdapter extends BaseAdapter{
        ArrayList<PlanEntity> planEntities;
         public PlanAdapter(ArrayList<PlanEntity> planEntities){
             this.planEntities = planEntities;
         }
        @Override
        public int getCount() {
            return planEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = null;
            ViewHodler viewHodler = null;
            if (convertView == null) {
                inflate = getLayoutInflater().inflate(R.layout.item_plan, null);
                 viewHodler = new ViewHodler();
                viewHodler.m_tv_qs = (TextView) inflate.findViewById(R.id.tv_qs);
                viewHodler.m_tv_rate = (TextView) inflate.findViewById(R.id.tv_rate);
                viewHodler.m_tv_bqtrate = (TextView) inflate.findViewById(R.id.tv_bqtrate);
                viewHodler.m_tv_sumCost = (TextView) inflate.findViewById(R.id.tv_sumCost);
                viewHodler.m_tv_win = (TextView) inflate.findViewById(R.id.tv_win);
                inflate.setTag(viewHodler);
            }else{
                inflate = convertView;
                viewHodler = (ViewHodler) inflate.getTag();
            }
            PlanEntity planEntity = planEntities.get(position);
            viewHodler.m_tv_qs.setText("" + planEntity.qs);
            viewHodler.m_tv_rate.setText("" + planEntity.rate);
            viewHodler.m_tv_bqtrate.setText("" + planEntity.bqtrate);
            viewHodler.m_tv_sumCost.setText("" + planEntity.sumCost);
            viewHodler.m_tv_win.setText("" + planEntity.win);
            XgoLog.e("planEntity:" + planEntity.toString());
            return inflate;
        }

        class ViewHodler{
            private TextView m_tv_qs;

            private TextView m_tv_rate;

            private TextView m_tv_bqtrate;

            private TextView m_tv_sumCost;

            private TextView m_tv_win;


        }
    }
}
