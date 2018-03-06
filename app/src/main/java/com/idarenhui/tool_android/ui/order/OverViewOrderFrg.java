package com.idarenhui.tool_android.ui.order;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.model.OrderData;
import com.idarenhui.tool_android.ui.adapter.MyRecyclerviewAdapter;
import com.idarenhui.tool_android.model.OrderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */

public class OverViewOrderFrg extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<OrderData> infoList;
    MyRecyclerviewAdapter adapter;
    //哪个fragment
//    int index=1;
//    public void setIndex(int index) {
//        this.index = index;
//    }
    private MyRecyclerviewAdapter.OnItemClickLisitenter lisitenter;

    private String TAG = "OrderList";
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
        infoList = new ArrayList<>(10);
//        infoList.add(new OrderInfo("test", "test", R.drawable.test));
//        infoList.add(new OrderInfo("test", "test", R.drawable.test));
//        infoList.add(new OrderInfo("test", "test", R.drawable.test));
        //recyclerView = (RecyclerView) view.findViewById(R.id.courseOverView_recycleList);
        recyclerView=(RecyclerView) view.findViewById(R.id.order_recycleList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MyRecyclerviewAdapter(infoList,getContext());
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
    public void setItemLisenter(){
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
    public void setInfoList(ArrayList<OrderData> infoList){
        this.infoList = infoList;
    }

    public void loadFromNet(){}
}
