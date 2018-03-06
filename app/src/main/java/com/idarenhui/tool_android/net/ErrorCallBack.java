package com.idarenhui.tool_android.net;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chen on 2017/7/25.
 */
//网络访问时，出现错误时onError()中使用
public class ErrorCallBack {
    private Context context;
    private ErrorProcess errorProcess;
    public interface ErrorProcess{
        void process();
    }
    public ErrorCallBack(Context context,String errorMsg ,ErrorProcess process){
        this.context = context;
        this.errorProcess = process;
        showForUser(errorMsg);
        errorProcess.process();
    }
    public void showForUser(String error){
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }
}
