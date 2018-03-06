package com.idarenhui.tool_android.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.CirclePictureTransformation;
import com.idarenhui.tool_android.custom.CourseOverview;
import com.idarenhui.tool_android.event.LogoutEvent;
import com.idarenhui.tool_android.ui.course.OverViewCourseFgt;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by chen on 2017/7/18.
 */

public class MainActivity_v1 extends FragmentActivity{
    //要切换的三个Fragment
    //private MainFragment mainFragment;
    private OverViewCourseFgt overViewCourseFgt;

    private DynamicFragment dynamicFragment;

    private PersonFragment personFragment;

    private ImageView tabLogo;
    private int currentId = R.id.tv_main;// 当前选中id,默认是主页
    private TextView tvMain, tvDynamic, tvPerson;//底部三个TextView
    private View.OnClickListener tabClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain_v1);

        EventBus.getDefault().register(this);

        loadLocalInfo();
        loadNet();
        iniView();
        overridePendingTransition(R.transition.slide_right_in, R.transition.slide_left_out);

    }
    protected void iniView(){
        tvMain = (TextView) findViewById(R.id.tv_main);
        tvMain.setSelected(true);//首页默认选中
        tvDynamic = (TextView) findViewById(R.id.tv_dynamic);
        tvPerson = (TextView) findViewById(R.id.tv_person);
        tabLogo = (ImageView)findViewById(R.id.logo_tab);
        //加载通知栏上的logo
        Glide.with(MainActivity_v1.this).load(R.drawable.logo).
                bitmapTransform(new CirclePictureTransformation(MainActivity_v1.this))
                .into(tabLogo);

        //默认加载首页
        overViewCourseFgt = new OverViewCourseFgt();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, overViewCourseFgt).commit();

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
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        hideFragments(transaction);//隐藏所有fragment
        if(resId==R.id.tv_main){//主页
            if(overViewCourseFgt==null){//如果为空先添加进来.不为空直接显示
                overViewCourseFgt = new OverViewCourseFgt();
                transaction.add(R.id.main_container,overViewCourseFgt);
            }else {
                transaction.show(overViewCourseFgt);
            }
        }else if(resId==R.id.tv_dynamic){//动态
            if(dynamicFragment==null){
                dynamicFragment = new DynamicFragment();
                transaction.add(R.id.main_container,dynamicFragment);
            }else {
                transaction.show(dynamicFragment);
            }
        }
        else if(resId==R.id.tv_person){//我
            if(personFragment==null){
                personFragment = new PersonFragment();
                transaction.add(R.id.main_container,personFragment);
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
        if (overViewCourseFgt != null)//不为空才隐藏,如果不判断第一次会有空指针异常
            transaction.hide(overViewCourseFgt);
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

    //加载本地信息（登陆状态、用户信息等）
    private void loadLocalInfo(){
        SharedPreferences userInfo = getBaseContext().getSharedPreferences("userInfo", 0);
        boolean hasLogin = userInfo.getBoolean("hasLogin", false);//默认没有登陆
        String tel = userInfo.getString("tel", "");//默认为null
        String token = userInfo.getString("token", "");//默认为null

        Log.i(Constants.TAG, "取出的tel的值为："+tel+ "; hasLogin为："+hasLogin);
        UserInfo.hasLogin = hasLogin;
        UserInfo.tel = tel;
        UserInfo.token = token;
    }

    //写入本地信息
    private void writeLocalInfo(){

        SharedPreferences userInfo = getBaseContext().getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = userInfo.edit();

        if (false == UserInfo.hasLogin){
            Log.i(Constants.TAG, "向本地写入：hasLogin: fasle; tel:; token:;");

            editor.putBoolean("hasLogin", false);
            editor.putString("tel","");
            editor.putString("token", "");
        }else {
            Log.i(Constants.TAG, "向本地写入：hasLogin: true; tel:"+UserInfo.tel+"; token:"+UserInfo.token+";");

            editor.putBoolean("hasLogin", true);
            editor.putString("tel",UserInfo.tel);
            editor.putString("token", UserInfo.token);
        }

        editor.commit();
    }

    @Subscribe
    public void onEventMainThread(LogoutEvent event) {
        Log.i(Constants.TAG,"MainActivity_v1接收LogoutEvent");
        writeLocalInfo();

    }

    //小米强制关闭app时不会调用 onDestory() !!!???
    @Override
    protected void onDestroy() {
        Log.i("mainAct","存储用户信息");
        writeLocalInfo();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //app打开之后，按下返回键回到桌面，再打开app，不会再看到启动页
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
