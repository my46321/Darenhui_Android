package com.idarenhui.tool_android.ui.course;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.ClassifyRecycleListAdapter;
import com.idarenhui.tool_android.model.ClassifyInfo;
import com.idarenhui.tool_android.model.gson.ClassifyInfoJSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.utils.Exceptions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by chen on 2017/7/21.
 */

public class ClassifyCourseListFgt extends Fragment{
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<ClassifyInfo> infoList;
    private ClassifyRecycleListAdapter.OnItemClickLisitenter lisitenter;
    //下拉刷新
    //private SwipeRefreshLayout swipeRefreshLayout;
    private ClassifyRecycleListAdapter listAdapter;

    private String TAG = "Test";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.viewpage_classifylist,container,false);
        loadData();
        init(view);
        //initPullRefresh();
        return view;
    }

    public void init(View view){
        //swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView)view.findViewById(R.id.courseOverView_recycleList);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);

        //注意!!！因为loadData()是异步加载的，所以此处初始化时infoList的信息可能并没加载到
        //所以在加载完数据时采取 listAdapter.add
        //Log.i(TAG , String.valueOf(infoList.size()));
        infoList = new ArrayList<>();
        listAdapter = new ClassifyRecycleListAdapter(infoList,getContext());
        setItemLisenter();

        recyclerView.setAdapter(listAdapter);
    }
    public void setInfoList(List<ClassifyInfo> infoList){
        this.infoList = infoList;
    }

    public void loadData(){
        //从服务器加载数据
        loadFromNet();
    }

    //设置recycleList中item的点击事件
    public void setItemLisenter(){
        listAdapter.setOnItemClickLisitenter(new ClassifyRecycleListAdapter.OnItemClickLisitenter(){
            @Override
            public void onItemClick(View v, int position) {
                //跳转至相应的分类下
            }
        });
    }

    //从服务器加载分类列表信息
    public void loadFromNet(){
        //获取分类列表
        String  url = "https://api.idarenhui.com/DRH_Test/v1.0/index/categorylist";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new UserCallback<ClassifyInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(ClassifyInfoJSON response, int id) {
                        //从服务器加载的分类的list;
                        List<ClassifyInfoJSON.Data.CourseData> courseDatas =  response.data.categoryList;
                        List<ClassifyInfo> dataList = new ArrayList<>();
                        String _id, title, imgUrl;
                        for (int i = 0; i < courseDatas.size(); i++) {
                            _id = courseDatas.get(i)._id;
                            title = courseDatas.get(i).title;
                            imgUrl = courseDatas.get(i).img;
                            dataList.add(new ClassifyInfo(title, imgUrl, _id));
                        }
                        //将解析的获得数据放入私有成员infoList中
                        //setInfoList(dataList);
                        //利用适配器将数据加至recycleList
                        listAdapter.AddHeaderItem(dataList);
                    }
                });
    }
}