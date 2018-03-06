package com.idarenhui.tool_android.ui.adapter.userInfoAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.model.ExperienceData;
import com.idarenhui.tool_android.model.LessonData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class experienceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<ExperienceData> list;
    private OnItemClickLisitenter onItemClickLisitenter = null;
    private Context context;

    public static interface OnItemClickLisitenter {
        void onItemClick(View v, int position);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickLisitenter != null) {
            onItemClickLisitenter.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickLisitenter(OnItemClickLisitenter lisitenter) {
        this.onItemClickLisitenter = lisitenter;
    }

    public experienceListAdapter(List<ExperienceData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //将数据与界面进行绑定
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //给itemView设置tag
        holder.itemView.setTag(position);

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ExperienceData data = list.get(position);
        //myViewHolder.picture.setImageResource(data.getPictureId());
        Glide.with(context).load(data.img).error(R.drawable.test).centerCrop().into(myViewHolder.picture);
        myViewHolder.name.setText(data.title);
        if (data.state == 1) {
            myViewHolder.statekind.setText("已审核");
            //myViewHolder.viewText.setText(data.getViewText());
        }
        else if(data.state == 2){
            myViewHolder.statekind.setText("已体验");
        }
        else if(data.state == 3){
            myViewHolder.statekind.setText("已下架");
        }else if(data.state ==0){
            myViewHolder.statekind.setText("待审核");
        }
    }

    public void AddHeaderItem(List<ExperienceData> items) {
        if (items == null) {
            list = new ArrayList<>();
        }
        //添加数据
        list.addAll(0, items);
        //刷新列表
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_experience, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView statekind;
        public MyViewHolder(View view) {
            super(view);
            picture = (ImageView) view.findViewById(R.id.experience_img);
            name = (TextView) view.findViewById(R.id.experience_title);
            statekind = (TextView) view.findViewById(R.id.experience_state);
            // button.setVisibility(View.GONE);
        }
    }
}