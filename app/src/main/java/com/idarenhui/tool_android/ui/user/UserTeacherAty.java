package com.idarenhui.tool_android.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.model.TeacherData;
import com.idarenhui.tool_android.model.gson.user_teacher_infoJSON;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.userInfoAdapter.teacherListAdapter;
import com.idarenhui.tool_android.ui.course.MasterInfoActivity;
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

public class UserTeacherAty extends Activity {
    @BindView(R.id.teacher_list)
    RecyclerView recyclerView;
    @BindView(R.id.close_icon)
    ImageView closeIcon;
    @BindView(R.id.cancel_edit)
    Button cancelEdit;
    private LinearLayoutManager linearLayoutManager;
    private List<TeacherData> infoList;
    teacherListAdapter adapter;
    private teacherListAdapter.OnItemClickLisitenter lisitenter;
    private boolean hasSwipe=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_teacher_list);
        ButterKnife.bind(this);
        infoList = new ArrayList<>(10);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        LoadFromNet();
        adapter = new teacherListAdapter(infoList, getApplicationContext());
        recyclerView.setAdapter(adapter);
       // recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new MyItemDecoration());

        cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                Log.i("position", Integer.toString(firstPosition) + " " + Integer.toString(lastPosition));
                if(firstPosition==0 && hasSwipe==false)
                {
                    for(int j=0;j<=lastPosition;j++)
                    {
                        View v = linearLayoutManager.getChildAt(j);
                        v.scrollBy(getResources().getDimensionPixelSize(R.dimen.scroll_width_left),0);
                    }
                    hasSwipe=true;
                }
                else if(firstPosition==0 && hasSwipe==true)
                {
                    for(int j=0;j<=lastPosition;j++)
                    {
                        View v = linearLayoutManager.getChildAt(j);
                       // v.scrollBy(getResources().getDimensionPixelSize(R.dimen.scroll_width_right),0);
                        v.scrollTo(0,0);
                    }
                    hasSwipe=false;
                }
            }
        });
        //index=getArguments().getInt("index");
        setItemLisenter();
    }

    public void setItemLisenter() {
        adapter.setOnItemClickLisitenter(new teacherListAdapter.OnItemClickLisitenter() {
            @Override
            public void onItemClick(View v, int position) {
                String s = infoList.get(position).teacher_id;
                Bundle bundle = new Bundle();
                bundle.putString("teacher_id", s);
                Intent intent;
                intent = new Intent(UserTeacherAty.this, MasterInfoActivity.class);
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
                .addParams("category", "teacher")
                .build()//
                .execute(new UserCallback<user_teacher_infoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("test", e.getMessage());
                    }

                    @Override
                    public void onResponse(user_teacher_infoJSON response, int id) {
                        //Log.i(Constants.TAG, response.data.user_info.avatar);
                        List<TeacherData> l = response.data.infolist;
                        adapter.AddHeaderItem(l);
                    }

                });
    }

    @OnClick(R.id.close_icon)
    public void onViewClicked() {
        finish();
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {
        /**
         *
         * @param outRect 边界
         * @param view recyclerView ItemView
         * @param parent recyclerView
         * @param state recycler 内部数据管理
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //设定底部边距为1px
            outRect.set(0, 0, 0, 1);
        }
    }

}
