package com.idarenhui.tool_android.ui.order;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.CirclePictureTransformation;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.model.gson.OrderDetailJSON;
import com.idarenhui.tool_android.net.UserCallback;
import com.pingplusplus.android.Pingpp;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

//

/**
 * Created by Administrator on 2017/7/20.
 */

public class WaitPayActivity extends Activity {

    public static final String PAY_URL="https://api.idarenhui.com/DRH_Test/v1.0/order/ping/alipay";
    public static final String PAY_URL2="https://api.idarenhui.com/DRH_Test/v1.0/order/ping/wx";
    public static final String FEEDBACK_URL="https://api.idarenhui.com/DRH_Test/v1.0/order/ping/alipay/back";
    @BindView(R.id.wait_accept_image1)
    ImageView waitAcceptImage1;
    @BindView(R.id.wait_accept_course1)
    TextView waitAcceptCourse1;
    @BindView(R.id.wait_accept_course2)
    TextView waitAcceptCourse2;
    @BindView(R.id.wait_pay_tag1)
    TextView waitPayTag1;
    @BindView(R.id.wait_pay_cost)
    TextView waitPayCost;
    @BindView(R.id.wait_pay_tag2)
    TextView waitPayTag2;
    @BindView(R.id.wait_pay_duration)
    TextView waitPayDuration;
    @BindView(R.id.wait_pay_tag3)
    TextView waitPayTag3;
    @BindView(R.id.wait_pay_time)
    TextView waitPayTime;
    @BindView(R.id.wait_pay_tag4)
    TextView waitPayTag4;
    @BindView(R.id.wiat_pay_locus)
    TextView wiatPayLocus;
    @BindView(R.id.wait_pay_master1)
    TextView waitPayMaster1;
    @BindView(R.id.temp_view1)
    View tempView1;
    @BindView(R.id.wait_pay_master_image)
    ImageView waitPayMasterImage;
    @BindView(R.id.wait_pay_master_name)
    TextView waitPayMasterName;
    @BindView(R.id.wait_pay_phone)
    TextView waitPayPhone;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.wait_accept_tag)
    ImageView waitAcceptTag;
    @BindView(R.id.wait_pay_scroll)
    ScrollView waitPayScroll;
    @BindView(R.id.wait_pay_price)
    TextView waitPayPrice;

    String order_id;
    @BindView(R.id.service_contact)
    TextView serviceContact;

    ContactDialog dialog;
    @BindView(R.id.exit_order)
    ImageView exitOrder;
    @BindView(R.id.wait_pay_comment)
    TextView waitPayComment;
    @BindView(R.id.pay_button)
    Button payButton;

    String data = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_for_pay);
        ButterKnife.bind(this);
        order_id = getIntent().getStringExtra("order_id");
        loadFromNet(order_id,UserInfo.token);
        serviceContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ContactDialog();
                dialog.showDialog(WaitPayActivity.this);
//                AlertDialog.Builder builder = new AlertDialog.Builder(WaitPayActivity.this);
//                builder.setTitle("请联系客服");
//                builder.setMessage("客服联系方式：1986402183");
//                builder.setPositiveButton("确定", null);
//                builder.show();
            }
        });
        exitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new PaymentTask().execute(order_id);
                showDialog();
            }
        });
        overridePendingTransition(R.transition.slide_right_in, R.transition.slide_right_out);
    }

    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        final Dialog dialog = new Dialog(this, R.style.Theme_Light_Dialog);
        dialog.setContentView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 为dialog设置动画
        window.setWindowAnimations(R.style.main_menu_animstyle);

        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wl = window.getAttributes();

        wl.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(wl);
