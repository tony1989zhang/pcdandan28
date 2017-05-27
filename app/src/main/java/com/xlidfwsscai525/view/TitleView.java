package com.xlidfwsscai525.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.tools.StringUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 作者：V先生 on 2016/8/3 09:05
 * 作用：主标题
 */
public class TitleView extends LinearLayout {
    @ViewInject(R.id.title_view_root)
    private RelativeLayout m_title_view_root;

    @ViewInject(R.id.title_back)
    private ImageView m_title_back;

    @ViewInject(R.id.title_left)
    private TextView m_title_left;

    @ViewInject(R.id.title_name)
    private TextView m_title_name;

    @ViewInject(R.id.title_right)
    private TextView m_title_right;

    @ViewInject(R.id.title_right_img)
    private ImageView m_title_right_img;

    @ViewInject(R.id.tv_em_count)
    private TextView m_tv_em_count;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
       LayoutInflater.from(context).inflate(R.layout.act_title, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        x.view().inject(this);
        ViewGroup.LayoutParams param = m_title_view_root.getLayoutParams();
        param.height = getResources().getDimensionPixelSize(R.dimen.title_height);
        m_title_view_root.setLayoutParams(param);
    }

    public void setTitleBackImg(int dr){
        m_title_back.setImageResource(dr);
    }
    public void setTitle(int resid){
        setTitle(this.getResources().getString(resid));
    }


    public void setEmCount(String countStr) {
        if (!StringUtils.isEmpty(countStr)) {
            m_tv_em_count.setVisibility(View.VISIBLE);
            m_tv_em_count.setText(countStr);
        }else{
            m_tv_em_count.setVisibility(View.GONE);
        }
    }
    public void setTitle(String title){
        if(m_title_name != null){
            m_title_name.setText(title);
        }
    }

    public void setTitleBackVisibility(int visibility){
        if(m_title_back != null){
            m_title_back.setVisibility(visibility);
        }
    }

    public void setTitleRightText(String text){
        m_title_right_img.setVisibility(View.GONE);
        if(m_title_right != null){
            if(TextUtils.isEmpty(text)){
                m_title_right.setVisibility(View.GONE);
            } else {
                m_title_right.setText(text);
                m_title_right.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setTitleLeftText(String text){
        if (null != m_title_back) {
            m_title_back.setVisibility(View.GONE);
        }
        if(m_title_left != null){
            if(TextUtils.isEmpty(text)){
                m_title_left.setVisibility(View.GONE);
            } else {
                m_title_left.setText(text);
                m_title_left.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setTitleRightImg(int resid){
        m_title_right.setVisibility(View.GONE);
        if(m_title_right_img != null){
            if(resid < 0){
                m_title_right_img.setVisibility(View.GONE);
            } else {
                m_title_right_img.setImageResource(resid);
                m_title_right_img.setVisibility(View.VISIBLE);
            }
        }
    }


    public void setTitleBackOnClickListener(OnClickListener listener){
        if(m_title_back != null){
            m_title_back.setOnClickListener(listener);
        }
        if(m_title_back != null){
            m_title_back.setOnClickListener(listener);
        }
    }

    public void setTitleRightOnClickListener(OnClickListener listener){
        if(m_title_right != null){
            m_title_right.setOnClickListener(listener);
        }
        if(m_title_right_img != null){
            m_title_right_img.setOnClickListener(listener);
        }
    }

    public void setTitleBackgroundColor(int color){
        m_title_view_root.setBackgroundColor(color);
        if (color == getResources().getColor(R.color.transparent)) {
            m_title_view_root.getBackground().setAlpha(100);
        }
    }
    public void setStatusBarTopInsert(int topMargin){
        final LayoutParams param = (LayoutParams) m_title_view_root.getLayoutParams();
        param.height = topMargin + this.getResources().getDimensionPixelSize(R.dimen.title_height);
        m_title_view_root.setLayoutParams(param);
    }

}


