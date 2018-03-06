package com.idarenhui.tool_android.ui.adapter.userInfoAdapter;

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
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.custom.CustomToast;
import com.idarenhui.tool_android.model.LessonData;
import com.idarenhui.tool_android.model.gson.CollectJSON;
import com.idarenhui.tool_android.net.UserCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/9/7.
 */

public class lessonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<LessonData> list;
    private OnItemClickLisitenter onItemClickLisitenter = null;
    private Context context;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


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

    public lessonListAdapter(List<LessonData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //将数据与界面进行绑定
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //给itemView设置tag
        holder.itemView.setTag(position);

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        LessonData data = list.get(position);
        //myViewHolder.picture.setImageResource(data.getPictureId());
        Glide.with(context).load(data.img).error(R.drawable.test).centerCrop().into(myViewHolder.picture);
        myViewHolder.name.setText(data.title);
        myViewHolder.intro.setText(data.description);
        myViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lesson_id=list.get(position)._id;
                try {
                    JSONObject object = new JSONObject();
                    object.put("category", "lesson");
                    object.put("id", lesson_id);
                    String json = object.toString();
                    OkHttpUtils
                            .delete()
                            .url("https://api.idarenhui.com/DRH_Test/v1.0/collect")
                            .addHeader("Access-Token", UserInfo.token)
                            .requestBody(RequestBody.create(JSON, json))//
                            .build()//
                            .execute(new UserCallback<CollectJSON>() {
                                @Override
                                public void onResponse(CollectJSON response, int id) {
                                    //Log.i("generate_order_succeed",response);
                                    //new PaymentTask().execute(order_id);
                                    //showDialog();
                                    list.remove(list.get(position));
                                    notifyDataSetChanged();
                                    CustomToast.toastMsgShort(context, "取消收藏");
                                }

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    e.printStackTrace();
                                    Log.i("collect_fail", e.getMessage());
                                }
                            });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        //myViewHolder.viewText.setText(data.getViewText());
    }

    public void AddHeaderItem(List<LessonData> items) {
        if (items == null) {
            list = new ArrayList<>();
        }
        Log.i("lessonAdapter", "添加入列表");
        //添加数据
        list.addAll(0, items);
        Log.i("lessonAdapter", Integer.toString(list.size()));
        //刷新列表
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lesson, parent, false);
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
        public TextView intro;
        public Button cancel;

        public MyViewHolder(View view) {
            super(view);
            cancel = (Button) view.findViewById(R.id.cancel_collect);
            picture = (ImageView) view.findViewById(R.id.lesson_img);
            name = (TextView) view.findViewById(R.id.lesson_title);
            intro = (TextView) view.findViewById(R.id.lesson_intro);
            // button.setVisibility(View.GONE);
        }
    }
}