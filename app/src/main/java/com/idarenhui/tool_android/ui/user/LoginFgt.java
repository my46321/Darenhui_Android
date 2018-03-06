package com.idarenhui.tool_android.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.CirclePictureTransformation;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.custom.CustomToast;
import com.idarenhui.tool_android.event.LoginEvent;
import com.idarenhui.tool_android.model.gson.LogOrRegReqJSON;
import com.idarenhui.tool_android.model.gson.UserLoginInfoJSON;
import com.idarenhui.tool_android.net.Api;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by chen on 2017/7/28.
 */

public class LoginFgt extends BaseFragment {
    @BindView(R.id.profile_image) ImageView profileImage;
    @BindView(R.id.PhoneNumText) EditText PhoneNumText;
    @BindView(R.id.VerificationCodeText) EditText VerificationCodeText;
    @BindView(R.id.button_getVerificationCode) Button buttonGetVerificationCode;
    @BindView(R.id.button_logReg) Button buttonLogReg;

    @Override
    public void initViews(View view) {
        Log.i(Constants.TAG, "LoginFgt.initViews()");
        //加载logo，并设置为圆形
        Glide.with(getContext()).load(R.drawable.logo).
                bitmapTransform(new CirclePictureTransformation(getContext()))
                .into(profileImage);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }



    @OnClick({R.id.button_getVerificationCode, R.id.button_logReg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_getVerificationCode:
                //获取验证码
                getCode();
                break;
            case R.id.button_logReg:
                //登陆
                loginClick();
                break;
        }
    }

    private void getCode(){
        String tel = PhoneNumText.getText().toString();
        if (tel.isEmpty()) {
            ContactDialog.showTipDialog(getContext(), "电话不能为空！");
        } else if (tel.length() != 11) {
            ContactDialog.showTipDialog(getContext(), "号码格式不对");
        }else {
            String url = Api.getVerifyCode;
            String data = "{\"mobile\":\""+ tel+ "\"}";
            OkHttpUtils
                    .postString()
                    .url(url)
                    .content(data)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            CustomToast.toastMsgShort(getContext(), "获取失败");
                            Log.e(Constants.TAG, getClass().getName() + " \nonError:" + e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            CustomToast.toastMsgShort(getContext(), "发送成功");
                            //一定时间内不能重复发送验证码

                        }
                    });
        }
    }

    private void loginClick(){
        //先判断电话号码，验证码是否填写
        String tel = PhoneNumText.getText().toString();
        String code = VerificationCodeText.getText().toString();
        if ((false ==tel.isEmpty()) && (false == code.isEmpty())){
            //都已填写
            UserInfo.tel = tel; //保存用户电话号码

            LogOrRegReqJSON req = new LogOrRegReqJSON();
            req.userInfo.mobile = tel;
            req.userInfo.code_str = code;

            OkHttpUtils
                    .postString()
                    .url("https://api.idarenhui.com/DRH_Test/v1.0/user")
                    .content(new Gson().toJson(req))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute(new UserCallback<UserLoginInfoJSON>() {

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i(Constants.TAG, e.getMessage().toString());
                            CustomToast.toastMsgShort(getContext(), "检查电话号码和验证码！");
                        }

                        @Override
                        public void onResponse(UserLoginInfoJSON response, int id) {
                            if(response.code == 200){
                                VerificationCodeText.setText("");
                                String token = response.data.accessToken;

                                UserInfo.token = token;//保存token

                                if(response.data.isRegister == true){
                                    //需要注册，跳入注册界面
                                    startActivity(new Intent(getContext(), RegisterAty.class));
                                }else{
                                    //已经注册过，直接登录
                                    EventBus.getDefault().post(new LoginEvent());
                                }
                            }
                        }
                    });
        }else {
            CustomToast.toastMsgShort(getContext(), "请填写完整的信息");
        }
    }
}
