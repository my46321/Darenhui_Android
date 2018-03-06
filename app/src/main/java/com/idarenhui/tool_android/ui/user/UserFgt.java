package com.idarenhui.tool_android.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.CirclePictureTransformation;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.event.LogoutEvent;
import com.idarenhui.tool_android.model.gson.UserInfoJSON;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by chen on 2017/7/28.
 */

public class UserFgt extends BaseFragment {
    @BindView(R.id.profile_user_fragment)
    ImageView profileUserFragment;
    @BindView(R.id.username_text)
    TextView usernameText;
    @BindView(R.id.gender_button)
    ImageView genderButton;
    @BindView(R.id.school_text)
    TextView schoolText;
    @BindView(R.id.enrollmentYear_text)
    TextView enrollmentYearText;
    @BindView(R.id.phoneNumber_text)
    TextView phoneNumberText;
    @BindView(R.id.logoff_text)
    Button logoffText;
    @BindView(R.id.user_modify_info)
    ImageView modifyInfoBtn;
    @BindView(R.id.focusTeacher)
    RelativeLayout focusTeacher;
    @BindView(R.id.collectLesson)
    RelativeLayout collectLesson;
    @BindView(R.id.experienceLesson)
    RelativeLayout experienceLesson;
    Unbinder unbinder;
    @BindView(R.id.focus_teacher)
    TextView focus_Teacher;
    @BindView(R.id.try_course)
    TextView try_Course;
    @BindView(R.id.collect_course)
    TextView collect_Course;
    @BindView(R.id.contact_us)
    RelativeLayout contactUs;
    @BindView(R.id.focus_info1)
    RelativeLayout focusInfo1;
    @BindView(R.id.focus_info2)
    RelativeLayout focusInfo2;
    @BindView(R.id.focus_info3)
    RelativeLayout focusInfo3;

    @Override
    public void initViews(View view) {
        Log.i(Constants.TAG, "UserFgt.initViews()");

        logoffText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(Constants.TAG, "点击退出登陆");

                UserInfo.token = "";
                UserInfo.hasLogin = false;
                UserInfo.tel = "";

                SharedPreferences userInfo = getContext().getSharedPreferences("userInfo", 0);
                SharedPreferences.Editor editor = userInfo.edit();
                editor.putBoolean("hasLogin", false);
                editor.putString("tel","");
                editor.putString("token", "");


                EventBus.getDefault().post(new LogoutEvent());//发送登出消息

            }
        });

        modifyInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Constants.TAG, "点击修改用户信息");
                startActivity(new Intent(getContext(), ModifyUserInfoAty.class));
            }
        });
    }

    @Override
    public void initData() {
        loadFromNet();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFromNet();
    }

    //获取用户信息
    private void loadFromNet() {
        OkHttpUtils
                .get()//
                .url("https://api.idarenhui.com/DRH_Test/v1.0/user")//
                .addHeader("Access-Token", UserInfo.token)
                .build()//
                .execute(new UserCallback<UserInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("test", e.getMessage());
                    }

                    @Override
                    public void onResponse(UserInfoJSON response, int id) {
                        Log.i(Constants.TAG, "获取用户信息" + response.code);
                        Log.i(Constants.TAG, response.data.user_info.avatar);

                        Glide.with(getContext()).load(response.data.user_info.avatar)
                                .bitmapTransform(new CirclePictureTransformation(getContext()))
                                .error(R.drawable.test)
                                .into(profileUserFragment);
                        if (response.data.user_info.avatar.equals(""))
                        {
                            Glide.with(getContext()).load(R.drawable.test)
                                    .bitmapTransform(new CirclePictureTransformation(getContext()))
                                    .into(profileUserFragment);
                        }

                        usernameText.setText(response.data.user_info.realName);
                        schoolText.setText(response.data.user_info.college);
                        enrollmentYearText.setText(response.data.user_info.identity + " ; " + response.data.user_info.grade);
                        phoneNumberText.setText(UserInfo.tel);
                        focus_Teacher.setText(Integer.toString(response.data.user_info.teacher_count));
                        try_Course.setText(Integer.toString(response.data.user_info.exp_count));
                        collect_Course.setText(Integer.toString(response.data.user_info.lesson_count));
                        if (response.data.user_info.sex.equals("man")) {
                            genderButton.setImageResource(R.drawable.reg_male_image2);
                            Log.i("test.gender", "打印man");
                        } else {
                            Log.i("test.gender", "打印woman");
                            genderButton.setImageResource(R.drawable.reg_female_image2);
                        }
                    }

                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        Log.i("userFgt","destory");
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.focusTeacher, R.id.collectLesson, R.id.experienceLesson,R.id.focus_info1, R.id.focus_info2, R.id.focus_info3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.focusTeacher: case R.id.focus_info1:
                startActivity(new Intent(getActivity(), UserTeacherAty.class));
                break;
            case R.id.collectLesson: case R.id.focus_info3:
                startActivity(new Intent(getActivity(), UserLessonAty.class));
                break;
            case R.id.experienceLesson: case R.id.focus_info2:
                startActivity(new Intent(getActivity(), UserExperienceAty.class));
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端中
            loadFromNet();
        }
    }

    @OnClick(R.id.contact_us)
    public void onViewClicked() {
        ContactDialog dialog = new ContactDialog();
        dialog.showDialog(getContext());
    }

}
