package com.sypm.shuyushangcheng.moudle.goods;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sypm.shuyushangcheng.R;
import com.sypm.shuyushangcheng.base.BaseActivity;
import com.sypm.shuyushangcheng.ui.GoodsViewGroup;
import com.sypm.shuyushangcheng.ui.MyViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GoodsDetailActivity extends BaseActivity implements GoodsViewGroup.OnGroupItemClickListener, View.OnClickListener {

    private GoodsViewGroup<TextView> mGroup;
    private TextView goShopping;
    private ArrayList<String> viewtexts = new ArrayList<>();
    private int chooseID = -1;
    private String chooseText;

    private MyViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        initView();
        setupViewPager();
    }

    private void initView() {
        String text;
        for (int i = 0; i < 4; i++) {
            text = "规格" + i;
            viewtexts.add(text);
        }
        mGroup = (GoodsViewGroup) findViewById(R.id.goodsView);
        goShopping = (TextView) findViewById(R.id.goShopping);
        mGroup.addItemViews(viewtexts, GoodsViewGroup.TEV_MODE);
        mGroup.setGroupClickListener(this);
        goShopping.setOnClickListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (MyViewPager) findViewById(R.id.viewPager2);
        count = (TextView) findViewById(R.id.count);
    }

    private void setupViewPager() {
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(1);
        mTabLayout.setTabTextColors(Color.parseColor("#242424"), Color.parseColor("#fc982d"));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);

        animateTabTextWithReflex(mViewPager.getCurrentItem());
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                animateTabTextWithReflex(tab.getPosition());
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //通过反射来拿到TabView
    public void animateTabTextWithReflex(int selectedTabPosition) {
        try {
            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                Field field = TabLayout.Tab.class.getDeclaredField("mView");
                field.setAccessible(true);
                Object tabView = field.get(mTabLayout.getTabAt(i));
                if (tabView instanceof LinearLayout) {
                    for (int j = 0; j < ((LinearLayout) tabView).getChildCount(); j++) {
                        View text = ((LinearLayout) tabView).getChildAt(j);
                        if (text instanceof TextView) {
                            if (selectedTabPosition == i) {
                                text.animate()
                                        .setDuration(150)
                                        .scaleX(1.23f)
                                        .scaleY(1.23f)
                                        .start();
                            } else {
                                text.animate()
                                        .setDuration(150)
                                        .scaleX(1.0f)
                                        .scaleY(1.0f)
                                        .start();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Reflex", "e=" + e.toString());
        }
    }

    private static class MyViewPagerAdapter extends FragmentPagerAdapter {

        private String[] titles = {"说明书", "详情页", "评价"};

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new InstructionFragment();
                    break;
                case 1:
                    fragment = new GoodsDetailFragment();
                    break;
                case 2:
                    fragment = new ReviewFragment();
                    break;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }

    @Override
    public void onClick(View v) {
        if (chooseID >= 0) {
            showToast("ID:" + chooseID + "  text:" + chooseText);
        } else {
            showToast("请选择");
        }
    }

    @Override
    public void onGroupItemClick(int item) {
        chooseID = item;
        chooseText = mGroup.getChooseText(item);
    }

    private void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

}
