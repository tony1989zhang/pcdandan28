package com.xlidfwsscai525.module.suoshui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xlidfwsscai525.R;
import com.xlidfwsscai525.view.TitleView;

import java.util.ArrayList;
import java.util.Collections;

import wang.yuchao.android.library.view.tagviewgroup.OnTagCheckedChangeListener;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class TwoStartAct extends Activity implements View.OnClickListener {
    ArrayList<String> mNullData;
    ArrayList<String> mData ;
    ArrayList<String> mDataOne ;
    ArrayList<String> mDataTwo;
    ArrayList<String> mDataChoose;
    CheckBoxTagViewGroup mCheckBoxTagViewGroup;
    CheckBoxTagViewGroup mCheckBoxTagViewGroupOne;
    CheckBoxTagViewGroup mCheckBoxTagViewGroupTwo;
    CheckBoxTagViewGroup2 checkboxTagsBigorsmail;
    CheckBoxTagViewGroup2 checkboxTagsOddoreven;
    TextView mTvTJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_twostart);
        initView();
        initData();
        initmCheckBoxTagView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initmCheckBoxTagView() {
        if (mCheckBoxTagViewGroup == null)
        mCheckBoxTagViewGroup = (CheckBoxTagViewGroup) findViewById(R.id.checkbox_tags_view_group);
        if (mCheckBoxTagViewGroupOne == null)
        mCheckBoxTagViewGroupOne = (CheckBoxTagViewGroup) findViewById(R.id.checkbox_tags_view_group_one);
        if (mCheckBoxTagViewGroupTwo == null)
        mCheckBoxTagViewGroupTwo = (CheckBoxTagViewGroup) findViewById(R.id.checkbox_tags_view_group_two);
        mCheckBoxTagViewGroup.setSingleLine(true);
        mCheckBoxTagViewGroupOne.setSingleLine(true);
        mCheckBoxTagViewGroupTwo.setSingleLine(true);
        mCheckBoxTagViewGroup.setOnTagCheckedChangeListener(new CheckBoxDefaultOnTagListener());
        mCheckBoxTagViewGroupOne.setOnTagCheckedChangeListener(new CheckBoxOneOnTagListener());
        mCheckBoxTagViewGroupTwo.setOnTagCheckedChangeListener(new CheckBoxTwoOnTagListener());
        mCheckBoxTagViewGroup.update(mData);  //添加
//        checkBoxTagViewGroup.update(allText); //移除
        //复选框
        ArrayList<String>  checkboxBigOrSmailLists = new ArrayList<>();
        checkboxBigOrSmailLists.add("大大");
        checkboxBigOrSmailLists.add("大小");
        checkboxBigOrSmailLists.add("小小");
        checkboxBigOrSmailLists.add("小大");

        ArrayList<String> checbkoxOddOrEventLists = new ArrayList<>();
        checbkoxOddOrEventLists.add("奇奇");
        checbkoxOddOrEventLists.add("奇偶");
        checbkoxOddOrEventLists.add("偶偶");
        checbkoxOddOrEventLists.add("偶奇");
         checkboxTagsBigorsmail = (CheckBoxTagViewGroup2) findViewById(R.id.checkbox_tags_bigorsmail);
        checkboxTagsBigorsmail.update(checkboxBigOrSmailLists);
         checkboxTagsOddoreven = (CheckBoxTagViewGroup2) findViewById(R.id.checkbox_tags_oddoreven);
        checkboxTagsOddoreven.update(checbkoxOddOrEventLists);

    }

    private void initData() {
        if (mNullData == null)
        { mNullData = new ArrayList<>();}
        if (mData == null)
        { mData = new ArrayList<>();}
        if (mDataOne == null)
        {mDataOne = new ArrayList<>();}
        if (mDataTwo == null)
        {mDataTwo = new ArrayList<>();}
        if (mDataChoose == null)
        { mDataChoose = new ArrayList<>();}
        mData.clear();
        mDataChoose.clear();
        mDataOne.clear();
        mDataTwo.clear();
        mDataChoose.clear();

        for (int i = 0; i < 10; i++) {
            mData.add("" + i);
        }
    }

    private void initView() {
        TitleView titleView = (TitleView) findViewById(R.id.title_view);
        titleView.setTitleBackVisibility(View.VISIBLE);
        titleView.setTitle("二星缩水");
        titleView.setTitleBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwoStartAct.this.finish();
            }
        });
        mTvTJ = (TextView) findViewById(R.id.tv_tj);
        findViewById(R.id.tv_base_big).setOnClickListener(new BaseOnClickListener());
        findViewById(R.id.tv_base_small).setOnClickListener(new BaseOnClickListener());
        findViewById(R.id.tv_base_odd).setOnClickListener(new BaseOnClickListener());
        findViewById(R.id.tv_base_even).setOnClickListener(new BaseOnClickListener());
        findViewById(R.id.tv_base_all).setOnClickListener(new BaseOnClickListener());
        findViewById(R.id.tv_base_no).setOnClickListener(new BaseOnClickListener());

        findViewById(R.id.tv_one_big).setOnClickListener(new OneBigOnClickListener());
        findViewById(R.id.tv_one_smail).setOnClickListener(new OneBigOnClickListener());
        findViewById(R.id.tv_one_odd).setOnClickListener(new OneBigOnClickListener());
        findViewById(R.id.tv_one_even).setOnClickListener(new OneBigOnClickListener());
        findViewById(R.id.tv_one_all).setOnClickListener(new OneBigOnClickListener());
        findViewById(R.id.tv_one_no).setOnClickListener(new OneBigOnClickListener());

        findViewById(R.id.tv_two_big).setOnClickListener(new TwoBigOnClickListener());
        findViewById(R.id.tv_two_smail).setOnClickListener(new TwoBigOnClickListener());
        findViewById(R.id.tv_two_odd).setOnClickListener(new TwoBigOnClickListener());
        findViewById(R.id.tv_two_even).setOnClickListener(new TwoBigOnClickListener());
        findViewById(R.id.tv_two_all).setOnClickListener(new TwoBigOnClickListener());
        findViewById(R.id.tv_two_no).setOnClickListener(new TwoBigOnClickListener());
        findViewById(R.id.tv_yl).setOnClickListener(this);
        findViewById(R.id.tv_clear).setOnClickListener(this);
        findViewById(R.id.tv_ss).setOnClickListener(this);
        findViewById(R.id.tv_jc).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_yl:
                if (mDataChoose.size() == 0 && mDataOne.size() == 0 && mDataTwo.size() == 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TwoStartAct.this)
                            .setMessage("至少要设置基础号码");
                    dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    setTvTj();
                }
                break;
            case R.id.tv_clear:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("您确认要清空数据");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initData();
                        initmCheckBoxTagView();
                        mCheckBoxTagViewGroupOne.update(mNullData);
                        mCheckBoxTagViewGroupTwo.update(mNullData);
                        setTvTj();
                        dialog.dismiss();
                    }
                });
                builder.show();
                //清空

                break;
            case R.id.tv_ss:
                setSuoShui();

                break;
            case R.id.tv_jc:
                //教程
                WebAct.onNewInstance(this,"http://www.zjt-cp.com/html/filterInstructionForApp.html?lotteryCategory=Lssc&os=android&appVersion=6.2.1705&appName=Android_ssczs&visitFrom=outside","缩水教程");
                break;

        }
    }

    private void setSuoShui() {
        mIsSetFilterBigOrSmail = false;
        mIsSetFilterOddOrEven = false;
        ArrayList<Plsz> plszArrayList = UtilsSF.SuoShui(mDataOne, mDataTwo);
        Log.e("plszArrayList", "plszArrayList:" + plszArrayList.toString());
        ArrayList<Plsz> plszArrayList1 = setfilterBigOrSmail(plszArrayList);
        Log.e("plszArrayList1","plszArrayList1:" + plszArrayList1.toString());
        ArrayList<Plsz> plszArrayList2 = setfilterOddOrEven(plszArrayList);
        Log.e("plszArrayList2","plszArrayList2:" + plszArrayList2.toString());
        finish();
        startActivity(new Intent(this,TwoStartAct.class));
        if (mIsSetFilterBigOrSmail&&mIsSetFilterOddOrEven){
            plszArrayList1.retainAll(plszArrayList2);
            SuoShuiListAct.OnNewInstance(this, plszArrayList1);
        }else if(mIsSetFilterBigOrSmail && !mIsSetFilterOddOrEven){
            SuoShuiListAct.OnNewInstance(this, plszArrayList1);
        }else if(!mIsSetFilterBigOrSmail &&mIsSetFilterOddOrEven){
            SuoShuiListAct.OnNewInstance(this, plszArrayList2);
        }else if(!mIsSetFilterBigOrSmail && !mIsSetFilterOddOrEven){
            SuoShuiListAct.OnNewInstance(this, plszArrayList);
        }
    }

    boolean mIsSetFilterBigOrSmail = false;
    boolean mIsSetFilterOddOrEven = false;
    private ArrayList<Plsz> setfilterBigOrSmail(ArrayList<Plsz> plszArrayList) {
        ArrayList<Plsz> bsFilterLists = new ArrayList<>();
            CheckBox cbBB = (CheckBox) checkboxTagsBigorsmail.getChildAt(0);
        if (cbBB.isChecked())
        {
            ArrayList<Plsz> plszBB = UtilsSF.getPlszBB(plszArrayList);
            bsFilterLists.addAll(plszBB);
            mIsSetFilterBigOrSmail = true;
        }
            CheckBox cbBS = (CheckBox) checkboxTagsBigorsmail.getChildAt(1);
        if (cbBS.isChecked())
        {
            ArrayList<Plsz> plszBS = UtilsSF.getPlszBS(plszArrayList);
            bsFilterLists.addAll(plszBS);
            mIsSetFilterBigOrSmail = true;
        }
            CheckBox cbSS = (CheckBox) checkboxTagsBigorsmail.getChildAt(2);
        if (cbSS.isChecked())
        {
            ArrayList<Plsz> plszSS = UtilsSF.getPlszBS(plszArrayList);
            bsFilterLists.addAll(plszSS);
            mIsSetFilterBigOrSmail = true;
        }
            CheckBox cbSB = (CheckBox) checkboxTagsBigorsmail.getChildAt(3);
        if (cbSB.isChecked())
        {
            ArrayList<Plsz> plszSB = UtilsSF.getPlszSB(plszArrayList);
            bsFilterLists.addAll(plszSB);
            mIsSetFilterBigOrSmail = true;
        }
        return bsFilterLists;
    }

    private ArrayList<Plsz> setfilterOddOrEven(ArrayList<Plsz> plszArrayList) {
        ArrayList<Plsz> oEFilterLists = new ArrayList<>();
        CheckBox cboo = (CheckBox) checkboxTagsOddoreven.getChildAt(0);
        if (cboo.isChecked())
        {
            ArrayList<Plsz> plszList = UtilsSF.getPlszOO(plszArrayList);
            oEFilterLists.addAll(plszList);
            mIsSetFilterOddOrEven = true;
        }

        CheckBox cboe = (CheckBox) checkboxTagsOddoreven.getChildAt(1);
        if (cboe.isChecked())
        {
            ArrayList<Plsz> plszList = UtilsSF.getPlszOE(plszArrayList);
            oEFilterLists.addAll(plszList);
            mIsSetFilterOddOrEven = true;
        }

        CheckBox cbee = (CheckBox) checkboxTagsOddoreven.getChildAt(2);
        if (cbee.isChecked())
        {
            ArrayList<Plsz> plszList = UtilsSF.getPlszEE(plszArrayList);
            oEFilterLists.addAll(plszList);
            mIsSetFilterOddOrEven = true;
        }

        CheckBox cbeo = (CheckBox) checkboxTagsOddoreven.getChildAt(3);
        if (cbeo.isChecked())
        {
            ArrayList<Plsz> plszList = UtilsSF.getPlszEO(plszArrayList);
            oEFilterLists.addAll(plszList);
            mIsSetFilterOddOrEven = true;
        }

        return oEFilterLists;
    }

    //条件预览
    private void setTvTj() {
        //条件预览
        String basetjString = "[基础号码]已选:\n" + mDataChoose.toString();
        String onetjString = "[一位选号]已选:\n" + mDataOne.toString();
        String twojString = "[二位选号]已选:\n" + mDataTwo.toString();
//        mTvTJ.setText(basetjString + "\n" + onetjString + "\n" + twojString);
        AlertDialog.Builder dialog = new AlertDialog.Builder(TwoStartAct.this)
                .setTitle("条件预览")
                .setMessage(basetjString + "\n" + onetjString + "\n" + twojString);
        dialog.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton("开始缩水", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSuoShui();
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    class OneBigOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mDataOne.clear();
            switch (v.getId()) {
                case R.id.tv_one_big:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        String s = mDataChoose.get(i);
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupOne.getChildAt(i);
                        if ((Integer.valueOf(s) > 4)) {
                            if (!checkBox.isChecked())
                           checkBox.setChecked(true);
                            else
                                mDataOne.add(s);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }
                    break;
                case R.id.tv_one_smail:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        String s = mDataChoose.get(i);
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupOne.getChildAt(i);
                        if (Integer.valueOf(s) < 5 ) {
                            //mDataOne.add(s);
                            if (!checkBox.isChecked())
                            checkBox.setChecked(true);
                            else
                                mDataOne.add(s);
                        } else {
                            checkBox.setChecked(false);
                        }
                        Log.e("data.size","data.size" + mDataOne.size());
                    }
                    break;
                case R.id.tv_one_odd:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        String s = mDataChoose.get(i);
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupOne.getChildAt(i);
                        if (Integer.valueOf(s) % 2 != 0 ) {
                            if (!checkBox.isChecked())
                            checkBox.setChecked(true);
                            else mDataOne.add(s);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }
                    break;
                case R.id.tv_one_even:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupOne.getChildAt(i);
                        String s = mDataChoose.get(i);
                        if (Integer.valueOf(s) % 2 == 0) {
                            if (!checkBox.isChecked())
                            checkBox.setChecked(true);
                            else mDataOne.add(s);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }
                    break;
                case R.id.tv_one_all:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupOne.getChildAt(i);
                        String s = mDataChoose.get(i);
                            if (!checkBox.isChecked())
                                checkBox.setChecked(true);
                            else mDataOne.add(s);
                    }
                    break;
                case R.id.tv_one_no:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupOne.getChildAt(i);
                        String s = mDataChoose.get(i);
                        if (checkBox.isChecked())
                            checkBox.setChecked(false);
                        mDataOne.clear();
                    }
                    break;
            }
            Collections.sort(mDataOne);
            //   setDataChoose();
        }
    }

    class TwoBigOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mDataTwo.clear();
            switch (v.getId()) {
                case R.id.tv_two_big:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        String s = mDataChoose.get(i);
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupTwo.getChildAt(i);
                        if (Integer.valueOf(s) > 4 ) {
                            if (!checkBox.isChecked())
                            checkBox.setChecked(true);
                            else
                                mDataTwo.add(s);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }
                    break;
                case R.id.tv_two_smail:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        String s = mDataChoose.get(i);
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupTwo.getChildAt(i);
                        if (Integer.valueOf(s) < 5 ) {
                            if (!checkBox.isChecked())
                            checkBox.setChecked(true);
                            else
                                mDataTwo.add(s);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }
                    break;
                case R.id.tv_two_odd:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        String s = mDataChoose.get(i);
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupTwo.getChildAt(i);
                        if (Integer.valueOf(s) % 2 != 0 ) {
                            if (!checkBox.isChecked())
                            checkBox.setChecked(true);
                            else
                                mDataTwo.add(s);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }
                    break;
                case R.id.tv_two_even:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupTwo.getChildAt(i);
                        String s = mDataChoose.get(i);
                        if (Integer.valueOf(s) % 2 == 0 ) {
                            if (!checkBox.isChecked())
                            checkBox.setChecked(true);
                            else mDataTwo.add(s);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }
                    break;
                case R.id.tv_two_all:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupTwo.getChildAt(i);
                        String s = mDataChoose.get(i);
                        if (!checkBox.isChecked())
                            checkBox.setChecked(true);
                        else mDataTwo.add(s);
                    }
                    break;
                case R.id.tv_two_no:
                    for (int i = 0; i < mDataChoose.size(); i++) {
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroupTwo.getChildAt(i);
                        String s = mDataChoose.get(i);
                        if (checkBox.isChecked())
                            checkBox.setChecked(false);
                        mDataTwo.clear();
                    }
            }
            Collections.sort(mDataTwo);
        }
    }

    class BaseOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            mDataChoose.clear();
            mDataOne.clear();
            mDataTwo.clear();
            switch (v.getId()) {
                case R.id.tv_base_big:
                    for (int i = 0; i < 10; i++) {
                        String s = mData.get(i);
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroup.getChildAt(i);
                        if (Integer.valueOf(s) > 4) {
                            mDataChoose.add(s);
                            checkBox.setChecked(true);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }

                    break;
                case R.id.tv_base_small:
                    for (int i = 0; i < 10; i++) {
                        String s = mData.get(i);
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroup.getChildAt(i);
                        if (Integer.valueOf(s) < 5) {
                            mDataChoose.add(s);
                            checkBox.setChecked(true);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }

                    break;
                case R.id.tv_base_odd: //奇
                    for (int i = 0; i < 10; i++) {
                        String s = mData.get(i);

                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroup.getChildAt(i);
                        if (Integer.valueOf(s) % 2 != 0) {
                            mDataChoose.add(s);
                            checkBox.setChecked(true);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }

                    break;
                case R.id.tv_base_even://偶
                    for (int i = 0; i < 10; i++) {
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroup.getChildAt(i);
                        String s = mData.get(i);

                        if (Integer.valueOf(s) % 2 == 0) {
                            mDataChoose.add(s);
                            checkBox.setChecked(true);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }
                    break;
                case R.id.tv_base_all:
                    for (int i = 0; i < 10; i++) {
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroup.getChildAt(i);
                        String s = mData.get(i);

                        if (!checkBox.isChecked()){
                            mDataChoose.add(s);
                            checkBox.setChecked(true);
                        }
                    }
                    break;
                case R.id.tv_base_no:
                    for (int i = 0; i < 10; i++) {
                        CheckBox checkBox = (CheckBox) mCheckBoxTagViewGroup.getChildAt(i);
                        mDataChoose.clear();
                        if (checkBox.isChecked())
                        checkBox.setChecked(false);

                    }
                    break;
            }

            setDataChoose();
        }
    }


    class CheckBoxDefaultOnTagListener implements OnTagCheckedChangeListener {

        @Override
        public void onTagCheckedChanged(CompoundButton compoundButton, boolean b, int position, Object tag) {
            String tagString = (String) tag;
            Toast.makeText(getApplicationContext(), "复选：第" + position + "个，值为：" + tagString + "值为:" + b, Toast.LENGTH_SHORT).show();
            if (b) {
                if (mDataChoose.contains(tagString))
                    ;
                else
                    mDataChoose.add(tagString);
            } else {
                mDataChoose.remove(tagString);
            }
            setDataChoose();

        }
    }


    private void setDataChoose() {
        mDataOne.clear();
        mDataTwo.clear();
        Collections.sort(mDataChoose);
        mCheckBoxTagViewGroupOne.update(mDataChoose);
        mCheckBoxTagViewGroupTwo.update(mDataChoose);
    }

    class CheckBoxOneOnTagListener implements OnTagCheckedChangeListener {

        @Override
        public void onTagCheckedChanged(CompoundButton compoundButton, boolean b, int position, Object tag) {
            String tagString = (String) tag;
            if (b)
                mDataOne.add(tagString);
            else
                mDataOne.remove(tagString);

            Collections.sort(mDataOne);
        }
    }

    class CheckBoxTwoOnTagListener implements OnTagCheckedChangeListener {

        @Override
        public void onTagCheckedChanged(CompoundButton compoundButton, boolean b, int position, Object tag) {
            String tagString = (String) tag;
            if (b)
                mDataTwo.add(tagString);
            else
                mDataTwo.remove(tagString);

            Collections.sort(mDataTwo);
        }
    }
}
