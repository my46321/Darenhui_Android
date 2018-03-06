package com.idarenhui.tool_android.ui.course;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.custom.MasterView;
import com.idarenhui.tool_android.model.gson.CourseDetialInfoJSON;
import com.idarenhui.tool_android.model.gson.ImageArticleInfoJSON;
import com.idarenhui.tool_android.net.Api;
import com.idarenhui.tool_android.net.ErrorCallBack;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import com.zyao89.view.zloading.star.StarBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by chen on 2017/7/24.
 */

public class CourseDetailWebFgt extends BaseFragment {
    @BindView(R.id.courses_detail_web)
    WebView webView;
    @BindView(R.id.courses_detail_button1)
    Button button_experience;
    @BindView(R.id.courses_detail_button2)
    Button button_purchase;
    @BindView(R.id.master_info)
    MasterView masterView;

    private String lesson_id;
    private String teacher_id;
    private String TAG = "Test";
    private String webContent = "<p>正在加载数据...<p>";

    ContactDialog dialog;
    ZLoadingDialog loadingDialog;

    @Override
    public void initViews(View view) {
        lesson_id = mActivity.getIntent().getStringExtra("lesson_id");
        Log.i(TAG, "CourseDetailWebFgt.initViews()\ncourseId = " + lesson_id);

        loadingDialog = new ZLoadingDialog(getContext());
        loadingDialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText("Loading...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .show();
        //onViewClicked(view);
    }

    @Override
    public void initData() {
        //从网络加载数据
        loadFromNet();
    }

    @Override
    public int getLayoutId() {
        return R.layout.coures_detail_info;
    }

    //底部按钮点击事件
    @OnClick({R.id.courses_detail_button1, R.id.courses_detail_button2,R.id.master_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.courses_detail_button1:
                buildExperienceLesson();
                break;
            case R.id.courses_detail_button2:
                Log.i(Constants.TAG, "点击报名！");
                break;
            case R.id.master_info:
                Log.i(Constants.TAG, "选中达人控件！");
                Bundle bundle = new Bundle();
                bundle.putString("teacher_id", teacher_id);
                Intent intent = new Intent(getContext(), MasterInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
    //创建用户体验课程
    protected void buildExperienceLesson(){
        //先判断用户有没有登陆
        if(UserInfo.hasLogin) {
            String url = Api.buildUserExperienceLesson;
            String data = "{\"lesson_id\":\""+lesson_id + "\"}";
            OkHttpUtils
                    .postString()
                    .url(url)
                    .addHeader("Access-Token", UserInfo.token)
                    .content(data)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ContactDialog.showTipDialog(getContext(), "创建体验失败！请联系客服！");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            ContactDialog.showTipDialog(getContext(), "创建体验成功！");
                        }
                    });
        }else {
            ContactDialog.showTipDialog(getContext(), "您还没有登陆，请先登陆！");
        }
    }

    public void loadFromNet() {
        //加载该页面的web内容
        String url = "https://api.idarenhui.com/DRH_Test/v1.0/lesson/imgarticle";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("lesson_id", lesson_id)
                .build()
                .execute(new UserCallback<ImageArticleInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, getClass().getName() + " \nonError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(ImageArticleInfoJSON response, int id) {
                        webContent = response.data.imgarticle_info._content;
                        Log.i(TAG, "webContent:" + webContent);
                        //webView.loadData(webContent, "text/html", "uft-8");//会出现乱码显示
                        webView.loadData(webContent, "text/html; charset=UTF-8", null);
                        loadingDialog.dismiss();
                    }
                });

        //加载更多信息，并从中获取老师的信息

        String url2 = "https://api.idarenhui.com/DRH_Test/v1.0/lesson";
        OkHttpUtils.get()
                .url(url2)
                .addParams("lesson_id", lesson_id)
                .build()
                .execute(new UserCallback<CourseDetialInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ErrorCallBack test = new ErrorCallBack(getContext(), "error!", new ErrorCallBack.ErrorProcess() {
                            @Override
                            public void process() {
                                //刷新一次？？或执行其他函数
                                test();
                            }
                        });
                        Log.e(TAG, getClass().getName() + " \nonError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(CourseDetialInfoJSON response, int id) {
                        masterView.setContent(response);
                        if (0 != response.data.lesson.teachers.size()){
                            teacher_id = response.data.lesson.teachers.get(0).teacher_id;
                        }
                    }
                });
    }

    private void test() {
        String url2 = "https://api.idarenhui.com/DRH_Test/v1.0/lesson";
        OkHttpUtils.get()
                .url(url2)
                .addParams("lesson_id", lesson_id)
                .build()
                .execute(new UserCallback<CourseDetialInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Log.e(TAG, getClass().getName() + " \nonError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(CourseDetialInfoJSON response, int id) {
                        masterView.setContent(response);
                    }
                });
    }
}