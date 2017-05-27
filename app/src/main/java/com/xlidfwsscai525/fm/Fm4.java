package com.xlidfwsscai525.fm;

import android.view.View;
import android.widget.TextView;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.base.BaseFm;
import com.xlidfwsscai525.entity.HotcoldnumberEntity;
import com.xlidfwsscai525.http.CM;
import com.xlidfwsscai525.http.ComCb;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

@ContentView(R.layout.layout_fm4)
public class Fm4 extends BaseFm {


    @ViewInject(R.id.tv_1)
    private TextView m_tv_1; //冠军;

    @ViewInject(R.id.tv_2)
    private TextView m_tv_2; //亚军;

    @ViewInject(R.id.tv_3)
    private TextView m_tv_3; //第三名;

    @ViewInject(R.id.tv_4)
    private TextView m_tv_4; //第四名;

    @ViewInject(R.id.tv_5)
    private TextView m_tv_5; //第五名;

    @ViewInject(R.id.tag1)
    private com.xlidfwsscai525.view.TagGroup m_tag1;

    @ViewInject(R.id.tag2)
    private com.xlidfwsscai525.view.TagGroup m_tag2;

    @ViewInject(R.id.tag3)
    private com.xlidfwsscai525.view.TagGroup m_tag3;
     ArrayList<TextView> mTvLists = new ArrayList<>();
    @Override
    public void initView(View view) {
        super.initView(view);
        x.view().inject(view);
        mTvLists.clear();
        mTvLists.add(m_tv_1);
        mTvLists.add(m_tv_2);
        mTvLists.add(m_tv_3);
        mTvLists.add(m_tv_4);
        mTvLists.add(m_tv_5);
        m_tv_1.setTextColor(getResources().getColor(R.color.title_red));
        getTags(0);
    }

    @Event({R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5})
    private void fmOnClick(View v) {
        int num = 0;
        switch (v.getId()) {
            case R.id.tv_1:
                num = 0;
                break;
            case R.id.tv_2:
                num = 1;
                break;
            case R.id.tv_3:
                num = 2;
                break;
            case R.id.tv_4:
                num = 3;
                break;
            case R.id.tv_5:
                num = 4;
                break;
        }
        for (int i = 0;i < mTvLists.size();i++)
        {
            if (v.getId() == mTvLists.get(i).getId())
            {
                mTvLists.get(i).setTextColor(getResources().getColor(R.color.title_red));
            }else{
                mTvLists.get(i).setTextColor(getResources().getColor(R.color.text_default_color));
            }
        }
        getTags(num);
    }

    public void getTags(int num) {
        showProgressDailog();
        CM.getInstance().hotcoldnumber(new F4ComCb(), num);
    }

    class F4ComCb extends ComCb<HotcoldnumberEntity> {
        @Override
        public void onSuccess(HotcoldnumberEntity result) {
            super.onSuccess(result);

            List<String> tags1 = result.itemArray.get(0);
            List<String> tags2 = result.itemArray.get(1);
            List<String> tags3 = result.itemArray.get(2);
            m_tag1.setTags(tags1);
            m_tag2.setTags(tags2);
            m_tag3.setTags(tags3);


        }

        @Override
        public void onFinished() {
            super.onFinished();
            dismissProgressDialog();
        }
    }
}
