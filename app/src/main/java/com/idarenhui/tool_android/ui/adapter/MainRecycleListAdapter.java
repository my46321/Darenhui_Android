package com.idarenhui.tool_android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.custom.CustomToast;
import com.idarenhui.tool_android.model.CourseOverviewInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/7/17.
 */

public class MainRecycleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private List<CourseOverviewInfo> list;
    private OnItemClickLisitenter onItemClickLisitenter = null;
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
    public void setOnItemClickLisitenter(OnItemClickLisitenter lisitenter){
        this.onItemClickLisitenter = lisitenter;
    }

    public MainRecycleListAdapter(List<CourseOverviewInfo> list, Context context){
        this.context = context;
        this.list = list;
    }

    //将数据与界面进行绑定
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //给itemView设置tag
        holder.itemView.setTag(position);

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        CourseOverviewInfo data = list.get(position);
        Glide.with(context).load(data.getImgURL()).error(R.drawable.test).centerCrop().into(myViewHolder.picture);
        //myViewHolder.picture.setImageResource(data.getPictureId());
        myViewHolder.title.setText(data.getTitle());
        myViewHolder.viewText.setText(data.getViewText());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_overview,parent, false);
        MyViewHolder vh = new MyViewHolder(view);
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

    public void clearList()
    {
        list.clear();
        notifyDataSetChanged();
    }

    public void AddHeaderItem(List<CourseOverviewInfo> items){
        if (items == null){
            list = new ArrayList<>();
        }
        //添加数据
        list.addAll(0,items);
        //刷新列表
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView title;
        public TextView viewText;
        public MyViewHolder(View view){
            super(view);
            picture=(ImageView)view.findViewById(R.id.CourseOverView_picture);
            title=(TextView)view.findViewById(R.id.CourseOverView_title);
            viewText=(TextView)view.findViewById(R.id.CourseOverView_viewText);
        }

    }
}
