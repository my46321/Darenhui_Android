package com.idarenhui.tool_android.ui.service;//package com.idarenhui.tool_android.ui.service;
//
//import android.content.Intent;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.dreamspace.superman.R;
//import com.dreamspace.superman.bean.Service;
//import com.dreamspace.superman.ui.base.BaseFragment;
//import com.dreamspace.superman.views.ServiceView;
//import com.dreamspace.util.DisplayUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by Liujilong on 2016/11/8/0008.
// * liujilong.me@gmail.com
// */
//
//public class ServiceListFgt extends BaseFragment {
//
//    @BindView(R.id.fgt_service_list_recycler)
//    RecyclerView mRecycler;
//
//    Adapter mAdapter;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fgt_service_list;
//    }
//
//    @Override
//    public void initViews(View view) {
//        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mAdapter = new ServiceListFgt.Adapter();
//        mRecycler.setAdapter(mAdapter);
//        mRecycler.addItemDecoration(new ServiceListFgt.ItemDecoration(DisplayUtils.dip2px(getActivity(), 13)));
//    }
//
//    @Override
//    public void initData() {
//        List<Service> list = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            Service service = new Service();
//            service.setmPicId(R.drawable.service);
//            service.setTitle("聂刚柔/健身达人");
//            service.setSlogan("十岁乐高达人，教你玩的不只是玩具");
//            list.add(service);
//        }
//        mAdapter.setServiceList(list);
//    }
//
//    @Override
//    protected String tag() {
//        return "ServiceListFgt";
//    }
//
//
//    public static ServiceListFgt newInstance() {
//        Bundle args = new Bundle();
//        ServiceListFgt fragment = new ServiceListFgt();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {//ServiceView
//
//        List<Service> mServiceList;
//
//        void setServiceList(List<Service> list) {
//            mServiceList = list;
//        }
//
//        List<Service> getServiceList() {
//            return mServiceList;
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View v = LayoutInflater.from(getActivity()).inflate(R.layout.fgt_service_list_view, parent, false);
//            ServiceVH serviceVh = new ServiceVH(v);
//            return serviceVh;
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            ServiceVH serviceVh = (ServiceVH) holder;
//            Service service = mServiceList.get(position);
//            serviceVh.sv.setService(service);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mServiceList == null ? 0 : mServiceList.size();
//        }
//    }
//
//
//    class ServiceVH extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.view_service_item)
//        ServiceView sv;
//
//
//        ServiceVH(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            itemView.setOnClickListener(vhClickListener);
//        }
//    }
//
//    private View.OnClickListener vhClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            int layoutPosition = mRecycler.getChildLayoutPosition(v);
////            String id = mAdapter.getServiceList().get(layoutPosition).id;
//            Intent i = new Intent(getActivity(), ServiceDetailAty.class);
////            i.putExtra(ActivityDetailAty.INTENT_ID,id);
//            startActivity(i);
//        }
//    };
//
//    static class ItemDecoration extends RecyclerView.ItemDecoration {
//        int margin;
//
//        ItemDecoration(int margin) {
//            this.margin = margin;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
//                                   RecyclerView.State state) {
//            outRect.top = margin;
//            outRect.left = margin;
//            outRect.right = margin;
//        }
//
//
//    }
//
//
//}
