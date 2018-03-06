package com.idarenhui.tool_android.ui.main;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.constant.PhoneStute;
import com.idarenhui.tool_android.custom.ContactDialog;

/**
 * Created by chen on 2017/7/29.
 */
//app进入动画页面
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splach);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Constants.TAG, "SplashActivity.onStart()");
        checkNetStatus();
    }

    //检查网络连接
    protected void checkNetStatus(){
        if (PhoneStute.isNetworkAvailable(getBaseContext())){
            Log.i(Constants.TAG, "有网了连接");
            jumpToMainActivity();
        }else {
            //当没有网络连接时，提示用户连接网路
            Log.i(Constants.TAG, "没有网络连接");
            ContactDialog.showDialog(SplashActivity.this, "没有网络连接", new ContactDialog.OnButtonClickLisenter() {
                @Override
                public void setOnClickOkLisenter() {
                    //点击确认后再确认下有没有联网,用户使用下拉栏连接网络不会重新onStart()(小米测试)
                    if (PhoneStute.isNetworkAvailable(getBaseContext())){
                        jumpToMainActivity();
                    }

                    //用户点击确认，跳转至系统网路设置页面
                    Intent intent=null;
                    //判断手机系统的版本  即API大于10 就是3.0或以上版本
                    if(android.os.Build.VERSION.SDK_INT>10){
                        intent = new Intent(Settings.ACTION_SETTINGS);
                    }else{
                        intent = new Intent();
                        ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                        intent.setComponent(component);
                        intent.setAction("android.intent.action.VIEW");
                    }
                    startActivity(intent);
                }

                @Override
                public void setOnClickCancleListener() {
                    //点击取消，程序退出
                    finish();
                }
            });
        }
    }

    //跳转至首页
    protected void jumpToMainActivity(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity_v1.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}
