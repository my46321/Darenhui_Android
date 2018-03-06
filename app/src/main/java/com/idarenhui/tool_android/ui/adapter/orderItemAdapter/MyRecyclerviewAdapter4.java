package com.idarenhui.tool_android.ui.adapter.orderItemAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.model.OrderData;
import com.idarenhui.tool_android.model.OrderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyRecyclerviewAdapter4 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<OrderData> list;
    private com.idarenhui.tool_android.ui.adapter.MyRecyclerviewAdapter.OnItemClickLisitenter onItemClickLisitenter = null;
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

    public void setOnItemClickLisitenter(com.idarenhui.tool_android.ui.adapter.MyRecyclerviewAdapter.OnItemClickLisitenter lisitenter) {
        this.onItemClickLisitenter = lisitenter;
    }

    public MyRecyclerviewAdapter4(List<OrderData> list,Context context) {
        this.list = list;
        this.context=context;
    }

    //将数据与界面进行绑定
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //给itemView设置tag
        holder.itemView.setTag(position);

        com.idarenhui.tool_android.ui.adapter.MyRecyclerviewAdapter.MyViewHolder myViewHolder = (com.idarenhui.tool_android.ui.adapter.MyRecyclerviewAdapter.MyViewHolder) holder;
        OrderData data = list.get(position);
//        myViewHolder.picture.setImageResource(data.getPictureId());
//        myViewHolder.title.setText(data.getTitle());
//        myViewHolder.viewText.setText(data.getViewText());
        Glide.with(context).load(data.lesson_img).error(R.drawable.test).centerCrop().into(myViewHolder.picture);
        myViewHolder.title.setText(data.lesson_title);
    }

    public void clearList()
    {
        list.clear();
        notifyDataSetChanged();
    }

    public void AddHeaderItem(List<OrderData> items){
        if (items == null){
            list = new ArrayList<>();
        }
        //添加数据
        list.addAll(0,items);
        //刷新列表
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order4, parent, false);
        com.idarenhui.tool_android.ui.adapter.MyRecyclerviewAdapter.MyViewHolder vh = new com.idarenhui.tool_android.ui.adapter.MyRecyclerviewAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView title;
        public TextView viewText;
        public Button button;
        public MyViewHolder(View view) {
            super(view);
            picture = (ImageView) view.findViewById(R.id.singleorder_image1);
            title = (TextView) view.findViewById(R.id.singleorder_text1);
            viewText = (TextView) view.findViewById(R.id.singleorder_text2);
            button = (Button) view.findViewById(R.id.singleorder_button);

            // button.setVisibility(View.GONE);
        }
    }
}