//        //Dialog的初始位置
//        wl.x = 0;
//        wl.y = getWindowManager().getDefaultDisplay().getHeight();
//        // 设置Dialog应该占的空间参数
//        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;

        // 把参数设置到Dialog里
        dialog.onWindowAttributesChanged(wl);
        // 设置点击空白处消失
        dialog.setCanceledOnTouchOutside(true);
        // 展示Dialog
        dialog.show();
        // 取得Dialog里的按钮控件添加监听器
        Button button = (Button) view.findViewById(R.id.cancle_btn);
        LinearLayout alipayButton=(LinearLayout) view.findViewById(R.id.alipay_btn);
        LinearLayout wxButton=(LinearLayout) view.findViewById(R.id.wxpay_btn);
        alipayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PaymentTask().execute(order_id);
            }
        });
        wxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("testPay","click");
                new PaymentTask1().execute(order_id);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
    }

    //get初始化界面
    public void loadFromNet(String order_id,String token) {
        OkHttpUtils
                .get()//
                .url("https://api.idarenhui.com/DRH_Test/v1.0/order")//
                .addHeader("Access-Token", token)
                .addParams("order_id", order_id)//
                .build()//
                .execute(new UserCallback<OrderDetailJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("test", e.getMessage());
                    }

                    @Override
                    public void onResponse(OrderDetailJSON response, int id) {
                        Glide.with(WaitPayActivity.this).load(response.data.order_info.avatar).bitmapTransform(new CirclePictureTransformation(WaitPayActivity.this)).error(R.drawable.test).into(waitPayMasterImage);
                        Glide.with(WaitPayActivity.this).load(response.data.order_info.lesson_info.img).error(R.drawable.test).centerCrop().into(waitAcceptImage1);
                        waitAcceptCourse1.setText(response.data.order_info.lesson_info.title);
                        waitAcceptCourse2.setText(response.data.order_info.lesson_info.description);
                        waitPayCost.setText(response.data.order_info.team_info.price);
                        waitPayDuration.setText(response.data.order_info.team_info.duration);
                        waitPayTime.setText(response.data.order_info.team_info.time);
                        wiatPayLocus.setText(response.data.order_info.team_info.site);
                        waitPayMaster1.setText(response.data.order_info.college);
                        waitPayMasterName.setText(response.data.order_info.name);
                        waitPayPhone.setText(response.data.order_info.mobile);
                        waitPayComment.setText(response.data.order_info.comment);
                        waitPayPrice.setText(response.data.order_info.team_info.price);
                    }
                });
    }

    class PaymentTask1 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //按键点击之后的禁用，防止重复点击
        }

        @Override
        protected String doInBackground(String... id) {
            String order_id = id[0];
            try {
                JSONObject object = new JSONObject();
                object.put("order_id", order_id);
                String json = object.toString();
                //向Your Ping++ Server SDK请求数据
                //data = postJson(CHARGE_URL, json);
                Log.i("testPay","askWxPay");
                OkHttpUtils
                        .postString()
                        .url(PAY_URL2)
                        .addHeader("Access-Token", UserInfo.token)
                        .content (json)
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new StringCallback(){
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                e.printStackTrace();
                                Log.i("test",e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                data=response;
                                if(null == data){
                                    //showMsg("请求出错", "请检查URL", "URL无法获取charge");
                                    return;
                                }
                                Log.i("charge", data);

                                int start=data.indexOf("data\": {");
                                int size=data.length()-1;
                                String newData=data.substring(start+7,size);
                                Log.i("chargeNew",newData);

                                Pingpp.createPayment(WaitPayActivity.this, newData);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Log.i("testsandk",data);
            return data;
        }

        /**
         * 获得服务端的charge，调用ping++ sdk。
         */
        @Override
        protected void onPostExecute(String data) {
//            if(null == data){
//                //showMsg("请求出错", "请检查URL", "URL无法获取charge");
//                return;
//            }
//            Log.i("charge", data);
//            //除QQ钱包外，其他渠道调起支付方式：
//            //参数一：Activity  当前调起支付的Activity
//            //参数二：data  获取到的charge或order的JSON字符串
//            Pingpp.createPayment(WaitPayActivity.this, data);
        }
    }

    class PaymentTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //按键点击之后的禁用，防止重复点击
        }

        @Override
        protected String doInBackground(String... id) {
            String order_id = id[0];
            try {
                JSONObject object = new JSONObject();
                object.put("order_id", order_id);
                String json = object.toString();
                //向Your Ping++ Server SDK请求数据
                //data = postJson(CHARGE_URL, json);
                OkHttpUtils
                        .postString()
                        .url(PAY_URL)
                        .addHeader("Access-Token", UserInfo.token)
                        .content (json)
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new StringCallback(){
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                e.printStackTrace();
                                Log.i("test",e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                data=response;
                                if(null == data){
                                    //showMsg("请求出错", "请检查URL", "URL无法获取charge");
                                    return;
                                }
                                Log.i("charge", data);

                                int start=data.indexOf("data\": {");
                                int size=data.length()-1;
                                String newData=data.substring(start+7,size);
                                Log.i("chargeNew",newData);

                                Pingpp.createPayment(WaitPayActivity.this, newData);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
           // Log.i("testsandk",data);
            return data;
        }

        /**
         * 获得服务端的charge，调用ping++ sdk。
         */
        @Override
        protected void onPostExecute(String data) {
//            if(null == data){
//                //showMsg("请求出错", "请检查URL", "URL无法获取charge");
//                return;
//            }
//            Log.i("charge", data);
//            //除QQ钱包外，其他渠道调起支付方式：
//            //参数一：Activity  当前调起支付的Activity
//            //参数二：data  获取到的charge或order的JSON字符串
//            Pingpp.createPayment(WaitPayActivity.this, data);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
//        payButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new PaymentTask().execute(order_id);
//            }
//        });
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                //showMsg(result, errorMsg, extraMsg);

                try {
                    JSONObject object = new JSONObject();
                    object.put("order_id", order_id);
                    object.put("result",result);
//                    if(result=="success")
//                        object.put("result","success");
//                    else
//                        object.put("result","fail");
                    String json = object.toString();
                    //向Your Ping++ Server SDK请求数据
                    //data = postJson(CHARGE_URL, json);
                    OkHttpUtils
                            .postString()
                            .url(FEEDBACK_URL)
                            .addHeader("Access-Token",UserInfo.token)
                            .content(json)
                            .mediaType(MediaType.parse("application/json; charset=utf-8"))
                            .build()
                            .execute(new StringCallback(){
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    e.printStackTrace();
                                    Log.i("test_feedback_fail",e.getMessage());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.i("feedback_succeed",response);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        }
    }

}
