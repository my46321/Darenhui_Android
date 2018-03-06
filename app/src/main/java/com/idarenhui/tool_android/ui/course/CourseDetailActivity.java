package com.idarenhui.tool_android.ui.course;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.CustomToast;
import com.idarenhui.tool_android.model.gson.CollectJSON;
import com.idarenhui.tool_android.model.gson.CourseDetialInfoJSON;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.CourseDetailPagerAdapter;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/7/14.
 */

public class CourseDetailActivity extends FragmentActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @BindView(R.id.close_icon)
    ImageView closeIcon;
    @BindView(R.id.course_collect)
    ImageView courseCollect;
    @BindView(R.id.share_url)
    ImageView shareUrl;
    private ViewPager viewPager;
    boolean isCollection = false;
    private CourseDetailPagerAdapter adapter;
    private List<Fragment> fgtList;
    private FragmentManager fragmentManager;
    private Dialog dialog;
    String lesson_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_view_click);
        ButterKnife.bind(this);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //showDialog();
            }
        });
        init();
        overridePendingTransition(R.transition.slide_right_in, R.transition.slide_right_out);
    }


//    private void setupWindowAnimations() {
//        Slide slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
//        getWindow().setExitTransition(slide);
//    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.course_viewpager);
        Fragment webFgt = new CourseDetailWebFgt();
//        Fragment purchaseFgt = new CourseDetailPurchaseFgt();
        Fragment purchaseFgt = new CourseDetailPurchaseFgt2();
        Fragment commentFgt = new CourseCommentFgt();
        fgtList = new ArrayList<>();
        //添加想要切换的fragment界面
        fgtList.add(commentFgt);
        fgtList.add(webFgt);
        fgtList.add(purchaseFgt);

        //数据适配器
        adapter = new CourseDetailPagerAdapter(getSupportFragmentManager(), fgtList);
        //绑定适配器
        viewPager.setAdapter(adapter);
        //viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setPageTransformer(true, new RotateDownPageTransformer());
        viewPager.setOffscreenPageLimit(2);
        //设置viewPager的初始界面为第一个界面
        viewPager.setCurrentItem(1);
        loadFromNet();
    }

    private void loadFromNet() {
        lesson_id = getIntent().getStringExtra("lesson_id");
        String url2 = "https://api.idarenhui.com/DRH_Test/v1.0/lesson";
        OkHttpUtils.get()
                .url(url2)
                .addHeader("Access-Token", UserInfo.token)
                .addParams("lesson_id", lesson_id)
                .build()
                .execute(new UserCallback<CourseDetialInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Log.e("collect_img", getClass().getName() + " \nonError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(CourseDetialInfoJSON response, int id) {
                        isCollection = response.data.lesson.isCollection;
                        if (response.data.lesson.isCollection) {
                            courseCollect.setImageResource(R.drawable.ic_star_black);
                        }
                    }
                });
    }

    @OnClick({R.id.course_collect,R.id.share_url})
    public void onViewClicked(View view) {
        //if(courseCollect.getResources().)
        switch (view.getId()) {
            case R.id.share_url:
                final String share_url = "https://api.idarenhui.com/DRH_phone_Web";
                dialog = new AlertDialog.Builder(this)

                        .setTitle("iHobby分享")

                        .setMessage(share_url)

                        .setPositiveButton("复制", null)

                        .setNegativeButton("访问", null)

                        .show();
                Button PositiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                Button NegativeButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                PositiveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Log.i("TEST", "button onClick");
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newPlainText("Label", "https://api.idarenhui.com/DRH_phone_Web");
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        CustomToast.toastMsgShort(getApplicationContext(), "复制成功");

                    }
                });
                NegativeButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Log.i("TEST", "button onClick");
                        String strUrl = "https://api.idarenhui.com/DRH_phone_Web";
                        Uri uri = Uri.parse(share_url);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);

                        intent.setData(uri);
//          intent.setDataAndType(uri, "text/html");

                        startActivity(intent);
                    }
                });
                break;
            case R.id.course_collect:
                if (UserInfo.hasLogin == true) {
                    if (isCollection == false) {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("category", "lesson");
                            object.put("id", lesson_id);
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("category", "lesson");
                            object.put("id", lesson_id);
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (UserInfo.hasLogin == false) {
                    CustomToast.toastMsgShort(getApplicationContext(), "请登录后收藏");
                }
                break;
        }
    }

    public class RotateDownPageTransformer implements ViewPager.PageTransformer {

        private static final float ROT_MAX = 20.0f;
        private float mRot;

        public void transformPage(View view, float position) {

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setRotation(0);

            } else if (position <= 1) // a页滑动至b页 ； a页从 0.0 ~ -1 ；b页从1 ~ 0.0
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                if (position < 0) {

                    mRot = (ROT_MAX * position);
                    view.setPivotX(view.getMeasuredWidth() * 0.5f);
                    view.setPivotY(view.getMeasuredHeight());
                    view.setRotation(mRot);
                } else {

                    mRot = (ROT_MAX * position);
                    view.setPivotX(view.getMeasuredWidth() * 0.5f);
                    view.setPivotY(view.getMeasuredHeight());
                    view.setRotation(mRot);
                }

                // Scale the page down (between MIN_SCALE and 1)

                // Fade the page relative to its size.

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setRotation(0);
            }
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
