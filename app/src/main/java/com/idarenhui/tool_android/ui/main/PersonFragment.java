package com.idarenhui.tool_android.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.event.LoginEvent;
import com.idarenhui.tool_android.event.LogoutEvent;
import com.idarenhui.tool_android.event.RegisterEvent;
import com.idarenhui.tool_android.ui.user.LoginFgt;
import com.idarenhui.tool_android.ui.user.UserFgt;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 个人中心
 *
 * @author Ansen
 * @create time 2015-09-08
 */
public class  PersonFragment extends Fragment {

    @BindView(R.id.person_contain)
    FrameLayout personContain;

    Unbinder unbinder;

    private UserFgt userFgt;
    private LoginFgt loginFgt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person, null);

        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);

        Log.i(Constants.TAG, "PersonFragment.onCreateView()");
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();//开启一个Fragment事务
        if (true == UserInfo.hasLogin ){
            Log.i(Constants.TAG, "已经登陆");
            userFgt = new UserFgt();
            transaction.add(R.id.person_contain, userFgt);
        }else {
            Log.i(Constants.TAG, "还没有登陆");
            loginFgt = new LoginFgt();
            transaction.add(R.id.person_contain, loginFgt);
        }
        transaction.commit();//一定要记得提交事务
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void userLogin(){
        Log.i(Constants.TAG,"login");
        UserInfo.hasLogin = true;

        writeLocalInfo();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();//开启一个Fragment事务
        hideFragments(transaction);//隐藏所有fragment
        //显示useFgt,这里出现了一个bug当上一个用户退出，并用另外一个账号登陆时显示的还是上一个用户的信息

        if (null == userFgt){
            userFgt = new UserFgt();
            transaction.add(R.id.person_contain, userFgt);
        }else {
            transaction.show(userFgt);
        }
        transaction.commitAllowingStateLoss();//一定要记得提交事务

//        //改为
//        if (null != userFgt){
//            userFgt.onDestroy();
//        }
//        userFgt = new UserFgt();
//        transaction.add(R.id.person_contain, userFgt);
//        //transaction.show(userFgt);
//        transaction.commit();//一定要记得提交事务
    }

    private void userLogout(){
        Log.i(Constants.TAG,"logout");

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();//开启一个Fragment事务

        hideFragments(transaction);//隐藏所有fragment
        //显示loginFgt
        if (null == loginFgt){//如果为空先添加进来.不为空直接显示
            loginFgt = new LoginFgt();
            transaction.add(R.id.person_contain, loginFgt);
        }else {
            transaction.show(loginFgt);
        }

        transaction.commitAllowingStateLoss();//一定要记得提交事务
    }

    private void hideFragments(FragmentTransaction transaction){
        if (userFgt != null)//不为空才隐藏,如果不判断第一次会有空指针异常
            transaction.hide(userFgt);
        if (loginFgt != null)
            transaction.hide(loginFgt);
    }

    @Subscribe
    public void onEventMainThread(LogoutEvent event) {
        Log.i(Constants.TAG,"PersonFragment接收LogoutEvent");
        userLogout();

    }
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        Log.i(Constants.TAG,"PersonFragment接收LoginEvent");
        userLogin();
    }

    private void writeLocalInfo(){

        SharedPreferences userInfo = getContext().getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = userInfo.edit();

        if (false == UserInfo.hasLogin){
            Log.i(Constants.TAG, "向本地写入：hasLogin: fasle; tel:; token:;");
//            editor.remove("hasLogin");
//            editor.remove("tel");
//            editor.remove("token");
            editor.putBoolean("hasLogin", false);
            editor.putString("tel","");
            editor.putString("token", "");
        }else {
            Log.i(Constants.TAG, "向本地写入：hasLogin: true; tel:"+UserInfo.tel+"; token:"+UserInfo.token+";");
//            editor.remove("hasLogin");
//            editor.remove("tel");
//            editor.remove("token");
            editor.putBoolean("hasLogin", true);
            editor.putString("tel",UserInfo.tel);
            editor.putString("token", UserInfo.token);
        }

        editor.commit();
    }
}
