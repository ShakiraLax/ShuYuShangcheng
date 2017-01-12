package com.sypm.shuyushangcheng.moudle.my;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sypm.shuyushangcheng.R;
import com.sypm.shuyushangcheng.base.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout modifyPass, suggestion, update, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        modifyPass = (LinearLayout) findViewById(R.id.modifyPass);
        suggestion = (LinearLayout) findViewById(R.id.suggestion);
        update = (LinearLayout) findViewById(R.id.update);
        exit = (LinearLayout) findViewById(R.id.exit);
        modifyPass.setOnClickListener(this);
        suggestion.setOnClickListener(this);
        update.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modifyPass:
                View view = View.inflate(getActivity(), R.layout.view_modify_pass, null);
                final EditText old = (EditText) view.findViewById(R.id.old);
                final EditText newPass = (EditText) view.findViewById(R.id.newPass);
                final EditText confirm = (EditText) view.findViewById(R.id.confirm);
                Dialog alertDialog = new AlertDialog.Builder(this)
                        .setView(view)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                alertDialog.show();
                break;
            case R.id.suggestion:
                Toast.makeText(getActivity(), "您的建议", Toast.LENGTH_SHORT).show();
                break;
            case R.id.update:
                Toast.makeText(getActivity(), "最新版本哟", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                Toast.makeText(getActivity(), "您要退出吗", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
