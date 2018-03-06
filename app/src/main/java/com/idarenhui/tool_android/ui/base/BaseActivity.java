package com.idarenhui.tool_android.ui.base;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Window;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ericrobert on 2017/7/16.
 */

public abstract class BaseActivity extends FragmentActivity {
    protected Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar = getActionBar();
        if(bar != null){
            bar.hide();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        initParams(bundle);
        setContentView(bindLayout());
        mUnbinder = ButterKnife.bind(this);

        initView();
        initData();
    }


    public abstract int bindLayout();
    protected void initParams(Bundle bundle){}

    protected abstract void initView();
    protected abstract void initData();


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    protected void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
        }
    }


    protected void jumpTo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void jumpTp(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}
