package com.idarenhui.tool_android.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.model.PurchaseInfo;
import com.idarenhui.tool_android.model.gson.CourseDetialInfoJSON;
import com.idarenhui.tool_android.model.gson.LessonTeamInfoJSON;
import com.idarenhui.tool_android.net.Api;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.CoursePurchaseRecycleListAdapter;
import com.idarenhui.tool_android.ui.adapter.CoursePurchaseRecycleListAdapter2;
import com.idarenhui.tool_android.ui.base.BaseFragment;
import com.idarenhui.tool_android.ui.order.SignupPayActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by chen on 2017/7/26.
 */

public class CourseDetailPurchaseFgt2 extends BaseFragment {
    @BindView(R.id.back_icon) ImageView backIcon;
    @BindView(R.id.course_picture) ImageView coursePicture;
    @BindView(R.id.course_title) TextView courseTitle;
    @BindView(R.id.course_description) TextView courseDescription;
    @BindView(R.id.purchase_tips) TextView purchaseTips;
    @BindView(R.id.purchase_checkBt) TextView purchaseCheckBt;
    @BindView(R.id.teamInfoList) RecyclerView recyclerView;
    @BindView(R.id.purchase_btn) Button purchaseBtn;

    private CoursePurchaseRecycleListAdapter2 adapter2;
    private List<PurchaseInfo> phsInfoList;
    private List<LessonTeamInfoJSON.Data.TeamInfo> teamInfoList;

    private String lesson_id;
    private String checkedTeamId;
    private String checkedprice, checkedduration, checkedtime, checkedsite,
        lesson_img, lesson_title, lesson_discription;
    private String lesson_note;

    private boolean hadChecked;
    private boolean hadLogin;
    @Override
    public void initViews(View view) {
        Log.i(Constants.TAG, "CourseDetailPurchaseFgt2.initViews()");

        LinearLayoutManager linLytMng;
        linLytMng = new LinearLayoutManager(getActivity());
        linLytMng.setOrientation(LinearLayoutManager.HORIZONTAL);
        //linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linLytMng);
        phsInfoList = new ArrayList<>();
        adapter2 = new CoursePurchaseRecycleListAdapter2(phsInfoList, getContext());
        setItemLisenter();
        recyclerView.setAdapter(adapter2);
    }

