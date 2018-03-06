package com.idarenhui.tool_android.ui.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.model.gson.CategoryInfoJson;
import com.idarenhui.tool_android.net.UserCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import com.idarenhui.tool_android.model.ClassifyInfo;
import com.idarenhui.tool_android.custom.MasterView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.inflate;

/**
 * Created by chen on 2017/7/14.
 */

public class CourseClassifyActivity extends Activity {
    @BindView(R.id.master_one)
    MasterView masterOne;
    @BindView(R.id.master_two)
    MasterView masterTwo;
    @BindView(R.id.courseOverView_recycleList)
    RecyclerView courseOverViewRecycleList;
    @BindView(R.id.classify_one_picture)
    ImageView classifyPicture;
    @BindView(R.id.classify_one_text)
    TextView classifyText;
    @BindView(R.id.classify_one_background)
    LinearLayout classifyBackground;

    //分类信息0-4五种
    private int id;

    String responseString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_classify);
        View view =  inflate(this, R.layout.course_classify, null);
        ButterKnife.bind(this);
        onViewClicked(view);
        init();
    }

    private void init() {
        initHead();
        //loadData();
    }

    private void initHead() {
        //接收id的值
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getInt("classify_id");
        List<ClassifyInfo> infoList = new ArrayList<>();
//        switch (id) {
//            case 0:
//                classifyBackground.setBackgroundResource(infoList.get(0).backColorId);
//                classifyPicture.setImageResource(infoList.get(0).pictureId);
//                classifyText.setText(infoList.get(0).text);
//                break;
//            case 1:
//                classifyBackground.setBackgroundResource(infoList.get(1).backColorId);
//                classifyPicture.setImageResource(infoList.get(1).pictureId);
//                classifyText.setText(infoList.get(1).text);
//                break;
//            case 2:
//                classifyBackground.setBackgroundResource(infoList.get(2).backColorId);
//                classifyPicture.setImageResource(infoList.get(2).pictureId);
//                classifyText.setText(infoList.get(2).text);
//                break;
//            case 3:
//                classifyBackground.setBackgroundResource(infoList.get(3).backColorId);
//                classifyPicture.setImageResource(infoList.get(3).pictureId);
//                classifyText.setText(infoList.get(3).text);
//                break;
//            case 4:
//                classifyBackground.setBackgroundResource(infoList.get(4).backColorId);
//                classifyPicture.setImageResource(infoList.get(4).pictureId);
//                classifyText.setText(infoList.get(4).text);
//                break;
//        }
    }

    //点击两个达人后产生的事件
    @OnClick({R.id.master_one, R.id.master_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.master_one:
                startActivity(new Intent(this, MasterInfoActivity.class));
                break;
            case R.id.master_two:
                startActivity(new Intent(this, MasterInfoActivity.class));
                break;
        }
        loadFromNet("5971aad790c4900c53f89919","10","0");
    }

    //从服务器加载首页信息
    public void loadFromNet(String category_id, String pagesize, String offset){
        String  url = "https://api.idarenhui.com/DRH_Test/v1.0/index/CategoryInfo";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("category_id", category_id)
                .addParams("pagesize", pagesize)
                .addParams("offset", offset)
                .build()
                .execute(new UserCallback<CategoryInfoJson>(){
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(CategoryInfoJson response, int id) {
//                        Toast.makeText(getApplicationContext(),"test"+ response.data.categoryInfo.category_title,
//                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
