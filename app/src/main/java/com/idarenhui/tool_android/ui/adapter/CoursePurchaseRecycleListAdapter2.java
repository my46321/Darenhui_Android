package com.idarenhui.tool_android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.model.ClassifyInfo;
import com.idarenhui.tool_android.model.PurchaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/7/26.
 */

public class CoursePurchaseRecycleListAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements View.OnClickListener{
    private List<PurchaseInfo> list;
    private List<MyViewHolder> listHodlers;
    private Context context;
    private OnItemClickLisitenter onItemClickLisitenter = null;

    public void AddHeaderItem(List<PurchaseInfo> items){
//        if (items == null){
//            list = new ArrayList<>();
//        }
//        //添加数据
//        list.addAll(0,items);
        list=items;
        //刷新列表
        notifyDataSetChanged();
    }

    public interface OnItemClickLisitenter{
        void onItemClick(View v, int position);
    }

    public CoursePurchaseRecycleListAdapter2(List<PurchaseInfo> list, Context context){
        this.list = list;
        this.context = context;
        this.listHodlers = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_purchase_item3,parent, false);
        MyViewHolder vh = new MyViewHolder(view);

        //将创建的View注册点击事件
        view.setOnClickListener(this);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //给itemView设置tag
        holder.itemView.setTag(position);

        MyViewHolder myHodler = (MyViewHolder) holder;
        listHodlers.add(myHodler);

        myHodler.site.setText(list.get(position).getSite());
        myHodler.price.setText(list.get(position).getPrice());
        myHodler.time.setText(list.get(position).getTime());
        myHodler.duration.setText(list.get(position).getDuration());
    }
    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }else {
            return 0;
        }
    }

    public void setOnItemClickLisitenter(OnItemClickLisitenter lisitenter){
        this.onItemClickLisitenter = lisitenter;
    }
    @Override
    public void onClick(View v) {
        if (onItemClickLisitenter != null){
            int positon = (int)v.getTag();
            onItemClickLisitenter.onItemClick(v, positon);
            checkChange(positon);
        }
    }
    private void checkChange(int position){
        for (int i = 0; i < listHodlers.size(); i++) {
            listHodlers.get(i).radioBtn.setChecked(false);
        }
        listHodlers.get(position).radioBtn.setChecked(true);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView site, time, duration, price;
        public RadioButton radioBtn;
        public MyViewHolder(View view){
            super(view);
            site = (TextView)view.findViewById(R.id.purchase_site);
            time = (TextView)view.findViewById(R.id.purchase_time);
            duration = (TextView)view.findViewById(R.id.purchase_duration);
            price = (TextView)view.findViewById(R.id.purchase_price);
            radioBtn = (RadioButton)view.findViewById(R.id.purchase_choose_button);
        }
    }
}
