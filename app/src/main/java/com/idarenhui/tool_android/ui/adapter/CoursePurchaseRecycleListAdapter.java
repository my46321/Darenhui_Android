package com.idarenhui.tool_android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/7/25.
 */

public class CoursePurchaseRecycleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements View.OnClickListener{
    private List<String> list;
    private List<BtnListHodler> listHodlers;
    private Context context;
    private OnItemClickLisitenter onItemClickLisitenter = null;

    private static final int STATE_SELECT = 0;//选中
    private static final int STATE_NOMAL = 1;//为选中但可选
    private static final int STATE_UNUSE = 2;//不可选
    //在外围改变该列表的值，通知刷新状态即可
    private List<Integer> itemState;

    public interface OnItemClickLisitenter{
        void onItemClick(View v, int position, List<Integer> stateList);
        //void onItemStateChange(List<Integer> stateList);
    }

    public CoursePurchaseRecycleListAdapter(List<String> list, Context context){
        this.list = list;
        this.context = context;
        listHodlers = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //!!!!加载布局时一定要加载对应item的布局，因为直接复制别的adapter没有改布局的名字二报错：list为空引用
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_purchase_item,parent, false);
        BtnListHodler vh = new BtnListHodler(view);

        //将创建的View注册点击事件
        view.setOnClickListener(this);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //给itemView设置tag
        holder.itemView.setTag(position);

        String text = list.get(position);
        BtnListHodler btnListHodler = (BtnListHodler) holder;
        listHodlers.add(btnListHodler);
        btnListHodler.button.setText(text);
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
            //一方面执行回掉函数中的事务
            //onItemClickLisitenter.onItemStateChange(itemState);

            onItemClickLisitenter.onItemClick(v, positon, itemState);

            //onItemClickLisitenter.setItemState(itemState);

            //每点击一次刷新一次布局颜色
            updateState();

            //改变被选中的item的字体颜色
            //changeColor(positon);
        }
    }
    private void changeColor(int position){
        for (int i = 0; i < listHodlers.size(); i++) {
            listHodlers.get(i).button.setSelected(false);
        }
        listHodlers.get(position).button.setSelected(true);
//        listHodlers.get(position).button.setBackgroundColor();
    }
    public void initStateList(){
        itemState = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            itemState.add(STATE_NOMAL);
        }
    }
    public List<Integer> getStateList(){
        return itemState;
    }
    //刷新该列表中所有item的颜色
    public void updateState(){
        Log.i(Constants.TAG, "updateState()");
        for (int i = 0; i < list.size(); i++) {
            if (itemState.get(i) == STATE_SELECT){
                select(i);
            }else if (itemState.get(i) == STATE_NOMAL){
                normal(i);
            }else if (itemState.get(i) == STATE_UNUSE){
                unuse(i);
            }
        }
    }
    private void select(int position){
        Log.i(Constants.TAG,"第"+position+"个："+ list.get(position) + "为select");
        listHodlers.get(position).button.setTextColor(context.getResources().getColor(R.color.purchase_text_SELECT));
    }
    private void normal(int position){
        Log.i(Constants.TAG,"第"+position+"个："+ list.get(position) + "为normal");
        listHodlers.get(position).button.setTextColor(context.getResources().getColor(R.color.purchase_text_NORMAL));
    }
    private void unuse(int position){
        Log.i(Constants.TAG,"第"+position+"个："+ list.get(position) + "为unuse");
        listHodlers.get(position).button.setTextColor(context.getResources().getColor(R.color.purchase_text_UNUSE));
    }

    public void AsyncInitItem(List<String> items){
        if (items == null){
            list = new ArrayList<>();
        }
        //清除原有数据
        if (list.size() != 0){
            list.removeAll(list);
        }
        //list.removeAll();
        //添加数据
        Log.i(Constants.TAG, "list原有"+list.size()+"条数据");
        Log.i(Constants.TAG, "添加了"+items.size()+"条数据");
        list.addAll(0,items);
        //刷新列表
        notifyDataSetChanged();
    }

    public class BtnListHodler extends RecyclerView.ViewHolder{
        public TextView button;
        public BtnListHodler(View view){
            super(view);
            button = (TextView) view.findViewById(R.id.purchase_item_btn);
        }
    }
}
