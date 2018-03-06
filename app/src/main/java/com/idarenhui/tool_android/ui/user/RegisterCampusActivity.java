package com.idarenhui.tool_android.ui.user;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.event.CampusChoseEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by Administrator on 2017/7/28.
 */

public class RegisterCampusActivity extends Activity {
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.close_icon)
    ImageView closeIcon;
    private String campus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        campus = "";
        setContentView(R.layout.fragment_register_campus);
        ButterKnife.bind(this);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campus.isEmpty())
                    ContactDialog.showTipDialog(RegisterCampusActivity.this, "请选择学校");
                else {
                    EventBus.getDefault().post(new CampusChoseEvent(campus));
                    finish();
                }
            }
        });
        overridePendingTransition(R.transition.slide_right_in, R.transition.slide_right_out);
    }

    @OnFocusChange({R.id.JiuLongHu_campus, R.id.SiPaiLou_campus, R.id.DingJiaQiao_campus,
            R.id.JiangJunLu_campus, R.id.MingGuGong_campus,
            R.id.JiangNing_campus, R.id.GuLou_campus})
    public void selectCampus(TextView current) {
        if (current.hasFocus()) {
            switch (current.getId()) {
                case R.id.JiuLongHu_campus:
                case R.id.SiPaiLou_campus:
                case R.id.DingJiaQiao_campus:
                    campus = "东南大学";
                    break;
                case R.id.JiangJunLu_campus:
                case R.id.MingGuGong_campus:
                    campus = "南京航空航天大学";
                    break;
                case R.id.JiangNing_campus:
                case R.id.GuLou_campus:
                    campus = "河海大学";
                    break;
            }
            campus += "(" + current.getText().toString() + ")";
        }
    }

    @OnClick({R.id.JiuLongHu_campus, R.id.SiPaiLou_campus, R.id.DingJiaQiao_campus,
            R.id.JiangJunLu_campus, R.id.MingGuGong_campus,
            R.id.JiangNing_campus, R.id.GuLou_campus})
    public void clickCampus(TextView current) {
        if (current.hasFocus()) {
            switch (current.getId()) {
                case R.id.JiuLongHu_campus:
                case R.id.SiPaiLou_campus:
                case R.id.DingJiaQiao_campus:
                    campus = "东南大学";
                    break;
                case R.id.JiangJunLu_campus:
                case R.id.MingGuGong_campus:
                    campus = "南京航空航天大学";
                    break;
                case R.id.JiangNing_campus:
                case R.id.GuLou_campus:
                    campus = "河海大学";
                    break;
            }
            campus += "(" + current.getText().toString() + ")";

        }
    }

}
