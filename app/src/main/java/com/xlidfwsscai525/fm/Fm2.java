package com.xlidfwsscai525.fm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xlidfwsscai525.Pk10OmitString;
import com.xlidfwsscai525.R;
import com.xlidfwsscai525.base.BaseListFm;
import com.xlidfwsscai525.base.BasePageAdapter;
import com.xlidfwsscai525.entity.HaoMaBean;
import com.xlidfwsscai525.entity.LaHmBean;
import com.xlidfwsscai525.entity.NumberBean;
import com.xlidfwsscai525.entity.Pk10_omit;
import com.xlidfwsscai525.http.CM;
import com.xlidfwsscai525.tools.XgoLog;
import com.google.gson.Gson;


import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class Fm2 extends BaseListFm<String> implements View.OnClickListener {
    private TextView m_tv1; //单号码;
    private TextView m_tv2; //冠亚和;
    private TextView m_t21; //冠;
    private TextView m_t22; //亚;
    private TextView m_t23; //三;
    private TextView m_t24; //四;
    private TextView m_t25; //五;
    private TextView m_t26; //六;
    private TextView m_t27; //七;
    private TextView m_t28; //八;
    private TextView m_t29; //九;
    private TextView m_t210; //十;

    private LinearLayout mF2_ll2;

    ArrayList<LaHmBean> mWeuList = new ArrayList<>();
    ArrayList<HaoMaBean> mGuanyahe = new ArrayList<>();
    ArrayList<TextView> mWeuTvList = new ArrayList<>();
    boolean mIsWeu = true;
    int mWeuNum = 0;
    int mGuanyaheNum = 0;

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        mWeuTvList.clear();

        mF2_ll2 = (LinearLayout) root.findViewById(R.id.f2_ll2);
        m_tv1 = (TextView) root.findViewById(R.id.tv1);
        m_tv1.setTextColor(getResources().getColor(R.color.title_red));
        m_tv2 = (TextView) root.findViewById(R.id.tv2);
        m_tv1.setOnClickListener(this);
        m_tv2.setOnClickListener(this);

        m_t21 = (TextView) root.findViewById(R.id.t21);
        m_t21.setTextColor(getResources().getColor(R.color.title_red));
        m_t22 = (TextView) root.findViewById(R.id.t22);
        m_t23 = (TextView) root.findViewById(R.id.t23);
        m_t24 = (TextView) root.findViewById(R.id.t24);
        m_t25 = (TextView) root.findViewById(R.id.t25);
        m_t26 = (TextView) root.findViewById(R.id.t26);
        m_t27 = (TextView) root.findViewById(R.id.t27);
        m_t28 = (TextView) root.findViewById(R.id.t28);
        m_t29 = (TextView) root.findViewById(R.id.t29);
        m_t210 = (TextView) root.findViewById(R.id.t210);

        mWeuTvList.add(m_t21);
        mWeuTvList.add(m_t22);
        mWeuTvList.add(m_t23);
        mWeuTvList.add(m_t24);
        mWeuTvList.add(m_t25);
        mWeuTvList.add(m_t26);
        mWeuTvList.add(m_t27);
        mWeuTvList.add(m_t28);
        mWeuTvList.add(m_t29);
        mWeuTvList.add(m_t210);

        for (int i = 0; i < 10; i++) {
            mWeuTvList.get(i).setOnClickListener(this);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fm2;
    }


    @Override
    protected List convertToBeanList(String s) {
        mWeuList.clear();
        mGuanyahe.clear();
        Pk10_omit pk10_omit = new Gson().fromJson(Pk10OmitString.PK10_OMIT, Pk10_omit.class);
        mWeuList.add(pk10_omit.root.weu.one);
        mWeuList.add(pk10_omit.root.weu.two);
        mWeuList.add(pk10_omit.root.weu.three);
        mWeuList.add(pk10_omit.root.weu.four);
        mWeuList.add(pk10_omit.root.weu.five);
        mWeuList.add(pk10_omit.root.weu.six);
        mWeuList.add(pk10_omit.root.weu.seven);
        mWeuList.add(pk10_omit.root.weu.eight);
        mWeuList.add(pk10_omit.root.weu.night);
        mWeuList.add(pk10_omit.root.weu.ten);
        mGuanyahe.add(pk10_omit.root.guanyahe.haoma);
        XgoLog.e("mGuanyahe:" + mGuanyahe.toString());
        if (mIsWeu) return mWeuList.get(mWeuNum).haoma.number;
        else return mGuanyahe.get(0).number;
    }

    @Override
    protected BasePageAdapter initAdapter() {
        return new Fm2Adapter();
    }

    @Override
    protected boolean isSwipeRefreshLayoutEnabled() {
        return false;
    }

    @Override
    protected int getSizeInPage() {
        return 0;
    }

    @Override
    protected Cancelable initRequest(int start) {
        return CM.getInstance().getOmit(this);
    }

    @Override
    protected boolean isPageEnabled() {
        return false;
    }

    @Override
    protected boolean isDataGot() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                m_tv1.setTextColor(getResources().getColor(R.color.title_red));
                m_tv2.setTextColor(getResources().getColor(R.color.text_default_color));
                mF2_ll2.setVisibility(View.VISIBLE);
                mIsWeu = true;
                break;
            case R.id.tv2:
                m_tv1.setTextColor(getResources().getColor(R.color.text_default_color));
                m_tv2.setTextColor(getResources().getColor(R.color.title_red));
                mF2_ll2.setVisibility(View.GONE);
                mIsWeu = false;
                break;
        }
        if (mIsWeu) {
            for (int i = 0; i < 10; i++) {
                TextView tv = mWeuTvList.get(i);
                if (tv.getId() == v.getId()) {
                    mWeuNum = i;
                } else {
                    tv.setTextColor(getResources().getColor(R.color.text_default_color));
                }
            }

            mWeuTvList.get(mWeuNum).setTextColor(getResources().getColor(R.color.title_red));
        }
        XgoLog.e("getId:" + getId());
        onRefresh();

    }

    class Fm2Adapter extends BasePageAdapter {

        @Override
        protected RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_item2, viewGroup, false);
            return new ItemViewHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {
            if (viewHoder instanceof ItemViewHodler) {
                ItemViewHodler holder = (ItemViewHodler) viewHoder;
                NumberBean numberBean = (NumberBean) mItems.get(position);
                XgoLog.e("numberBean" + numberBean.toString());
                if (position + 1 == 1)
                    holder.m_tv41.setBackgroundResource(R.drawable.shape_solid_titlecolor);
                else if (position + 1 == 2)
                    holder.m_tv41.setBackgroundResource(R.drawable.shape_solid_cyan);
                else if (position + 1 == 3)
                    holder.m_tv41.setBackgroundResource(R.drawable.shape_solid_cygreen);
                else if (position + 1 == 4)
                    holder.m_tv41.setBackgroundResource(R.drawable.shape_solid_dblue);
                else if (position + 1 == 5)
                    holder.m_tv41.setBackgroundResource(R.drawable.shape_solid_dyellow);
                else if (position + 1 == 6)
                    holder.m_tv41.setBackgroundResource(R.drawable.shape_solid_dcyan);
                else if (position + 1 == 7)
                    holder.m_tv41.setBackgroundResource(R.drawable.shape_solid_dgreen);
                else if (position + 1 == 8)
                    holder.m_tv41.setBackgroundResource(R.drawable.shape_solid_orange);
                else if (position + 1 == 9)
                    holder.m_tv41.setBackgroundResource(R.drawable.shape_solid_purple);
                else if (position + 1 == 10)
                    holder.m_tv41.setBackgroundResource(R.drawable.shape_solid_bpurple);
                holder.m_tv41.setText("" + numberBean.no);

                holder.m_tv42.setText("" + numberBean.appearrate);
                holder.m_tv43.setText("" + numberBean.aver_omit);
                holder.m_tv44.setText("" + numberBean.current_omit);
                holder.m_tv45.setText("" + numberBean.history_max_omit);
                holder.m_tv46.setText("" + numberBean.last_time_omit);
                holder.m_tv47.setText("" + numberBean.wishrate);
            }
        }

        class ItemViewHodler extends RecyclerView.ViewHolder {
            @ViewInject(R.id.tv41)
            private TextView m_tv41; //号码;

            @ViewInject(R.id.tv42)
            private TextView m_tv42; //出现\n占比;

            @ViewInject(R.id.tv43)
            private TextView m_tv43; //当前\n遗漏;

            @ViewInject(R.id.tv44)
            private TextView m_tv44; //上次\n遗漏;

            @ViewInject(R.id.tv45)
            private TextView m_tv45; //历史最大遗漏;

            @ViewInject(R.id.tv46)
            private TextView m_tv46; //平均\n遗漏;

            @ViewInject(R.id.tv47)
            private TextView m_tv47; //欲出\n机率;


            public ItemViewHodler(View itemView) {
                super(itemView);
                x.view().inject(this, itemView);
            }
        }
    }
}
