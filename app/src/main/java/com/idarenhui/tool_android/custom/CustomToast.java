package com.idarenhui.tool_android.custom;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chen on 2017/7/27.
 */

public class CustomToast {
    public static void toastMsgShort(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
