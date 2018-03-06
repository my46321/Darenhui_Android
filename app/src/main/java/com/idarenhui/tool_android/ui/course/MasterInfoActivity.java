package com.idarenhui.tool_android.ui.course;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.CustomToast;
import com.idarenhui.tool_android.custom.MasterView;
import com.idarenhui.tool_android.model.gson.CollectJSON;
import com.idarenhui.tool_android.model.gson.MasterDetailInfoJSON;
import com.idarenhui.tool_android.net.Api;
import com.idarenhui.tool_android.net.ErrorCallBack;
import com.idarenhui.tool_android.net.UserCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/7/14.
 */

public class MasterInfoActivity extends Activity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    @BindView(R.id.cover_img)
    ImageView coverImg;
    @BindView(R.id.master_info)
    MasterView masterInfo;
    @BindView(R.id.introduction_text)
    TextView introductionText;
    @BindView(R.id.close_icon)
    ImageView closeIcon;
    @BindView(R.id.course_collect)
    ImageView courseCollect;

    private String teacher_id;
    private boolean isCollection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        ButterKnife.bind(this);
        loadData();
        overridePendingTransition(R.transition.slide_right_in, R.transition.slide_right_out);
    }

    public void loadData() {
        teacher_id = getIntent().getStringExtra("teacher_id");
        Log.i(Constants.TAG, "获取teacher_id为：" + teacher_id + "的达人详情");
        loadFromNet();
    }

    public void loadFromNet() {
        //获取讲师详情
        String url = Api.getTeacherDetailInfo;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("teacher_id", teacher_id)
                .addHeader("Access-Token",UserInfo.token)
                .build()
                .execute(new UserCallback<MasterDetailInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ErrorCallBack errorCallBack = new ErrorCallBack(getBaseContext(), "error!", new ErrorCallBack.ErrorProcess() {
                            @Override
                            public void process() {
                                //
                            }
                        });
                        Log.e(Constants.TAG, getClass().getName() + " \nonError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(MasterDetailInfoJSON response, int id) {
                        masterInfo.setContent(response);
                        String coverImgUrl = response.data.teacher_info.cover;
                        Glide.with(getBaseContext()).load(coverImgUrl)
                                .crossFade(1000)
                                .error(R.drawable.test)
                                .centerCrop()
                                .into(coverImg);
                        introductionText.setText(response.data.teacher_info.introduction);
                        isCollection = response.data.teacher_info.isCollection;
                        if (response.data.teacher_info.isCollection) {
                            courseCollect.setImageResource(R.drawable.ic_star_black);
                        }
                    }
                });
    }


    @OnClick({R.id.close_icon, R.id.course_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_icon:
                finish();
                break;
            case R.id.course_collect:
                if(UserInfo.hasLogin==true)
                {
                    if(isCollection==false)
                    {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("category", "teacher");
                            object.put("id",teacher_id);
                            String json = object.toString();
                            //String s="{\"category\":"
                            OkHttpUtils
                                    .postString()
                                    .url("https://api.idarenhui.com/DRH_Test/v1.0/collect")
                                    .addHeader("Access-Token", UserInfo.token)
                                    .content(json)
                                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                    .build()
                                    .execute(new UserCallback<CollectJSON>() {
                                        @Override
                                        public void onResponse(CollectJSON response, int id) {
                                            //Log.i("generate_order_succeed",response);
                                            //new PaymentTask().execute(order_id);
                                            //showDialog();
                                            courseCollect.setImageResource(R.drawable.ic_star_black);
                                            isCollection = true;
                                            CustomToast.toastMsgShort(getApplicationContext(), "收藏成功！");
                                        }

                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            e.printStackTrace();
                                            Log.i("collect_fail", e.getMessage());
                                        }
                                    });
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("category", "teacher");
                            object.put("id", teacher_id);
                            String json = object.toString();
                            OkHttpUtils
                                    .delete()
                                    .url("https://api.idarenhui.com/DRH_Test/v1.0/collect")
                                    .addHeader("Access-Token", UserInfo.token)
                                    .requestBody(RequestBody.create(JSON, json))//
                                    .build()//
                                    .execute(new UserCallback<CollectJSON>() {
                                        @Override
                                        public void onResponse(CollectJSON response, int id) {
                                            //Log.i("generate_order_succeed",response);
                                            //new PaymentTask().execute(order_id);
                                            //showDialog();
                                            courseCollect.setImageResource(R.drawable.ic_star);
                                            isCollection = false;
                                            CustomToast.toastMsgShort(getApplicationContext(), "取消收藏");
                                            //ContactDialog.showTipDialog(getApplicationContext(), "取消收藏");
                                        }

                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            e.printStackTrace();
                                            Log.i("collect_fail", e.getMessage());
                                        }
                                    });
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                else if(UserInfo.hasLogin==false)
                {
                    CustomToast.toastMsgShort(getApplicationContext(), "请登录后收藏");
                }
                break;
        }
    }
}
