package com.xlidfwsscai525.fm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.base.BaseListFm;
import com.xlidfwsscai525.base.BasePageAdapter;
import com.xlidfwsscai525.entity.TwofaceanalysisEntity;
import com.xlidfwsscai525.http.CM;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class Fm3 extends BaseListFm<TwofaceanalysisEntity> {
    @Override
    protected List convertToBeanList(TwofaceanalysisEntity twofaceanalysisEntity) {
        return twofaceanalysisEntity.itemArray;
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fm3;
    }

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
        return CM.getInstance().twofaceanalysis(this);
    }

    @Override
    protected boolean isPageEnabled() {
        return false;
    }

    @Override
    protected boolean isDataGot() {
        return false;
    }

    class FmAdapter extends BasePageAdapter {

        @Override
        protected RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_item3, viewGroup, false);
            return new ItemViewHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {

            if (viewHoder instanceof ItemViewHodler) {
                ItemViewHodler holder = (ItemViewHodler) viewHoder;
                List<String> item = (List<String>) mItems.get(position);
                String[] split = item.get(0).split(",");
                holder.mTv_1.setText("" + split[0] );
                holder.mTv_2.setText("" + split[1]);
                holder.mTv_3.setText("" + item.get(1));
            }
        }
    }

    class ItemViewHodler extends RecyclerView.ViewHolder {
        @ViewInject(R.id.tv_1)
        public TextView mTv_1;
        @ViewInject(R.id.tv_2)
        public TextView mTv_2;
        @ViewInject(R.id.tv_3)
        public TextView mTv_3;

        public ItemViewHodler(View itemView) {
            super(itemView);
            x.view().inject(this, itemView);
        }
    }
}
