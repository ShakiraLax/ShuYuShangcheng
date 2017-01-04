package com.sypm.shuyushangcheng.moudle.login;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sypm.shuyushangcheng.R;
import com.sypm.shuyushangcheng.utils.BaseActivity;

public class LoginByPassActivity extends BaseActivity {

    Button passwordStatus;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_pass);
        initView();
    }

    private void initView() {
        passwordStatus = (Button) findViewById(R.id.passwordStatus);
        password = (EditText) findViewById(R.id.password);
        passwordStatus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        password.setInputType(0x90);
                        break;
                    case MotionEvent.ACTION_UP:
                        password.setInputType(0x81);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
