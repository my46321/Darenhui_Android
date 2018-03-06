package com.idarenhui.tool_android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.model.ClassifyInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/7/21.
 */

public class ClassifyRecycleListAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private List<ClassifyInfo> list;
    private ClassifyRecycleListAdapter.OnItemClickLisitenter onItemClickLisitenter = null;
    private Context context;

    private String TAG = "Test";

    public static interface OnItemClickLisitenter{
        void onItemClick(View v, int position);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickLisitenter != null){
            onItemClickLisitenter.onItemClick(v, (int)v.getTag());
        }
    }
    public void setOnItemClickLisitenter(ClassifyRecycleListAdapter.OnItemClickLisitenter lisitenter){
        this.onItemClickLisitenter = lisitenter;
    }

    public ClassifyRecycleListAdapter(List<ClassifyInfo> list, Context context){
        this.context = context;
        this.list = list;
    }

    //将数据与界面进行绑定
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //给itemView设置tag
        holder.itemView.setTag(position);

        ClassifyRecycleListAdapter.ClassifyViewHolder  viewHolder = (ClassifyViewHolder)holder;
        ClassifyInfo data = list.get(position);

        Glide.with(context).load(data.getPicURL()).error(R.drawable.test).centerCrop().into(viewHolder.picture);
        viewHolder.title.setText(data.getTitle());
        viewHolder.linearLayout.setBackgroundResource(data.getBackBackgourndColor());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_classify_one,parent, false);
        ClassifyRecycleListAdapter.ClassifyViewHolder vh = new ClassifyRecycleListAdapter.ClassifyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);

        return vh;
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }else{
            return 0;
        }
    }

    public void AddHeaderItem(List<ClassifyInfo> items){
        if (items == null){
            list = new ArrayList<>();
        }
        //添加数据
        list.addAll(0,items);
        //刷新列表
        notifyDataSetChanged();
    }

    public static class ClassifyViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView title;
        public LinearLayout linearLayout;
        public ClassifyViewHolder(View view){
            super(view);
            linearLayout = (LinearLayout)view.findViewById(R.id.classify_one_background);
            picture=(ImageView)view.findViewById(R.id.classify_one_picture);
            title=(TextView)view.findViewById(R.id.classify_one_text);
        }

    }
}