    @Override
    public void initData() {
        lesson_id = mActivity.getIntent().getStringExtra("lesson_id");
        Log.i(Constants.TAG, "courseId = " + lesson_id);

        hadChecked = false;
        hadLogin = false;
        loadData();
    }
    private void loadData(){
        try {
            loadFromNet();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadFromNet(){
        //获取课程详情
        String lessonUrl = Api.getLessonInfo;
        OkHttpUtils
                .get()
                .url(lessonUrl)
                .addParams("lesson_id", lesson_id)
                .build()
                .execute(new UserCallback<CourseDetialInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(Constants.TAG, getClass().getName() + " \nonError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(CourseDetialInfoJSON response, int id) {
                        Log.i(Constants.TAG, "获取课程详情:"+response.code);

                        lesson_img = response.data.lesson.img;
                        lesson_title = response.data.lesson.title;
                        lesson_discription = response.data.lesson.description;
                        lesson_note = response.data.lesson.note;//课程须知

                        Glide.with(getContext()).load(lesson_img).error(R.drawable.test).centerCrop().into(coursePicture);
                        courseTitle.setText(lesson_title);
                        courseDescription.setText(lesson_discription);

                        //报名须知
                        purchaseTips.setText(lesson_note);
                        int lines =  purchaseTips.getLineCount();
                        if (lines > 3){
                            //报名须知大于三行
                            purchaseTips.setMaxLines(3);
                        }else {
                            //隐藏查看更多属性
                            purchaseCheckBt.setVisibility(View.GONE);
                        }
                    }
                });
        //获取班级详情
        String teamUrl = Api.getLessonTeam;
        OkHttpUtils
                .get()
                .url(teamUrl)
                .addParams("lesson_id", lesson_id)
                .addParams("pagesize", String.valueOf(100))
                .addParams("offset", String.valueOf(0))
                .build()
                .execute(new UserCallback<LessonTeamInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(Constants.TAG, getClass().getName() + " \nonError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(LessonTeamInfoJSON response, int id) {
                        Log.i(Constants.TAG, "获取班级详情:"+response.code);
                        teamInfoList = response.data.teams_info;
                        for (int i = 0; i < response.data.teams_info.size(); i++) {
                            phsInfoList.add(new PurchaseInfo(response.data.teams_info.get(i)));
                            Log.i(Constants.TAG, "班级信息:"+response.data.teams_info.get(i).site);
                        }
                        adapter2.AddHeaderItem(phsInfoList);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.course_purchase2;
    }

    private void setItemLisenter(){
        adapter2.setOnItemClickLisitenter(new CoursePurchaseRecycleListAdapter2.OnItemClickLisitenter() {
            @Override
            public void onItemClick(View v, int position) {
                hadChecked = true;//设置选中状态为true
                //记录选中item的信息
                checkedTeamId = teamInfoList.get(position).team_id;
                Log.i(Constants.TAG,"选中课程的team_id为："+ checkedTeamId);
                checkedprice = teamInfoList.get(position).price;
                checkedduration = teamInfoList.get(position).duration;
                checkedsite = teamInfoList.get(position).site;
                checkedtime = teamInfoList.get(position).time;
            }
        });
    }

    private static boolean  radio = true;
    @OnClick({R.id.back_icon, R.id.purchase_btn, R.id.purchase_checkBt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                //点击返回按钮
                break;
            case R.id.purchase_btn:
                //点击预约报名
                applyLesson();
                break;
            case R.id.purchase_checkBt:
                if (true == radio){
                    //点击查看更多(包含第一次点击)展开
                    purchaseTips.setMaxLines(100);
                    purchaseCheckBt.setText("收起");
                    radio = false;
                }else {
                    //收起
                    purchaseTips.setMaxLines(3);
                    purchaseCheckBt.setText("查看全部");
                    radio = true;
                }
                break;
        }
    }

    //对预约按钮的响应事件
    private void applyLesson(){
        //先判断是否有选中课程，再判断是否登陆
        hadLogin = UserInfo.hasLogin;
        if (false == hadLogin){
            //没有登陆,弹出登陆页面
            ContactDialog.showTipDialog(getContext(), "您还没有登陆，请先登陆");
            //
        }else {
            //已经登陆
            boolean hadPay = false;
            //先从服务器获取该课程是否已经支付过
            if (false == hadPay){
                //没有支付
                if (false == hadChecked){
                    //没有选择课程规格
                    ContactDialog.showTipDialog(getContext(), "您还没有选中任何课程，请从课程规格中挑选一项！");
                }else {
                    // 进入预约报名页面
                    Bundle bundlePurchase= new Bundle();
                    bundlePurchase.putString("lesson_id", lesson_id);
                    bundlePurchase.putString("team_id", checkedTeamId);
                    bundlePurchase.putString("price", checkedprice);
                    bundlePurchase.putString("duration", checkedduration);
                    bundlePurchase.putString("time", checkedtime);
                    bundlePurchase.putString("site", checkedsite);
                    bundlePurchase.putString("lesson_img", lesson_img);
                    bundlePurchase.putString("lesson_title", lesson_title);
                    bundlePurchase.putString("lesson_discription", lesson_discription);

                    Intent intentPurchase = new Intent(getContext(), SignupPayActivity.class);
                    intentPurchase.putExtras(bundlePurchase);
                    startActivity(intentPurchase);
                }
            }else {
                //已经支付过了
                ContactDialog.showTipDialog(getContext(), "改课程您已经付过款了！");
            }

        }
    }
}
