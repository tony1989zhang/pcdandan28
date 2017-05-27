package com.xlidfwsscai525;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xlidfwsscai525.base.BaseFmAct;
import com.xlidfwsscai525.fm.Fm0;
import com.xlidfwsscai525.fm.Fm1;
import com.xlidfwsscai525.fm.Fm2;
import com.xlidfwsscai525.fm.Fm3;
import com.xlidfwsscai525.fm.Fm4;
import com.xlidfwsscai525.http.CM;
import com.xlidfwsscai525.view.BanSlidingViewPage;
import com.xlidfwsscai525.view.TitleView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseFmAct {
    @ViewInject(R.id.title_view)
    TitleView mTitleView;
    @ViewInject(R.id.banSlidingView)
    private BanSlidingViewPage m_banSlidingView;
    @ViewInject(R.id.iv0)
    private ImageView iv0;
    @ViewInject(R.id.iv1)
    private ImageView iv1;
    @ViewInject(R.id.iv2)
    private ImageView iv2;
    @ViewInject(R.id.iv3)
    private ImageView iv3;
    @ViewInject(R.id.iv4)
    private ImageView iv4;

    @ViewInject(R.id.tv0)
    private TextView tv0;
    @ViewInject(R.id.tv1)
    private TextView tv1;
    @ViewInject(R.id.tv2)
    private TextView tv2;
    @ViewInject(R.id.tv3)
    private TextView tv3;
    @ViewInject(R.id.tv4)
    private TextView tv4;
    @ViewInject(R.id.title_back)
    private ImageView m_title_back;
    private ArrayList<Fragment> mFraments = new ArrayList<>();
    int currentIndex = 0;
    @ViewInject(R.id.draw)
    private DrawerLayout mDrawerLayout;
    @ViewInject(R.id.nav_view)
    private NavigationView mNavigationView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleView(mTitleView,getResources().getString(R.string.app_name));

        m_title_back.setVisibility(View.VISIBLE);
        m_title_back.setImageResource(R.mipmap.title_black);

        mFraments.add(new Fm0());
        mFraments.add(new Fm1());
        mFraments.add(new Fm3());
        mFraments.add(new Fm2());
        mFraments.add(new Fm4());
        ItineraryFragmentAdapter itineraryFragmentAdapter = new ItineraryFragmentAdapter(getSupportFragmentManager());
        m_banSlidingView.setAdapter(itineraryFragmentAdapter);
//        m_banSlidingView.setOnPageChangeListener(new ItineraryOnPageChangeListener());
        m_banSlidingView.setOffscreenPageLimit(0);
        m_banSlidingView.setCurrentItem(currentIndex);
        m_banSlidingView.setScanScroll(false);
        iv0.setImageResource(R.drawable.tab_found_on);
        tv0.setTextColor(getResources().getColor(R.color.title_red));
        //m_tv_quote.setTextColor(getResources().getColor(R.color.swipe_refrsh_color3));
        setupDrawerContent();
    }

    private void setupDrawerContent() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    public void selectDrawerItem(MenuItem menuItem) {

        String url = "";
        String urlTitle = "";
        switch (menuItem.getItemId()) {
            case R.id.nav_first_yyjj:
                url =  CM.APP_JIAN_JIE;
                urlTitle = "应用简介";
                break;
            case R.id.nav_second_help:
                url = CM.APP_HELP;
                urlTitle = "帮助中心";
                break;
            case R.id.nav_third_mz:

                url = CM.APP_MZSM;
                urlTitle = "免责声明";
                break;
            case R.id.nav_fourth_about:
                url = CM.APP_ABOUT;
                urlTitle = "关于";
                break;
            default:
        }
        com.xlidfwsscai525.module.suoshui.WebAct.onNewInstance(MainActivity.this,url,urlTitle);
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }
    class ItineraryFragmentAdapter extends FragmentPagerAdapter {

        public ItineraryFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFraments.get(position);
        }

        @Override
        public int getCount() {
            return mFraments.size();
        }
    }
    @Event({R.id.ll0,R.id.ll1,R.id.ll2,R.id.ll3,R.id.ll4,R.id.title_back})
    private void ItineraryOnClick(View v) {

        switch (v.getId()) {
            case R.id.ll0:
                currentIndex = 0;
                iv0.setImageResource(R.drawable.tab_found_on);
                tv0.setTextColor(getResources().getColor(R.color.title_red));
                iv1.setImageResource(R.drawable.tab_2_off);
                tv1.setTextColor(getResources().getColor(R.color.white));
                iv2.setImageResource(R.drawable.tab_3_off);
                tv2.setTextColor(getResources().getColor(R.color.white));
                iv3.setImageResource(R.drawable.tab_4_off);
                tv3.setTextColor(getResources().getColor(R.color.white));
                iv4.setImageResource(R.drawable.tab_5_off);
                tv4.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.ll1:
                currentIndex = 1;
                iv0.setImageResource(R.drawable.tab_found_off);
                tv0.setTextColor(getResources().getColor(R.color.white));
                iv1.setImageResource(R.drawable.tab_2_on);
                tv1.setTextColor(getResources().getColor(R.color.title_red));
                iv2.setImageResource(R.drawable.tab_3_off);
                tv2.setTextColor(getResources().getColor(R.color.white));
                iv3.setImageResource(R.drawable.tab_4_off);
                tv3.setTextColor(getResources().getColor(R.color.white));
                iv4.setImageResource(R.drawable.tab_5_off);
                tv4.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.ll2:
                currentIndex = 2;
                iv0.setImageResource(R.drawable.tab_found_off);
                tv0.setTextColor(getResources().getColor(R.color.white));
                iv1.setImageResource(R.drawable.tab_2_off);
                tv1.setTextColor(getResources().getColor(R.color.white));
                iv2.setImageResource(R.drawable.tab_3_on);
                tv2.setTextColor(getResources().getColor(R.color.title_red));
                iv3.setImageResource(R.drawable.tab_4_off);
                tv3.setTextColor(getResources().getColor(R.color.white));
                iv4.setImageResource(R.drawable.tab_5_off);
                tv4.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.ll3:
                currentIndex = 3;
                iv0.setImageResource(R.drawable.tab_found_off);
                tv0.setTextColor(getResources().getColor(R.color.white));
                iv1.setImageResource(R.drawable.tab_2_off);
                tv1.setTextColor(getResources().getColor(R.color.white));
                iv2.setImageResource(R.drawable.tab_3_off);
                tv2.setTextColor(getResources().getColor(R.color.white));
                iv3.setImageResource(R.drawable.tab_4_on);
                tv3.setTextColor(getResources().getColor(R.color.title_red));
                iv4.setImageResource(R.drawable.tab_5_off);
                tv4.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.ll4:
                currentIndex = 4;
                iv0.setImageResource(R.drawable.tab_found_off);
                tv0.setTextColor(getResources().getColor(R.color.white));
                iv1.setImageResource(R.drawable.tab_2_off);
                tv1.setTextColor(getResources().getColor(R.color.white));
                iv2.setImageResource(R.drawable.tab_3_off);
                tv2.setTextColor(getResources().getColor(R.color.white));
                iv3.setImageResource(R.drawable.tab_4_off);
                tv3.setTextColor(getResources().getColor(R.color.white));
                iv4.setImageResource(R.drawable.tab_5_on);
                tv4.setTextColor(getResources().getColor(R.color.title_red));
                break;
            case R.id.title_back:
                openNavDrawer();
                break;
        }
       // ToastManager.getManager().show("" +  currentIndex);
        m_banSlidingView.setCurrentItem(currentIndex);
    }

    public void setCurrent(int currentIndex)
    {    this.currentIndex = currentIndex;
        switch (currentIndex) {
            case 0:
                iv0.setImageResource(R.drawable.tab_found_on);
                tv0.setTextColor(getResources().getColor(R.color.title_red));
                iv1.setImageResource(R.drawable.tab_2_off);
                tv1.setTextColor(getResources().getColor(R.color.white));
                iv2.setImageResource(R.drawable.tab_3_off);
                tv2.setTextColor(getResources().getColor(R.color.white));
                iv3.setImageResource(R.drawable.tab_4_off);
                tv3.setTextColor(getResources().getColor(R.color.white));
                iv4.setImageResource(R.drawable.tab_5_off);
                tv4.setTextColor(getResources().getColor(R.color.white));
                break;
            case 1:
                iv0.setImageResource(R.drawable.tab_found_off);
                tv0.setTextColor(getResources().getColor(R.color.white));
                iv1.setImageResource(R.drawable.tab_2_on);
                tv1.setTextColor(getResources().getColor(R.color.title_red));
                iv2.setImageResource(R.drawable.tab_3_off);
                tv2.setTextColor(getResources().getColor(R.color.white));
                iv3.setImageResource(R.drawable.tab_4_off);
                tv3.setTextColor(getResources().getColor(R.color.white));
                iv4.setImageResource(R.drawable.tab_5_off);
                tv4.setTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                iv0.setImageResource(R.drawable.tab_found_off);
                tv0.setTextColor(getResources().getColor(R.color.white));
                iv1.setImageResource(R.drawable.tab_2_off);
                tv1.setTextColor(getResources().getColor(R.color.white));
                iv2.setImageResource(R.drawable.tab_3_on);
                tv2.setTextColor(getResources().getColor(R.color.title_red));
                iv3.setImageResource(R.drawable.tab_4_off);
                tv3.setTextColor(getResources().getColor(R.color.white));
                iv4.setImageResource(R.drawable.tab_5_off);
                tv4.setTextColor(getResources().getColor(R.color.white));
                break;
            case 3:
                iv0.setImageResource(R.drawable.tab_found_off);
                tv0.setTextColor(getResources().getColor(R.color.white));
                iv1.setImageResource(R.drawable.tab_2_off);
                tv1.setTextColor(getResources().getColor(R.color.white));
                iv2.setImageResource(R.drawable.tab_3_off);
                tv2.setTextColor(getResources().getColor(R.color.white));
                iv3.setImageResource(R.drawable.tab_4_on);
                tv3.setTextColor(getResources().getColor(R.color.title_red));
                iv4.setImageResource(R.drawable.tab_5_off);
                tv4.setTextColor(getResources().getColor(R.color.white));
                break;
            case 4:
                iv0.setImageResource(R.drawable.tab_found_off);
                tv0.setTextColor(getResources().getColor(R.color.white));
                iv1.setImageResource(R.drawable.tab_2_off);
                tv1.setTextColor(getResources().getColor(R.color.white));
                iv2.setImageResource(R.drawable.tab_3_off);
                tv2.setTextColor(getResources().getColor(R.color.white));
                iv3.setImageResource(R.drawable.tab_4_off);
                tv3.setTextColor(getResources().getColor(R.color.white));
                iv4.setImageResource(R.drawable.tab_5_on);
                tv4.setTextColor(getResources().getColor(R.color.title_red));
                break;
        }
        // ToastManager.getManager().show("" +  currentIndex);
        m_banSlidingView.setCurrentItem(currentIndex);
    }

    private void openNavDrawer(){
        if (!isNavDrawerOpen())
            mDrawerLayout.openDrawer(GravityCompat.START);
        else
            closeNavDrawer();
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
