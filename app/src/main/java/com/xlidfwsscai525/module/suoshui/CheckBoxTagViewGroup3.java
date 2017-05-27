package com.xlidfwsscai525.module.suoshui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.xlidfwsscai525.R;

import wang.yuchao.android.library.view.tagviewgroup.TagViewGroup;


/**
 * Created by wangyuchao on 16/4/27.
 */
public class CheckBoxTagViewGroup3 extends TagViewGroup<CheckBox, String> {

    public CheckBoxTagViewGroup3(Context context) {
        super(context);
    }

    public CheckBoxTagViewGroup3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public CheckBox getTagItemView(int position, String tag) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tag_view_group_default_check_box3, null);
        //tag布局不能包含在别的布局中，否则会报错 The specified child already has a parent. You must call removeView() on the child's parent first.
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.tag);
        checkBox.setText(tag);
        return checkBox;
    }
}
