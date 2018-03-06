package com.idarenhui.tool_android.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.model.LessonData;
import com.idarenhui.tool_android.model.gson.user_lesson_infoJSON;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.userInfoAdapter.lessonListAdapter;
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

public class UserLessonAty extends Activity {
    @BindView(R.id.lesson_list)
    RecyclerView recyclerView;
    @BindView(R.id.close_icon)
    ImageView closeIcon;
    private LinearLayoutManager linearLayoutManager;
    private List<LessonData> infoList;
    lessonListAdapter adapter;
    private lessonListAdapter.OnItemClickLisitenter lisitenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_lesson_list);
        ButterKnife.bind(this);
        infoList = new ArrayList<>(10);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        LoadFromNet();
        adapter = new lessonListAdapter(infoList, getApplicationContext());

        recyclerView.setAdapter(adapter);
        //index=getArguments().getInt("index");
        setItemLisenter();
    }

    public void setItemLisenter() {
        adapter.setOnItemClickLisitenter(new lessonListAdapter.OnItemClickLisitenter() {
            @Override
            public void onItemClick(View v, int position) {
                String s = infoList.get(position)._id;
                Bundle bundle = new Bundle();
                bundle.putString("lesson_id", s);
                Intent intent;
                intent = new Intent(UserLessonAty.this, CourseDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void LoadFromNet() {
        OkHttpUtils
                .get()//
                .url("https://api.idarenhui.com/DRH_Test/v1.0/user/findinfo")//
                .addHeader("Access-Token", UserInfo.token)
                .addParams("category", "lesson")
                .build()//
                .execute(new UserCallback<user_lesson_infoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("test", e.getMessage());
                    }

                    @Override
                    public void onResponse(user_lesson_infoJSON response, int id) {
                        //Log.i(Constants.TAG, response.data.user_info.avatar);
                        List<LessonData> l = response.data.infolist;
                        adapter.AddHeaderItem(l);
                    }
                });
    }

    @OnClick(R.id.close_icon)
    public void onViewClicked() {
        finish();
    }
}