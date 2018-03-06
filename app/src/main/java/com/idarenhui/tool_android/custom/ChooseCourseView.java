package com.idarenhui.tool_android.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idarenhui.tool_android.R;

//import com.example.administrator.test1.R;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by Administrator on 2017/7/12.
 */

public class ChooseCourseView extends RelativeLayout{
    private RadioButton RB1;
    private TextView tv_info;
    private TextView tv_duration;
    private TextView tv_time;
    private TextView tv_locus;
    public ChooseCourseView(Context context) {
        super(context);
        init();
    }

    public ChooseCourseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChooseCourseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        inflate(getContext(), R.layout.view_choose_course, this);
        RB1=(RadioButton) findViewById(R.id.purchase_choose_button);
        tv_info=(TextView) findViewById(R.id.purchase_info);
        tv_duration=(TextView) findViewById(R.id.purchase_duration);
        tv_time=(TextView) findViewById(R.id.purchase_time);
        tv_locus=(TextView) findViewById(R.id.purchase_locus);
    }
}
