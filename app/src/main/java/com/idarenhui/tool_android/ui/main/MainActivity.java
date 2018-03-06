package com.idarenhui.tool_android.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.idarenhui.tool_android.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by chen on 2017/7/18.
 */

public class MainActivity extends FragmentActivity{
    //要切换的三个Fragment
    private MainFragment mainFragment;
    private DynamicFragment dynamicFragment;
    private PersonFragment personFragment;
    private int currentId = R.id.tv_main;// 当前选中id,默认是主页
    private TextView tvMain, tvDynamic, tvPerson;//底部三个TextView
    private View.OnClickListener tabClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);

        loadNet();

        tvMain = (TextView) findViewById(R.id.tv_main);
        tvMain.setSelected(true);//首页默认选中
        tvDynamic = (TextView) findViewById(R.id.tv_dynamic);
        tvPerson = (TextView) findViewById(R.id.tv_person);

        /**
         * 默认加载首页
         */
        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, mainFragment).commit();

        setTabClickListener();
    }
    private void setTabClickListener(){
        tabClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() != currentId) {//如果当前选中跟上次选中的一样,不需要处理
                    changeSelect(v.getId());//改变图标跟文字颜色的选中
                    changeFragment(v.getId());//fragment的切换
                    currentId = v.getId();//设置选中id
                }
            }
        };

        tvMain.setOnClickListener(tabClickListener);
        tvDynamic.setOnClickListener(tabClickListener);
        tvPerson.setOnClickListener(tabClickListener);

    }

    /**
     * 改变fragment的显示
     *
     * @param resId
     */
    private void changeFragment(int resId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//开启一个Fragment事务

        hideFragments(transaction);//隐藏所有fragment
        if(resId==R.id.tv_main){//主页
            if(mainFragment==null){//如果为空先添加进来.不为空直接显示
                mainFragment = new MainFragment();
                transaction.add(R.id.main_container,mainFragment);
                //transaction.replace(R.id.main_container,mainFragment);
            }else {
                transaction.show(mainFragment);
            }
        }else if(resId==R.id.tv_dynamic){//动态
            if(dynamicFragment==null){
                dynamicFragment = new DynamicFragment();
                transaction.add(R.id.main_container,dynamicFragment);
                //transaction.replace(R.id.main_container,dynamicFragment);
            }else {
                transaction.show(dynamicFragment);
            }
        }
        else if(resId==R.id.tv_person){//我
            if(personFragment==null){
                personFragment = new PersonFragment();
                transaction.add(R.id.main_container,personFragment);
                //transaction.replace(R.id.main_container,personFragment);
            }else {
                transaction.show(personFragment);
            }
        }
        transaction.commit();//一定要记得提交事务
    }

    /**
     * 显示之前隐藏所有fragment
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction){
        if (mainFragment != null)//不为空才隐藏,如果不判断第一次会有空指针异常
            transaction.hide(mainFragment);
        if (dynamicFragment != null)
            transaction.hide(dynamicFragment);
        if (personFragment != null)
            transaction.hide(personFragment);
    }

    /**
     * 改变TextView选中颜色
     * @param resId
     */
    private void changeSelect(int resId) {
        tvMain.setSelected(false);
        tvDynamic.setSelected(false);
        tvPerson.setSelected(false);

        switch (resId) {
            case R.id.tv_main:
                tvMain.setSelected(true);
                break;
            case R.id.tv_dynamic:
                tvDynamic.setSelected(true);
                break;
            case R.id.tv_person:
                tvPerson.setSelected(true);
                break;
        }
    }

    private void loadNet(){

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .hostnameVerifier(new HostnameVerifier()
                {
                    @Override
                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
