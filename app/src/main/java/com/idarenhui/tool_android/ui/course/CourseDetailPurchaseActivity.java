package com.idarenhui.tool_android.ui.course;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.model.PurchaseInfo;
import com.idarenhui.tool_android.model.gson.CourseDetialInfoJSON;
import com.idarenhui.tool_android.model.gson.LessonTeamInfoJSON;
import com.idarenhui.tool_android.net.Api;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.CoursePurchaseRecycleListAdapter2;
import com.idarenhui.tool_android.ui.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by chen on 2017/7/27.
 */

public class CourseDetailPurchaseActivity extends BaseActivity {

    @BindView(R.id.back_icon)
    ImageView backIcon;
    @BindView(R.id.course_picture)
    ImageView coursePicture;
    @BindView(R.id.course_title)
    TextView courseTitle;
    @BindView(R.id.course_description)
    TextView courseDescription;
    @BindView(R.id.apply_tip_tag)
    TextView applyTipTag;
    @BindView(R.id.purchase_tips)
    TextView purchaseTips;
    @BindView(R.id.teamInfoList)
    RecyclerView recyclerView;
    @BindView(R.id.purchase_checkBt)
    TextView purchaseCheckBt;
    @BindView(R.id.purchase_btn)
    Button purchaseBtn;

    private CoursePurchaseRecycleListAdapter2 adapter2;
    private List<PurchaseInfo> phsInfoList;
    private List<LessonTeamInfoJSON.Data.TeamInfo> teamInfoList;

    private String lesson_id= "5975634290c4905246ac743c";
    @Override
    protected void initView() {
        Log.i(Constants.TAG, "CourseDetailPurchaseActivity.initViews()");
        LinearLayoutManager linLytMng;

        linLytMng = new LinearLayoutManager(this);
        linLytMng.setOrientation(LinearLayoutManager.VERTICAL);
        //linearLayoutManager.scrollToPosition(0);

        recyclerView.setLayoutManager(linLytMng);
        phsInfoList = new ArrayList<>();
        adapter2 = new CoursePurchaseRecycleListAdapter2(phsInfoList,getBaseContext());
        setItemLisenter();
        recyclerView.setAdapter(adapter2);
    }

    @Override
    public void initData() {
        loadData();
    }
    private void loadData(){loadFromNet();}
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
                        Glide.with(getBaseContext()).load(response.data.lesson.img).error(R.drawable.test).centerCrop().into(coursePicture);
                        courseTitle.setText(response.data.lesson.title);
                        courseDescription.setText(response.data.lesson.description);
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
                        }
                    }
                });
    }

    private void setItemLisenter(){
        adapter2.setOnItemClickLisitenter(new CoursePurchaseRecycleListAdapter2.OnItemClickLisitenter() {
            @Override
            public void onItemClick(View v, int position) {
                Log.i(Constants.TAG,"选中课程的id为："+teamInfoList.get(position).team_id);
            }
        });
    }
    @Override
    public int bindLayout() {
        return R.layout.course_purchase2;
    }


    @OnClick({R.id.back_icon, R.id.purchase_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                break;
            case R.id.purchase_btn:
                break;
        }
    }
}
