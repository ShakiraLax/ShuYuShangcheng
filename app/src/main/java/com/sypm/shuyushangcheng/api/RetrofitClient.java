package com.sypm.shuyushangcheng.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/1/10.
 */

public class RetrofitClient {

    private Retrofit retrofit;

    private OkHttpClient okHttpClient;

    public static final String FORMAL_HOST = "https://yys.sypm.cn/ship/";//正式访问用HOST

    public static final String TEST_HOST = "http://test.sypm.cn/ship/";//测试访问用HOST

    public static String HOST = FORMAL_HOST;

    private RetrofitClient() {
        //拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient
                .Builder()
                .sslSocketFactory(HttpsHelper.getSSLSocketFactory())
                .hostnameVerifier(HttpsHelper.getHostnameVerifier())
                .cookieJar(Injection.provideCookieJar())
                .connectTimeout(4, TimeUnit.SECONDS)//设置超时时间
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接
                .addInterceptor(httpLoggingInterceptor)//让所有网络请求都附上拦截器
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {
        return ClientInstance.sInstance;
    }

    private static class ClientInstance {
        private static RetrofitClient sInstance = new RetrofitClient();
    }

    public ShuYuService getSYService() {
        return retrofit.create(ShuYuService.class);
    }

}
