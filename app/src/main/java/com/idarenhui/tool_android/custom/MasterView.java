package com.idarenhui.tool_android.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.model.MasterInfo;
import com.idarenhui.tool_android.model.gson.CourseDetialInfoJSON;
import com.idarenhui.tool_android.model.gson.MasterDetailInfoJSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chen on 2017/7/25.
 */

public class MasterView extends RelativeLayout {
    @BindView(R.id.master_name)
    TextView masterName;
    @BindView(R.id.master_intro)
    TextView masterIntro;
    @BindView(R.id.master_tag1)
    TextView masterTag1;
    @BindView(R.id.master_tag2)
    TextView masterTag2;
    @BindView(R.id.master_avatar)
    ImageView masterAvatar;

    private Context context;

    public MasterView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MasterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MasterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        Log.i(Constants.TAG, "MasterView.init()");
        inflate(getContext(), R.layout.view_master, this);
        ButterKnife.bind(this,this);
        //设置默认值
        setContent(new MasterInfo("test", "", "", "", new ArrayList<String>()));
    }
    //直接用MasterInfo赋值
    public void setContent(MasterInfo info){
        masterName.setText(info.getName());
        masterIntro.setText(info.getDescription());
        List<String> tagList = info.getTags();

        if (tagList.size() >= 2){
            masterTag1.setText(tagList.get(0));
            masterTag2.setText(tagList.get(1));
        }else if (tagList.size() ==1){
            masterTag1.setText(tagList.get(0));
            masterTag2.setText("test");
        }else {
            masterTag1.setText("test");
            masterTag2.setText("test");
        }
        //Glide.with(getContext()).load(info.getPicUrl()).error(R.drawable.test).centerCrop().into(masterAvatar);
        //加载圆形图片，调用时不能加centerCrop()否则图片不能正常显示为圆形
        Glide.with(getContext()).load(info.getPicUrl())
                .bitmapTransform(new CirclePictureTransformation(getContext()))
                .crossFade(1000)
                .error(R.drawable.test)
                .into(masterAvatar);
    }
    //从CourseDetialInfoJSON提取teacher信息
    public void setContent(CourseDetialInfoJSON courseInfo){
        List<CourseDetialInfoJSON.Data.Lesson.Teacher> teacherList = courseInfo.data.lesson.teachers;
        if (teacherList.size() != 0){
            CourseDetialInfoJSON.Data.Lesson.Teacher teacher = teacherList.get(0);
            String description;
            //当teacher.description是List<String>时
//            if (0 != teacher.description.size()){
//                description = teacher.description.get(0);//暂时只取teacher.description[]中的第一个元素
//            }else { description = "NULL";}
            //当teacher.description是String时
            description = teacher.description;
            Log.i(Constants.TAG,"name:" +teacher.name +"; description: " + description);
            setContent(new MasterInfo(teacher.name, teacher.teacher_id, teacher.avatar, description, teacher.tags));
        }
        else {
            Toast.makeText(getContext(), "获取达人信息错误", Toast.LENGTH_SHORT).show();
        }
    }
    //从MasterDetailInfoJSON提取teacher信息
    public void setContent(MasterDetailInfoJSON info){
        MasterDetailInfoJSON.Data.TeacherInfo teacherInfo = info.data.teacher_info;
        String description;
//        if (0 != teacherInfo.description.size()){
//            description = teacherInfo.description.get(0);//暂时只取teacher.description[]中的第一个元素
//        }else { description = "NULL";}
        description = teacherInfo.description;
        setContent(new MasterInfo(teacherInfo.name, "", teacherInfo.avatar,description, teacherInfo.tags));
    }
}
