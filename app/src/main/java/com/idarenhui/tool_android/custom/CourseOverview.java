package com.idarenhui.tool_android.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idarenhui.tool_android.ui.course.CourseDetailActivity;
import com.idarenhui.tool_android.R;


/**
 * Created by chen on 2017/7/12.
 */

public class CourseOverview extends LinearLayout {
    private ImageView picture;
    private TextView title;
    private TextView viewText;

    public ImageView getPicture() {
        return picture;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getViewText() {
        return viewText;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public void setViewText(TextView viewText) {
        this.viewText = viewText;
    }


    //    当放在一个list中时通过id进行判别
    private int item_id;
    private Context context;

    public CourseOverview(Context context){
        super(context);
        this.context=context;
        init();
    }

    public CourseOverview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public CourseOverview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

//    public CourseOverview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    public void init(){
        inflate(getContext(), R.layout.course_overview, this);
        picture=(ImageView) findViewById(R.id.CourseOverView_picture);
        title=(TextView) findViewById(R.id.CourseOverView_title);
        viewText=(TextView) findViewById(R.id.CourseOverView_viewText);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent((Activity)context,CourseDetailActivity.class));
            }
        });
    }

//    public void setContent(CourseOverviewInfo info){
//        title.setText(info.getTitle());
//        viewText.setText(info.getViewText());
//
//        //图片的加载待定
//        //Glide.....
//        picture.setImageResource(info.getPictureId());
//    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getItem_id() {
        return item_id;
    }
}
