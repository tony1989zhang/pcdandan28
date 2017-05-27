package com.xlidfwsscai525.module.suoshui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xlidfwsscai525.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class SuoShuiListAct extends Activity {
    private static final String SUOSHUI_STR = "SUOSHUI_STR";

    public static void OnNewInstance(Activity act,ArrayList<Plsz> plszArrayList){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SUOSHUI_STR, (Serializable)plszArrayList);
        intent.putExtras(bundle);
        intent.setClass(act,SuoShuiListAct.class);
        act.startActivity(intent);
    }
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.layout_suoshuilt);
        ArrayList<Plsz> plszArrayList = (ArrayList<Plsz>) getIntent().getSerializableExtra(SUOSHUI_STR);
         mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new MyAdapter(plszArrayList));
    }

    class MyAdapter extends BaseAdapter{
        ArrayList<Plsz> plszArrayList;
        public MyAdapter(ArrayList<Plsz> plszArrayList){
            this.plszArrayList = plszArrayList;
        }

        @Override
        public int getCount() {
            return plszArrayList.size();
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
            ViewHolder viewHolder = null;
            if (convertView == null)
            {
                viewHolder = new ViewHolder();
                inflate = getLayoutInflater().inflate(R.layout.layout_twostart_item, null);
                viewHolder.mTv_xh = (TextView) inflate.findViewById(R.id.tv_xh);
                viewHolder.mTv_num = (TextView) inflate.findViewById(R.id.tv_num);
                viewHolder.mTv_dxb = (TextView) inflate.findViewById(R.id.tv_dxb);
                viewHolder.mTv_job = (TextView) inflate.findViewById(R.id.tv_job);
                viewHolder.mTv_heji = (TextView) inflate.findViewById(R.id.tv_heji);
                inflate.setTag(viewHolder);
            }else{
                inflate = convertView;
                viewHolder = (ViewHolder) inflate.getTag();
            }
            Plsz plsz = plszArrayList.get(position);
            viewHolder.mTv_xh.setText("" + position);
            viewHolder.mTv_num.setText("" +plsz.thesingle + ":" + plsz.thedouble );

            viewHolder.mTv_dxb.setText("" +  UtilsSF.DaXiao(plsz));
            viewHolder.mTv_job.setText("" + UtilsSF.JiOu(plsz));
            viewHolder.mTv_heji.setText("" + UtilsSF.HeZhi(plsz));

            return inflate;
        }

        class ViewHolder{
            TextView mTv_xh;
            TextView  mTv_num;
            TextView  mTv_dxb ;
            TextView mTv_job;
            TextView  mTv_heji;
        }
    }
}
