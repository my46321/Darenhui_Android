package com.idarenhui.tool_android.ui.order;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamspace.util.Logger;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.event.FirstEvent;
import com.idarenhui.tool_android.model.OrderData;
import com.idarenhui.tool_android.model.gson.OrderCountJSON;
import com.idarenhui.tool_android.model.gson.OrderInfoJSON;
import com.idarenhui.tool_android.net.OrderInfoJSONCallback;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.MyRecyclerviewAdapter;
import com.idarenhui.tool_android.ui.adapter.orderItemAdapter.MyRecyclerviewAdapter2;
import com.idarenhui.tool_android.model.OrderInfo;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/7/19.
 */

public class OverViewOrderFrg2 extends OverViewOrderFrg {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<OrderData> infoList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String TAG = "Test";
    MyRecyclerviewAdapter2 adapter;
//    @BindView(R.id.dynamic_tab_count_2)
//    TextView tab_count_2;

    //哪个fragment
//    int index=1;
//    public void setIndex(int index) {
//        this.index = index;
//    }
    private MyRecyclerviewAdapter.OnItemClickLisitenter lisitenter;
//    public void setItemLisenter(){
//        adapter.setOnItemClickLisitenter(new MyRecycleListAdapter.OnItemClickLisitenter() {
//            @Override
//            public void onItemClick(View v, int position) {
//                startActivity(new Intent(getActivity(),CourseDetailActivity.class));
//            }
//        });
//    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.viewpage_recyclelist, container, false);
        View view=inflater.inflate(R.layout.order_recyclelist_frg,container,false);
        infoList = new ArrayList<>();
//        infoList.add(new OrderInfo("【茗礼】茶道美学系列课程", "待接单", R.drawable.test));
//        infoList.add(new OrderInfo("【茗礼】茶道美学系列课程", "待接单", R.drawable.test));
//        infoList.add(new OrderInfo("【茗礼】茶道美学系列课程", "待接单", R.drawable.test));
        //recyclerView = (RecyclerView) view.findViewById(R.id.courseOverView_recycleList);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        initPullRefresh();
//        loadCountFromNet();
        recyclerView=(RecyclerView) view.findViewById(R.id.order_recycleList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        //loadFromNet("0","3","10");
        //loadFromNet("0","1","10");
        //Log.i("123", infoList.get(0).lesson_title);
       // Log.i("231", infoList.get(0).lesson_img);
//        if(true == UserInfo.hasLogin)
//            loadFromNet("0","3","10",UserInfo.token);
        adapter = new MyRecyclerviewAdapter2(infoList,getContext());
//        adapter.singleorderButton.setText("联系客服");
//        adapter.singleorderText2.setText("待接单");
//        adapter.singleorderText2.setTextColor(getResources().getColor(R.color.colorCroci));
        // MyRecyclerviewAdapter adapter=new MyRecyclerviewAdapter(list);
//        adapter.setOnItemClickLisitenter(new RecycleViewLisitenter.onItemClickLisitenter() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Log.i(TAG, "onItemClick()!");
//                Toast.makeText(getActivity(), list.get(position).getTitle(), Toast.LENGTH_SHORT);
//            }
//        });

        recyclerView.setAdapter(adapter);
        //index=getArguments().getInt("index");
        setItemLisenter();
        return view;
    }
    public void initPullRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clearList();
                        Log.i(TAG, "OverViewOrderFrg1.onRefresh()");
                        loadFromNet("0", "3", "10", UserInfo.token);
                        //刷新完成
                        EventBus.getDefault().post(new FirstEvent("FirstEvent has done"));
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }
    public void loadData()
    {
        adapter.clearList();
        if(true == UserInfo.hasLogin)
            loadFromNet("0","3","10",UserInfo.token);
    }
    @Override
    public void onHiddenChanged(boolean hidd) {
       // super.setUserVisibleHint(hidd);
        if (hidd) {

            //相当于Fragment的onResume
        } else {
            adapter.clearList();
            if(true == UserInfo.hasLogin)
                loadFromNet("0","3","10",UserInfo.token);
            //相当于Fragment的onPause
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void setItemLisenter(){
        adapter.setOnItemClickLisitenter(new MyRecyclerviewAdapter.OnItemClickLisitenter() {
                   @Override
                    public void onItemClick(View v, int position) {
                       String s=infoList.get(position).order_id;
                       Bundle bundle=new Bundle();
                       bundle.putString("order_id",s);
                       Intent intent=new Intent(getActivity(), WaitAcceptActivity.class);
                       intent.putExtras(bundle);
                       startActivity(intent);
                    }
                });
//        switch (index)
//        {
//            case 1:
//                adapter.setOnItemClickLisitenter(new MyRecyclerviewAdapter.OnItemClickLisitenter() {
//                    @Override
//                    public void onItemClick(View v, int position) {
//                        startActivity(new Intent(getActivity(),PurchaseDetailActivity.class));
//                    }
//                });
//            case 2:
//                adapter.setOnItemClickLisitenter(new MyRecyclerviewAdapter.OnItemClickLisitenter() {
//                    @Override
//                    public void onItemClick(View v, int position) {
//                        startActivity(new Intent(getActivity(),CourseDetailActivity.class));
//                    }
//                });
//            case 3:
//                adapter.setOnItemClickLisitenter(new MyRecyclerviewAdapter.OnItemClickLisitenter() {
//                    @Override
//                    public void onItemClick(View v, int position) {
//                        startActivity(new Intent(getActivity(),WaitEvaluateActivity.class));
//                    }
//                });
//            case 4:
//                adapter.setOnItemClickLisitenter(new MyRecyclerviewAdapter.OnItemClickLisitenter() {
//                    @Override
//                    public void onItemClick(View v, int position) {
//                        startActivity(new Intent(getActivity(),OrderComplishmentAcitivity.class));
//                    }
//                });
//            case 5:
//                adapter.setOnItemClickLisitenter(new MyRecyclerviewAdapter.OnItemClickLisitenter() {
//                    @Override
//                    public void onItemClick(View v, int position) {
//                        startActivity(new Intent(getActivity(),RefundComlishmentActivity.class));
//                    }
//                });
//
//        }
    }
//    public void loadCountFromNet(){
//        if (UserInfo.hasLogin==true) {
//            OkHttpUtils
//                    .get()//
//                    .url("https://api.idarenhui.com/DRH_Test/v1.0/order/count")//
//                    .addHeader("Access-Token", UserInfo.token)
//                    .build()//
//                    .execute(new UserCallback<OrderCountJSON>() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            Log.i("test", e.getMessage());
//                        }
//
//                        @Override
//                        public void onResponse(OrderCountJSON response, int id) {
//                            List<String> l = response.data.orders_count;
//                            tab_count_2.setText(l.get(3));
//                        }
//                    });
//        }
//        else
//        {
//            ContactDialog.showTipDialog(getContext(),"还未登录，请登录后查看个人订单");
//        }
//    }
    public void setInfoList(ArrayList<OrderData> infoList){
        this.infoList = infoList;
    }

    public void loadFromNet(String offset,String state,String pagesize,String token){
        OkHttpUtils
                .get()//
                .url("https://api.idarenhui.com/DRH_Test/v1.0/order/list")//
                .addHeader("Access-Token",token)
                .addParams("offset", offset)//
                .addParams("state", state)//
                .addParams("pagesize",pagesize)
                .build()//
                .execute(new OrderInfoJSONCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("test",e.getMessage());
                    }

                    @Override
                    public void onResponse(OrderInfoJSON response, int id) {
                        List<OrderData> l=response.data.orders;
                        for(int i=0;i<l.size();i++)
                        {
                            l.get(i).TYPE=3;
                        }
                        adapter.AddHeaderItem(l);
                    }
                });
    }
}
