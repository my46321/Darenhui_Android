package com.idarenhui.tool_android.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.CirclePictureTransformation;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.model.gson.OrderDetailJSON;
import com.idarenhui.tool_android.net.UserCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/20.
 */

public class WaitEvaluateActivity extends Activity {
    @BindView(R.id.wait_accept_image1)
    ImageView waitAcceptImage1;
    @BindView(R.id.wait_accept_course1)
    TextView waitAcceptCourse1;
    @BindView(R.id.wait_accept_course2)
    TextView waitAcceptCourse2;
    @BindView(R.id.wait_accept_tag1)
    ImageView waitAcceptTag1;
    @BindView(R.id.wait_accept_tag2)
    ImageView waitAcceptTag2;
    @BindView(R.id.wait_accept_image2)
    ImageView waitAcceptImage2;
    @BindView(R.id.wait_accept_master1)
    TextView waitAcceptMaster1;
    @BindView(R.id.wait_accept_master2)
    TextView waitAcceptMaster2;
    @BindView(R.id.goto_evaluate)
    Button gotoEvaluate;
    @BindView(R.id.wait_accept_tag)
    ImageView waitAcceptTag;
    @BindView(R.id.wait_accept_scroll)
    ScrollView waitAcceptScroll;

    String order_id;
    @BindView(R.id.wait_accept_course3)
    TextView waitAcceptCourse3;
    @BindView(R.id.wait_accept_locus)
    TextView waitAcceptLocus;
    @BindView(R.id.wait_accept_time)
    TextView waitAcceptTime;
    @BindView(R.id.wait_accept_phone)
    TextView waitAcceptPhone;
    @BindView(R.id.service_contact)
    TextView serviceContact;

    ContactDialog dialog;
    @BindView(R.id.exit_order)
    ImageView exitOrder;
    @BindView(R.id.comment_info)
    TextView commentInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_for_evaluate);
        ButterKnife.bind(this);
        order_id = getIntent().getStringExtra("order_id");
        gotoEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = order_id;
                Bundle bundle = new Bundle();
                bundle.putString("order_id", s);
                Intent intent = new Intent(WaitEvaluateActivity.this, EditEvaluation.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });
        //
        serviceContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ContactDialog();
                dialog.showDialog(WaitEvaluateActivity.this);
            }
        });
        exitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadFromNet(order_id);
        overridePendingTransition(R.transition.slide_right_in, R.transition.slide_right_out);
    }

     public void loadFromNet(String order_id) {
        OkHttpUtils
                .get()//
                .url("https://api.idarenhui.com/DRH_Test/v1.0/order")//
                .addHeader("Access-Token", UserInfo.token)
                .addParams("order_id", order_id)//
                .build()//
                .execute(new UserCallback<OrderDetailJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("test", e.getMessage());
                    }

                    @Override
                    public void onResponse(OrderDetailJSON response, int id) {
                        Glide.with(WaitEvaluateActivity.this).load(response.data.order_info.avatar).bitmapTransform(new CirclePictureTransformation(WaitEvaluateActivity.this)).error(R.drawable.test).into(waitAcceptImage2);
                        Glide.with(WaitEvaluateActivity.this).load(response.data.order_info.lesson_info.img).error(R.drawable.test).centerCrop().into(waitAcceptImage1);
                        waitAcceptCourse1.setText(response.data.order_info.lesson_info.title);
                        waitAcceptCourse2.setText(response.data.order_info.team_info.duration);
                        waitAcceptCourse3.setText(response.data.order_info.team_info.price);
                        //waitPayDuration.setText(response.data.order_info.team_info.duration);
                        waitAcceptTime.setText(response.data.order_info.team_info.time);
                        waitAcceptLocus.setText(response.data.order_info.team_info.site);
                        waitAcceptMaster2.setText(response.data.order_info.college);
                        waitAcceptMaster1.setText(response.data.order_info.name);
                        waitAcceptPhone.setText(response.data.order_info.mobile);
                        commentInfo.setText(response.data.order_info.comment);
                        //waitPayPrice.setText(response.data.order_info.team_info.price);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
