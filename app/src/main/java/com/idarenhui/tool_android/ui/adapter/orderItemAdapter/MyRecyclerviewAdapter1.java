package com.idarenhui.tool_android.ui.adapter.orderItemAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.model.OrderData;
import com.idarenhui.tool_android.model.OrderInfo;
import com.idarenhui.tool_android.ui.order.EditEvaluation;
import com.idarenhui.tool_android.ui.order.WaitPayActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyRecyclerviewAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
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

    public MyRecyclerviewAdapter1(List<OrderData> list,Context context) {
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
        int state=data.TYPE;
        final String s=data.order_id;
        switch(state)
        {
            case 0:
                myViewHolder.viewText.setText("已取消");
                myViewHolder.viewText.setTextColor(context.getResources().getColor(R.color.color_black));
                myViewHolder.button.setVisibility(View.GONE);
                break;
            case 1:
                myViewHolder.viewText.setText("待付款");
                myViewHolder.viewText.setTextColor(context.getResources().getColor(R.color.colorRed));
                myViewHolder.button.setText("去支付");
                myViewHolder.button.setVisibility(View.VISIBLE);
                myViewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //context.startActivity(new Intent(context, EditEvaluation.class));
                        Bundle bundle=new Bundle();
                        bundle.putString("order_id",s);
                        Intent intent=new Intent(context, WaitPayActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                break;
            case 2:
                myViewHolder.viewText.setText("支付中");
                myViewHolder.viewText.setTextColor(context.getResources().getColor(R.color.color_black));
                myViewHolder.button.setVisibility(View.GONE);
                break;
            case 3:
                myViewHolder.viewText.setText("待接单");
                myViewHolder.viewText.setTextColor(context.getResources().getColor(R.color.colorOrange));
                myViewHolder.button.setText("联系客服");
                myViewHolder.button.setVisibility(View.VISIBLE);
                myViewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContactDialog d=new ContactDialog();
                        d.showDialog(context);
                    }
                });
                break;
            case 4:
                myViewHolder.viewText.setText("服务中");
                myViewHolder.viewText.setTextColor(context.getResources().getColor(R.color.color_black));
                myViewHolder.button.setVisibility(View.GONE);
                break;
            case 5:
                myViewHolder.viewText.setText("待评价");
                myViewHolder.viewText.setTextColor(context.getResources().getColor(R.color.colorGreen));
                myViewHolder.button.setText("去评价");
                myViewHolder.button.setVisibility(View.VISIBLE);
                myViewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle=new Bundle();
                        bundle.putString("order_id",s);
                        Intent intent=new Intent(context, EditEvaluation.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                break;
            case 6:
                myViewHolder.viewText.setText("已完成");
                myViewHolder.viewText.setTextColor(context.getResources().getColor(R.color.color_black));
                myViewHolder.button.setVisibility(View.GONE);
                break;
            case 7:
                myViewHolder.viewText.setText("已退款");
                myViewHolder.viewText.setTextColor(context.getResources().getColor(R.color.color_black));
                myViewHolder.button.setVisibility(View.GONE);
                break;
        }
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order1, parent, false);
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