package com.example.surveyandroidsecurity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;

import com.dueeeke.tablayout.CommonTabLayout;
import com.dueeeke.tablayout.listener.CustomTabEntity;
import com.dueeeke.tablayout.listener.OnTabSelectListener;
import com.dueeeke.tablayout.utils.UnreadMsgUtils;
import com.dueeeke.tablayout.widget.MsgView;

import java.util.ArrayList;
import java.util.Random;

public class AppListActivity extends AppCompatActivity {
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    private String[] mTitles = {"Social", "Entretenimiento", "Multimedia", "Productividad","Otros"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_u_social_icon, R.drawable.ic_u_games_icon,
            R.drawable.ic_u_multimedia_icon, R.drawable.ic_u_office_icon,R.drawable.ic_u_games_icon};
    private int[] mIconSelectIds = {
            R.drawable.ic_social_icon, R.drawable.ic_games_icon,
            R.drawable.ic_multimedia_icon, R.drawable.ic_office_icon, R.drawable.ic_otros_icon};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_tab);

        mFragments.add(UserAppsFragment.getInstance( 0));
        mFragments.add(UserAppsFragment.getInstance( 1));
        mFragments.add(UserAppsFragment.getInstance( 2));
        mFragments.add(UserAppsFragment.getInstance( 3));
        mFragments.add(UserAppsFragment.getInstance( 4));

           // mFragments2.add(SimpleCardFragment.getInstance("Switch Fragment " + title));



        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mDecorView = getWindow().getDecorView();

        Display display = getWindowManager().getDefaultDisplay();

        display.getHeight();
        System.out.println("tamaño de pantalla"+display.getHeight());
        mViewPager = ViewFindUtils.find(mDecorView, R.id.vp_2);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        /** with nothing */

        /** with ViewPager */
        mTabLayout_2 = ViewFindUtils.find(mDecorView, R.id.tl_2);
        /** with Fragments */



        tl_2();




        //显示未读红点



        //两位数
      //  mTabLayout_2.showMsg(0, 55);
       // mTabLayout_2.setMsgMargin(0, -5, 5);

        //三位数
      //  mTabLayout_2.showMsg(1, 100);
      //  mTabLayout_2.setMsgMargin(1, -5, 5);

        //设置未读消息红点
      //  mTabLayout_2.showDot(2);
        MsgView rtv_2_2 = mTabLayout_2.getMsgView(2);
        if (rtv_2_2 != null) {
            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
        }

        //设置未读消息背景
       mTabLayout_2.showMsg(3, 5);
        mTabLayout_2.setMsgMargin(3, 0, 5);
        MsgView rtv_2_3 = mTabLayout_2.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
    }

    Random mRandom = new Random();

    private void tl_2() {
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                System.out.println("xxx");
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                   // mTabLayout_2.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("scrolled");
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("page selected");
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            System.out.println("fragment");
            return mFragments.get(position);

        }
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
