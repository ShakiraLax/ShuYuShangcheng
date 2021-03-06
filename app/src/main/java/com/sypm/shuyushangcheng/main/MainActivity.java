package com.sypm.shuyushangcheng.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sypm.shuyushangcheng.R;
import com.sypm.shuyushangcheng.base.FragmentManagerActivity;


public class MainActivity extends FragmentManagerActivity {

    private static final int INDEX = 0;

    private static final int CLASSIFY = 1;

    private static final int SHOPPINGCART = 2;

    private static final int MY = 3;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (savedInstanceState == null) {
            addFragment(INDEX);
        }
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setCustomView(createCustomTab("首页", R.drawable.main_tab_one)), true);
        tabLayout.addTab(tabLayout.newTab().setCustomView(createCustomTab("分类", R.drawable.main_tab_two)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createCustomTab("购物车", R.drawable.main_tab_three)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createCustomTab("我的", R.drawable.main_tab_four)));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case INDEX:
                        addFragment(INDEX);
                        break;
                    case CLASSIFY:
                        addFragment(CLASSIFY);
                        break;
                    case SHOPPINGCART:
                        addFragment(SHOPPINGCART);
                        break;
                    case MY:
                        addFragment(MY);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    View createCustomTab(String text, int iconRes) {
        View view = getLayoutInflater().inflate(R.layout.view_custom_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.tabText);
        ImageView imageView = (ImageView) view.findViewById(R.id.tabIcon);
        textView.setText(text);
        imageView.setImageResource(iconRes);
        return view;
    }


    @Override
    public Fragment createFragment(int index) {
        switch (index) {
            case INDEX:
                return new IndexFragment();
            case CLASSIFY:
                return new ClassifyFragment();
            case SHOPPINGCART:
                return new ShoppingCartFragment();
            case MY:
                return new MyFragment();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    private long exitTime = 0;

    //再按一次退出
    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出漱玉商城！", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
