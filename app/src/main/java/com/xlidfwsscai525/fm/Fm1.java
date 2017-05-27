package com.xlidfwsscai525.fm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.base.BaseListFm;
import com.xlidfwsscai525.base.BasePageAdapter;
import com.xlidfwsscai525.entity.Pk10Entity2;
import com.xlidfwsscai525.http.CM;
import com.xlidfwsscai525.tools.StringUtils;
import com.xlidfwsscai525.tools.XgoLog;
import com.xlidfwsscai525.view.CustomPopWindow;
import com.google.gson.Gson;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class Fm1 extends BaseListFm<String> implements View.OnClickListener {
    private TextView mTv1;
    private TextView mTv2;
    private CustomPopWindow mCustomPopWindow;
    private int dateNum = 0;

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        mTv1 = (TextView) root.findViewById(R.id.tv1);
        mTv2 = (TextView) root.findViewById(R.id.tv2);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv1.setTextColor(getResources().getColor(R.color.title_red));
        mTv2.setTextColor(getResources().getColor(R.color.text_default_color));
    }

    @Override
    protected List convertToBeanList(String xml) {
        XgoLog.e("xml:" + xml);
        Pk10Entity2 pk10Entity2 = new Gson().fromJson(xml, Pk10Entity2.class);
        List<List<String>> itemArray = pk10Entity2.itemArray;
        return itemArray;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fm1;
    }


    /* @Override
    protected List convertToBeanList(String loginBean) {
        XgoLog.d("loginBean:" + loginBean);
        HashMap<String,Pk10EntityLists> map = new HashMap<>();
        Pk10EntityLists pk10EntityLists = new Pk10EntityLists();
        map.put("Pk10EntityLists",pk10EntityLists);
        Pk10EntityLists pk10EntityLists2 = (Pk10EntityLists) StringUtils.xml2Bean(map, loginBean);
        XgoLog.e("o:" + pk10EntityLists2.toString());
        return pk10EntityLists2.getPk10EntityArrayList();
    }*/

    @Override
    protected BasePageAdapter initAdapter() {
        return new FmAdapter();
    }

    @Override
    protected boolean isSwipeRefreshLayoutEnabled() {
        return true;
    }

    @Override
    protected int getSizeInPage() {
        return 0;
    }

    @Override
    protected Cancelable initRequest(int start) {
        return CM.getInstance().getList(getBackDate()[dateNum],this);
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
                dateNum = 0;
                mTv1.setTextColor(getResources().getColor(R.color.title_red));
                mTv2.setTextColor(getResources().getColor(R.color.text_default_color));
                onRefresh();
                break;
            case R.id.tv2:
                showPopMenu();
               mTv1.setTextColor(getResources().getColor(R.color.text_default_color));
                mTv2.setTextColor(getResources().getColor(R.color.title_red));

                break;
        }
    }

    public String[] getBackDate() {
        int backDate[] = {0, -1, -2, -3, -4};
        String[] backDateStr = new String[5];
        for (int i = 0;i < 5;i++){
            Date date = new Date();//取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, backDate[i]);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime(); //这个时间就是日期往后推一天的结果
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            backDateStr[i] = dateString;
        }
        return backDateStr;
    }


    class FmAdapter extends BasePageAdapter {
        @Override
        protected RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = View.inflate(viewGroup.getContext(), R.layout.item_quote, null);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_item, viewGroup, false);
            return new ItemViewHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {
            if (viewHoder instanceof ItemViewHodler) {
                ItemViewHodler holder = (ItemViewHodler) viewHoder;
                List<String> item = (List<String>) mItems.get(position);
                holder.mTv_1.setText("" + item.get(2));
                holder.mTv_2.setText("" + item.get(1));
                int[] split = StringUtils.StringtoInt(item.get(3));
                holder.mTv_31.setText("" + split[0]);
                setTvBg(split[0], holder.mTv_31);
                holder.mTv_32.setText("" + split[1]);
                setTvBg(split[1], holder.mTv_32);
                holder.mTv_33.setText("" + split[2]);
                setTvBg(split[2], holder.mTv_33);
                holder.mTv_34.setText("" + split[3]);
                setTvBg(split[3], holder.mTv_34);
                holder.mTv_35.setText("" + split[4]);
                setTvBg(split[4], holder.mTv_35);
                holder.mTv_36.setText("" + split[5]);
                setTvBg(split[5], holder.mTv_36);
                holder.mTv_37.setText("" + split[6]);
                setTvBg(split[6], holder.mTv_37);
                holder.mTv_38.setText("" + split[7]);
                setTvBg(split[7], holder.mTv_38);
                holder.mTv_39.setText("" + split[8]);
                setTvBg(split[8], holder.mTv_39);
                holder.mTv_310.setText("" + split[9]);
                setTvBg(split[9], holder.mTv_310);
            }
        }

        public void setTvBg(int i, TextView tv) {
            switch (i) {
                case 1:
                    tv.setBackgroundResource(R.drawable.shape_solid_purple);
                    break;
                case 2:
                    tv.setBackgroundResource(R.drawable.shape_solid_blue);
                    break;
                case 3:
                    tv.setBackgroundResource(R.drawable.shape_solid_cyan);
                    break;
                case 4:
                    tv.setBackgroundResource(R.drawable.shape_solid_cygreen);
                    break;
                case 5:
                    tv.setBackgroundResource(R.drawable.shape_solid_cyan);
                    break;
                case 6:
                    tv.setBackgroundResource(R.drawable.shape_solid_dgreen);
                    break;
                case 7:
                    tv.setBackgroundResource(R.drawable.shape_solid_dcyan);
                    break;
                case 8:
                    tv.setBackgroundResource(R.drawable.shape_solid_dyellow);
                    break;
                case 9:
                    tv.setBackgroundResource(R.drawable.shape_solid_dblue);
                    break;
                case 10:
                    tv.setBackgroundResource(R.drawable.shape_solid_titlecolor);
                    break;
            }
        }

        class ItemViewHodler extends RecyclerView.ViewHolder {

            @ViewInject(R.id.tv_1)
            public TextView mTv_1;
            @ViewInject(R.id.tv_2)
            public TextView mTv_2;
            @ViewInject(R.id.tv_31)
            public TextView mTv_31;
            @ViewInject(R.id.tv_32)
            public TextView mTv_32;
            @ViewInject(R.id.tv_33)
            public TextView mTv_33;
            @ViewInject(R.id.tv_34)
            public TextView mTv_34;
            @ViewInject(R.id.tv_35)
            public TextView mTv_35;
            @ViewInject(R.id.tv_36)
            public TextView mTv_36;
            @ViewInject(R.id.tv_37)
            public TextView mTv_37;
            @ViewInject(R.id.tv_38)
            public TextView mTv_38;
            @ViewInject(R.id.tv_39)
            public TextView mTv_39;
            @ViewInject(R.id.tv_310)
            public TextView mTv_310;


            public ItemViewHodler(View itemView) {
                super(itemView);
                x.view().inject(this, itemView);
            }
        }
    }

    private void showPopMenu(){
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_menu,null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
         mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .create()
                .showAsDropDown(mTv2,0,20);


    }
    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){
       final String[] backDate = getBackDate();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.menu1:
                        showContent = backDate[0];
                        dateNum = 0;
                        break;
                    case R.id.menu2:
                        showContent = backDate[1];
                        dateNum = 1;
                        break;
                    case R.id.menu3:
                        showContent = backDate[2];
                        dateNum = 2;
                        break;
                    case R.id.menu4:
                        showContent = backDate[3];
                        dateNum = 3;
                        break;
                    case R.id.menu5:
                        showContent = backDate[4] ;
                        dateNum = 4;
                        break;
                }
                onRefresh();
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
        contentView.findViewById(R.id.menu4).setOnClickListener(listener);
        contentView.findViewById(R.id.menu5).setOnClickListener(listener);
        TextView tv1 = (TextView) contentView.findViewById(R.id.item_tv_1);
        TextView tv2 = (TextView) contentView.findViewById(R.id.item_tv_2);
        TextView tv3 = (TextView) contentView.findViewById(R.id.item_tv_3);
        TextView tv4 = (TextView) contentView.findViewById(R.id.item_tv_4);
        TextView tv5 = (TextView) contentView.findViewById(R.id.item_tv_5);

        tv1.setText("" + backDate[0]);
        tv2.setText("" + backDate[1]);
        tv3.setText("" + backDate[2]);
        tv4.setText("" + backDate[3]);
        tv5.setText("" + backDate[4]);

    }
}
