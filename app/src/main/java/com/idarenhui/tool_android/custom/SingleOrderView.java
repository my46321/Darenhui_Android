package com.idarenhui.tool_android.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idarenhui.tool_android.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/21.
 */

public class SingleOrderView extends RelativeLayout {
    @BindView(R.id.singleorder_image1)
    ImageView singleorderImage1;
    @BindView(R.id.singleorder_text1)
    TextView singleorderText1;
    @BindView(R.id.singleorder_text2)
    TextView singleorderText2;
    @BindView(R.id.singleorder_button)
    Button singleorderButton;

    public SingleOrderView(Context context) {
        super(context);
        init();
    }

    public SingleOrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SingleOrderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        inflate(getContext(), R.layout.single_order, this);
        init();
    }
    void setRed()
    {
        singleorderText2.setTextColor(getResources().getColor(R.color.colorRed));
    }
    void setOrange()
    {
        singleorderText2.setTextColor(getResources().getColor(R.color.colorOrange));
    }
    void setGreen()
    {
        singleorderText2.setTextColor(getResources().getColor(R.color.colorGreen));
    }
    void setBlack()
    {
        singleorderText2.setTextColor(getResources().getColor(R.color.dynamic_tab_text_select));
    }
    void setGone()
    {
        singleorderButton.setVisibility(View.GONE);
    }
    void setVisble()
    {
        singleorderButton.setVisibility(View.VISIBLE);
    }
//    @OnClick(R.id.singleorder_button)
//    public void onViewClicked() {
//    }
}
