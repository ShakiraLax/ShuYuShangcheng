package com.sypm.shuyushangcheng.app;

import android.app.Application;

import com.sypm.shuyushangcheng.api.Injection;
import com.sypm.shuyushangcheng.utils.ToastUtils;
import com.tumblr.remember.Remember;

/**
 * Created by Administrator on 2017/1/10.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Injection.setApplicationContext(getApplicationContext());
        Remember.init(this, "com.sypm.shuyushangcheng");
        ToastUtils.init(this);
    }
}
