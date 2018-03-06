package com.idarenhui.tool_android.ui.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.UserInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/7/20.
 */

public class EditEvaluation extends Activity {

    public static final String COMMENT_URL="https://api.idarenhui.com/DRH_Test/v1.0/order/comment";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @BindView(R.id.evaluate_text1)
    TextView evaluateText1;
    @BindView(R.id.evaluate_text2)
    TextView evaluateText2;
    @BindView(R.id.evaluate_text3)
    TextView evaluateText3;
    @BindView(R.id.evaluate_text4)
    TextView evaluateText4;
    @BindView(R.id.edit_evaluation)
    EditText editEvaluation;
    @BindView(R.id.exit_edit_evaluation)
    ImageView exitEditEvaluation;

    String remark_choices = "非常满意";
    String remark;
    String order_id;

    Unbinder unbinder;

    @BindView(R.id.comment_submit)
    Button commentSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_evaluation);
        unbinder = ButterKnife.bind(this);
        exitEditEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        evaluateText1.setSelected(true);
        commentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order_id=getIntent().getStringExtra("order_id");
                remark=editEvaluation.getText().toString();
                try {
                    JSONObject object = new JSONObject();
                    object.put("order_id",order_id);
                    object.put("remark", remark);
                    object.put("remark_choices",remark_choices);
                    String json = object.toString();
                    OkHttpUtils
                            .put()//also can use delete() ,head() , patch()
                            .url(COMMENT_URL)
                            .addHeader("Access-Token", UserInfo.token)
                            .requestBody(RequestBody.create(JSON, json))//
                            .build()//
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    e.printStackTrace();
                                    Log.i("comment_fail", e.getMessage());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.i("comment_success", response);
                                    finish();
                                }
                            });
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        overridePendingTransition(R.transition.slide_right_in, R.transition.slide_right_out);
    }

    @OnClick({R.id.evaluate_text1, R.id.evaluate_text2, R.id.evaluate_text3, R.id.evaluate_text4})
    public void onViewClicked(View view) {
        evaluateText1.setSelected(false);
        evaluateText2.setSelected(false);
        evaluateText3.setSelected(false);
        evaluateText4.setSelected(false);
        switch (view.getId()) {
            case R.id.evaluate_text1:
                remark_choices = "非常满意";
                evaluateText1.setSelected(true);
                break;
            case R.id.evaluate_text2:
                remark_choices = "基本满意";
                evaluateText2.setSelected(true);
                break;
            case R.id.evaluate_text3:
                remark_choices = "不满意";
                evaluateText3.setSelected(true);
                break;
            case R.id.evaluate_text4:
                remark_choices = "差评!";
                evaluateText4.setSelected(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
