package com.idarenhui.tool_android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.custom.CirclePictureTransformation;
import com.idarenhui.tool_android.model.CommentData;
import com.idarenhui.tool_android.model.OrderData;
import com.idarenhui.tool_android.ui.adapter.userInfoAdapter.lessonListAdapter;
import com.idarenhui.tool_android.ui.order.WaitPayActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class CourseCommentAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;
    private List<CommentData> list;
    public CourseCommentAdapter(List<CommentData> list, Context context) {
        this.list = list;
        this.context=context;
    }
    public void AddHeaderItem(List<CommentData> items){
        if (items == null){
            list = new ArrayList<>();
        }
        Log.i("adapter","添加数据");
        //添加数据
        list.addAll(0,items);
        //刷新列表
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setTag(position);

        CourseCommentAdapter.MyViewHolder myViewHolder = (CourseCommentAdapter.MyViewHolder) holder;
        CommentData data=list.get(position);
        Glide.with(context).load(data.avatar).bitmapTransform(new CirclePictureTransformation(context)).error(R.drawable.test).into(myViewHolder.userAvatar);
        myViewHolder.userName.setText(data.name);
        myViewHolder.courseComment.setText(data.comment);
        //double m_time=Double.valueOf(data.time);
        //Log.i("CourseCommentAdapter",data.time);;
        Date date=new Date((long)data.time*1000);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        myViewHolder.commentDate.setText(sdf.format(date));
        switch (data.comment_choice){
            case "差评！":
                myViewHolder.courseLevel.setImageResource(R.drawable.no_star);
                break;
            case "不满意":
                myViewHolder.courseLevel.setImageResource(R.drawable.one_star);
                break;
            case "非常满意":
                myViewHolder.courseLevel.setImageResource(R.drawable.two_stars);
                break;
            case "基本满意":
                myViewHolder.courseLevel.setImageResource(R.drawable.three_stars);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_comment, parent, false);
        CourseCommentAdapter.MyViewHolder vh = new CourseCommentAdapter.MyViewHolder(view);
        return vh;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView userAvatar;
        public TextView userName;
        public TextView commentDate;
        public TextView courseComment;
        public ImageView courseLevel;
        public MyViewHolder(View view) {
            super(view);
            userAvatar=(ImageView)view.findViewById(R.id.user_img);
            userName=(TextView)view.findViewById(R.id.user_name);
            commentDate=(TextView)view.findViewById(R.id.comment_date);
            courseComment=(TextView)view.findViewById(R.id.course_comment);
            courseLevel=(ImageView)view.findViewById(R.id.star_level);
            // button.setVisibility(View.GONE);
        }
    }
}
