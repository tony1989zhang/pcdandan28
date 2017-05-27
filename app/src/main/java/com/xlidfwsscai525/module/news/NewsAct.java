package com.xlidfwsscai525.module.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.base.BaseAct;
import com.xlidfwsscai525.base.BaseListAct;
import com.xlidfwsscai525.base.BasePageAdapter;
import com.xlidfwsscai525.entity.NewsEntity;
import com.xlidfwsscai525.fm.Fm3;
import com.xlidfwsscai525.http.CM;
import com.xlidfwsscai525.http.CUtil;
import com.xlidfwsscai525.module.suoshui.WebAct;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class NewsAct extends BaseListAct<NewsEntity> {
    @Override
    protected List convertToBeanList(NewsEntity newsEntity) {
        return newsEntity.msg;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleV(mTitleView,"资讯");
    }

    @Override
    protected BasePageAdapter initAdapter() {
        return new NewsAdapter();
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
        return CM.getInstance().news(this);
    }

    @Override
    protected boolean isPageEnabled() {
        return true;
    }

    @Override
    protected boolean isDataGot() {
        return false;
    }

    class NewsAdapter extends BasePageAdapter {

        @Override
        protected RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
            View inflate = LayoutInflater.from(NewsAct.this).inflate(R.layout.msg_listview_item, viewGroup, false);
            return new ItemViewHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {

            if (viewHoder instanceof ItemViewHodler) {
                final ItemViewHodler holder = (ItemViewHodler) viewHoder;
               final NewsEntity.MsgBean msgBean = (NewsEntity.MsgBean) mItems.get(position);
                holder.m_msg_author.setText(msgBean.summary);
                CUtil.loadImage(holder.m_msg_pic,msgBean.logofile);
                holder.m_msg_title.setText(msgBean.title);
                holder.m_msg_time.setText(msgBean.publishdate);
                holder.ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WebAct.onNewInstance(NewsAct.this,msgBean.url,msgBean.title);
                    }
                });
            }
        }
    }

    class ItemViewHodler extends RecyclerView.ViewHolder {
        @ViewInject(R.id.msg_pic)
        private ImageView m_msg_pic;

        @ViewInject(R.id.msg_title)
        private TextView m_msg_title; //福利彩票;

        @ViewInject(R.id.msg_author)
        private TextView m_msg_author; //福利彩票福利彩票福利彩票福利彩票福利彩票福利彩票福利彩票福利彩票福利彩票福利彩票福利;

        @ViewInject(R.id.msg_time)
        private TextView m_msg_time; //2017-04-18 11:01:02;
        @ViewInject(R.id.ll)
        private View ll;


        public ItemViewHodler(View itemView) {
            super(itemView);
            x.view().inject(this, itemView);
        }
    }
}
