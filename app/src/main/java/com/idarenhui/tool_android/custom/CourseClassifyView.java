package com.idarenhui.tool_android.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idarenhui.tool_android.R;

/**
 * Created by Administrator on 2017/7/14.
 */

public class CourseClassifyView extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
    private Context context;


    public CourseClassifyView(Context context) {
        super(context);
        init();
        this.context = context;
    }

    public CourseClassifyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        this.context = context;
    }

    public CourseClassifyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        this.context = context;
    }

    public void init(){
        inflate(getContext(), R.layout.course_classify_one, this);
//        imageView = (ImageView)findViewById(R.id.classify_image);
//        textView = (TextView)findViewById(R.id.classify_text);
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(new Intent((Activity)context, CourseClassifyActivity.class));
//            }
//        });
    }


}
