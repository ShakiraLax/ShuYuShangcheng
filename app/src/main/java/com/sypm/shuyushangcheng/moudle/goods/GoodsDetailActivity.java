package com.sypm.shuyushangcheng.moudle.goods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sypm.shuyushangcheng.R;
import com.sypm.shuyushangcheng.utils.BaseActivity;
import com.sypm.shuyushangcheng.utils.GoodsViewGroup;

import java.util.ArrayList;

public class GoodsDetailActivity extends BaseActivity implements GoodsViewGroup.OnGroupItemClickListener, View.OnClickListener {

    private GoodsViewGroup<TextView> mGroup;
    private TextView goShopping;
    private ArrayList<String> viewtexts = new ArrayList<>();
    private int chooseID = -1;
    private String chooseText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        initView();
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
