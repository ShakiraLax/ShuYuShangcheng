package com.sypm.shuyushangcheng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jorge.circlelibrary.ImageCycleView;
import com.sypm.shuyushangcheng.moudle.search.SearchActivity;
import com.sypm.shuyushangcheng.utils.BaseFragment;
import com.sypm.shuyushangcheng.utils.BaseListAdapter;
import com.sypm.shuyushangcheng.utils.HorizontalListView;
import com.sypm.shuyushangcheng.utils.HorizontalListViewAdapter;
import com.sypm.shuyushangcheng.utils.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */
public class IndexFragment extends BaseFragment {

    HorizontalListView mHorizontalListView;
    HorizontalListViewAdapter mAdapter;
    List<String> mData;
    NoScrollGridView gridView;
    ImageCycleView viewPager;
    LinearLayout searchView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //允许刷新按钮
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHorizontalListView = (HorizontalListView) getView().findViewById(R.id.list);
        gridView = (NoScrollGridView) getView().findViewById(R.id.gridView);
        viewPager = (ImageCycleView) getView().findViewById(R.id.viewPager);
        searchView = (LinearLayout) getView().findViewById(R.id.search);
        initView();
    }

    private void initView() {
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                startActivity(intent);
            }
        });
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add("" + i);
        }
        mAdapter = new HorizontalListViewAdapter(getActivity(), mData);
        mHorizontalListView.setAdapter(mAdapter);
        viewPager.setCycle_T(ImageCycleView.CYCLE_T.CYCLE_VIEW_NORMAL);
        ArrayList<String> imageDescList = new ArrayList<>();
        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("http://img.sypm.cn/advertise/6dc66d2a9c152123a9267ad463e49037/22be38af2db7a2546e6c285a44c0eea6.png");
        urlList.add("http://img.sypm.cn/advertise/6dc66d2a9c152123a9267ad463e49037/9921a5e0732708e839bc5997de79401d.png");
        urlList.add("http://img.sypm.cn/advertise/6dc66d2a9c152123a9267ad463e49037/921adcb7868f02e0fd38e43121835b1c.png");
        imageDescList.add("1");
        imageDescList.add("2");
        imageDescList.add("3");
        gridView.setAdapter(new GridViewAdapter(getActivity(), mData));

        mHorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("横向listView点击事件", "" + id);
                Toast.makeText(getActivity(), "listView item id：" + id, Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "gridView item id：" + id, Toast.LENGTH_SHORT).show();
            }
        });
        initCarsuelView(imageDescList, urlList);
    }

    /**
     * 初始化轮播图
     */
    public void initCarsuelView(ArrayList<String> imageDescList, ArrayList<String> urlList) {
        LinearLayout.LayoutParams cParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getScreenHeight(getActivity()) * 3 / 10);
        viewPager.setLayoutParams(cParams);
        ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void onImageClick(int position, View imageView) {
                /**实现点击事件*/
                Toast.makeText(getActivity(), "position=" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                /**在此方法中，显示图片，可以用自己的图片加载库，也可以用本demo中的（Imageloader）*/
                /**
                 * 此处使用Glide
                 */
                Glide.with(getActivity()).load(imageURL).into(imageView);
            }
        };
        /**设置数据*/
        viewPager.setImageResources(imageDescList, urlList, mAdCycleViewListener);
        // 是否隐藏底部
        viewPager.hideBottom(true);
        viewPager.startImageCycle();
    }

    /**
     * 得到屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (null == context) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    private static class GridViewAdapter extends BaseListAdapter {

        public GridViewAdapter(Context context, List list) {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_grid_list, parent, false);
            }
            return convertView;
        }
    }


}
