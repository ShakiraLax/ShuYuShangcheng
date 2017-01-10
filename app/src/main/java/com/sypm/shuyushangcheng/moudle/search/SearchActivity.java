package com.sypm.shuyushangcheng.moudle.search;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sypm.shuyushangcheng.R;
import com.sypm.shuyushangcheng.base.BaseActivity;
import com.sypm.shuyushangcheng.uiutils.FlowLayout;
import com.sypm.shuyushangcheng.uiutils.SearchListView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backPressed;

    private String[] mDatas = new String[]{"QQ", "视频", "WIFI万能钥匙", "播放器", "捕鱼达人2", "机票", "游戏", "熊出没之熊大快跑", "美图秀秀", "浏览器"};
    private FlowLayout flowLayout;
    private SearchListView listView;
    private TextView tv_clear;
    public static final String EXTRA_KEY_TYPE = "extra_key_type";
    public static final String EXTRA_KEY_KEYWORD = "extra_key_keyword";
    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";
    private SharedPreferences mPref;//使用SharedPreferences记录搜索历史
    private String mType;
    private EditText input;
    private ImageView btn_search;
    private List<String> mHistoryKeywords;//记录文本
    private ArrayAdapter<String> mArrAdapter;//搜索历史适配器
    private LinearLayout historyView;//搜索历史显示区域

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initFlowView();
        initHistoryView();
    }

    private void initView() {
        backPressed = (ImageView) findViewById(R.id.backPressed);
        backPressed.setOnClickListener(this);
        input = (EditText) findViewById(R.id.searchText);
        btn_search = (ImageView) findViewById(R.id.search_button);
        historyView = (LinearLayout) findViewById(R.id.history);
        flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        listView = (SearchListView) findViewById(R.id.listView);
    }

    /**
     * 流布局
     */
    private void initFlowView() {
        // 循环添加TextView到容器
        for (int i = 0; i < mDatas.length; i++) {
            final TextView view = new TextView(this);
            view.setText(mDatas[i]);
            view.setTextColor(Color.WHITE);
            view.setPadding(5, 5, 5, 5);
            view.setGravity(Gravity.CENTER);
            view.setTextSize(14);
            view.setTextColor(Color.BLACK);

            // 设置点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*直接进行搜索操作*/
                    input.setText(view.getText().toString());
                }
            });

            /*设置背景*/
            view.setBackgroundResource(R.drawable.search_classify_bg);

            flowLayout.addView(view);
        }
    }

    /**
     * 搜索历史
     */
    private void initHistoryView() {
        btn_search.setOnClickListener(this);
        mPref = getSharedPreferences("input", Activity.MODE_PRIVATE);
        mType = getIntent().getStringExtra(EXTRA_KEY_TYPE);
        String keyword = getIntent().getStringExtra(EXTRA_KEY_KEYWORD);
        if (!TextUtils.isEmpty(keyword)) {
            input.setText(keyword);
        }
        mHistoryKeywords = new ArrayList<String>();
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    if (mHistoryKeywords.size() > 0) {
                        historyView.setVisibility(View.VISIBLE);
                    } else {
                        historyView.setVisibility(View.GONE);
                    }
                } else {
                    historyView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initSearchHistory();
    }

    /**
     * 初始化搜索历史记录
     */
    public void initSearchHistory() {
        tv_clear.setOnClickListener(this);
        String history = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if (!TextUtils.isEmpty(history)) {
            List<String> list = new ArrayList<String>();
            for (Object o : history.split(",")) {
                list.add((String) o);
            }
            mHistoryKeywords = list;
        }
        if (mHistoryKeywords.size() > 0) {
            historyView.setVisibility(View.VISIBLE);
        } else {
            historyView.setVisibility(View.GONE);
        }
        mArrAdapter = new ArrayAdapter<String>(this, R.layout.item_search_history, mHistoryKeywords);
        listView.setAdapter(mArrAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                input.setText(mHistoryKeywords.get(i));
                historyView.setVisibility(View.GONE);
            }
        });
        mArrAdapter.notifyDataSetChanged();
    }

    /**
     * 储存搜索历史
     */
    public void save() {
        String text = input.getText().toString();
        String oldText = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");

        if (!TextUtils.isEmpty(text) && !(oldText.contains(text))) {
            if (mHistoryKeywords.size() > 10) {
                //最多保存条数
                return;
            }
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString(KEY_SEARCH_HISTORY_KEYWORD, text + "," + oldText);
            editor.commit();
            mHistoryKeywords.add(0, text);
        }
        mArrAdapter.notifyDataSetChanged();
    }

    /**
     * 清除历史纪录
     */
    public void cleanHistory() {
        mPref = getSharedPreferences("input", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove(KEY_SEARCH_HISTORY_KEYWORD).commit();
        mHistoryKeywords.clear();
        mArrAdapter.notifyDataSetChanged();
        historyView.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "清除搜索历史成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button:
                String keywords = input.getText().toString();
                if (!TextUtils.isEmpty(keywords)) {
                    Log.d("数据库操作", "保存成功" + keywords);
                    save();
                } else {
                    Toast.makeText(getActivity(), "请输入搜索内容", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_clear:
                cleanHistory();
                break;
            case R.id.backPressed:
                back();
                break;
            default:
                break;
        }
    }

    private void back() {
        finish();
    }
}
