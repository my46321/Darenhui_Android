package com.idarenhui.tool_android.ui.course;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.model.CommentData;
import com.idarenhui.tool_android.model.LessonData;
import com.idarenhui.tool_android.model.gson.CourseCommentJSON;
import com.idarenhui.tool_android.model.gson.user_lesson_infoJSON;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.CourseCommentAdapter;
import com.idarenhui.tool_android.ui.adapter.userInfoAdapter.lessonListAdapter;
import com.idarenhui.tool_android.ui.base.BaseFragment;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/18.
 */

public class CourseCommentFgt extends BaseFragment {

    private LinearLayoutManager linearLayoutManager;
    private List<CommentData> infoList;
    CourseCommentAdapter adapter;
    Unbinder unbinder;
    private SwipeMenuRecyclerView recyclerView;
    private int offset;
    private String lesson_id;
    private RelativeLayout whenNoComment;

    @Override
    public void initViews(View view) {
        lesson_id = mActivity.getIntent().getStringExtra("lesson_id");
        offset=0;
        recyclerView=(SwipeMenuRecyclerView)view.findViewById(R.id.comment_recyclerview);
        whenNoComment=(RelativeLayout)view.findViewById(R.id.when_no_comment);
        //onViewClicked(view);
    }

    @Override
    public void initData() {
        infoList = new ArrayList<>(100);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.i("CourseCommentFgt","即将设置相关操作");


        recyclerView.setLongPressDragEnabled(true); // 开启拖拽。
        recyclerView.setItemViewSwipeEnabled(false); // 开启滑动删除。
        OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                int fromPosition = srcHolder.getAdapterPosition();
                int toPosition = targetHolder.getAdapterPosition();
                if(fromPosition<infoList.size() && toPosition<infoList.size()) {
                    // Item被拖拽时，交换数据，并更新adapter。
                    Collections.swap(infoList, fromPosition, toPosition);
                    adapter.notifyItemMoved(fromPosition, toPosition);
                }
                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                int position = srcHolder.getAdapterPosition();
                if(position<infoList.size()) {
                    // Item被侧滑删除时，删除数据，并更新adapter。
                    infoList.remove(position);
                    adapter.notifyItemRemoved(position);
                }
            }
        };
        recyclerView.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。
        recyclerView.useDefaultLoadMore(); // 使用默认的加载更多的View。
        SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeMenuRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                // 该加载更多啦。
                loadFromNet();
             // 请求数据，并更新数据源操作。
                adapter.notifyDataSetChanged();
                // 数据完更多数据，一定要调用这个方法。
                // 第一个参数：表示此次数据是否为空。
                // 第二个参数：表示是否还有更多数据。
                recyclerView.loadMoreFinish(false, true);

                // 如果加载失败调用下面的方法，传入errorCode和errorMessage。
                // errorCode随便传，你自定义LoadMoreView时可以根据errorCode判断错误类型。
                // errorMessage是会显示到loadMoreView上的，用户可以看到。
                 recyclerView.loadMoreError(0, "请求失败");
            }
        };
        recyclerView.setLoadMoreListener(mLoadMoreListener); // 加载更多的监听。


        loadFromNet();
        adapter = new CourseCommentAdapter(infoList, mActivity);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.course_comment_info;
    }

    private void loadFromNet() {
        OkHttpUtils
                .get()//
                .url("https://api.idarenhui.com/DRH_Test/v1.0/lesson/comment")//
                //.addHeader("Access-Token", UserInfo.token)
                .addParams("pagesize", "10")
                .addParams("offset",Integer.toString(offset))
                .addParams("lesson_id",lesson_id)
                .build()//
                .execute(new UserCallback<CourseCommentJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("test", e.getMessage());
                        Log.i("CourseCommentFgt","load失败");
                    }

                    @Override
                    public void onResponse(CourseCommentJSON response, int id) {
                        //Log.i(Constants.TAG, response.data.user_info.avatar);
                        Log.i("CourseCommentFgt","load成功");
                        List<CommentData> l = response.data.comments;
                        if (l.size()==0)
                        {
                            whenNoComment.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            whenNoComment.setVisibility(View.INVISIBLE);
                        }
                        offset+=l.size();
                        adapter.AddHeaderItem(l);
                    }

                });
    }
}
