package com.idarenhui.tool_android.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.custom.CustomToast;
import com.idarenhui.tool_android.model.ExperienceData;
import com.idarenhui.tool_android.model.gson.user_experience_infoJSON;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.userInfoAdapter.experienceListAdapter;
import com.idarenhui.tool_android.ui.course.CourseDetailActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/7.
 */

public class UserExperienceAty extends Activity {
    @BindView(R.id.exper_list)
    RecyclerView recyclerView;
    @BindView(R.id.close_icon)
    ImageView closeIcon;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<ExperienceData> infoList;
    experienceListAdapter adapter;
    private experienceListAdapter.OnItemClickLisitenter lisitenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_experience_list);
        ButterKnife.bind(this);
        infoList = new ArrayList<>(10);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);

        gridLayoutManager=new GridLayoutManager(this,2);
       // recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(gridLayoutManager);
        LoadFromNet();
        adapter = new experienceListAdapter(infoList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        //index=getArguments().getInt("index");
        setItemLisenter();
    }

    public void setItemLisenter() {
        adapter.setOnItemClickLisitenter(new experienceListAdapter.OnItemClickLisitenter() {
            @Override
            public void onItemClick(View v, int position) {
                String s = infoList.get(position).lesson_id;
                int state=infoList.get(position).state;
                if(3==state)
                {
                    CustomToast.toastMsgShort(getApplicationContext(),"课程已下架");
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("lesson_id", s);
                    Intent intent;
                    intent = new Intent(UserExperienceAty.this, CourseDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void LoadFromNet() {
        OkHttpUtils
                .get()//
                .url("https://api.idarenhui.com/DRH_Test/v1.0/user/findinfo")//
                .addHeader("Access-Token", UserInfo.token)
                .addParams("category", "experience")
                .build()//
                .execute(new UserCallback<user_experience_infoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("test", e.getMessage());
                    }

                    @Override
                    public void onResponse(user_experience_infoJSON response, int id) {
                        //Log.i(Constants.TAG, response.data.user_info.avatar);
                        List<ExperienceData> l = response.data.infolist;

                        adapter.AddHeaderItem(l);
                    }

                });
    }

    @OnClick(R.id.close_icon)
    public void onViewClicked() {
        finish();
    }
}