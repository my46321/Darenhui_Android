package com.idarenhui.tool_android.custom;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

/**
 * Created by Administrator on 2017/7/24.
 */
//为了统一弹窗风格而自定义的Dialog类
public class ContactDialog {
    private String tel="18018035537";

    public interface OnButtonClickLisenter{
        void setOnClickOkLisenter();
        void  setOnClickCancleListener();
    }
    public void showDialog(Context context)
    {
        final Context context2=context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请联系客服");
        builder.setMessage("客服联系方式："+tel);
        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("拨打电话", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            String phone_number = tel;
            Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                    + phone_number));
            try {
                context2.startActivity(intent2);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }).show();
    }
    public static void showTipDialog(Context context, String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", null);
        builder.show();
    }
    public static void showDialog(Context context, String msg, final OnButtonClickLisenter lisenter){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle("");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lisenter.setOnClickOkLisenter();
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lisenter.setOnClickCancleListener();
            }
        });
        builder.create().show();
    }
}
