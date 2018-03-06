package com.idarenhui.tool_android.net;

import android.util.Log;
import android.widget.Toast;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by chen on 2017/7/19.
 */

public class MyStringCallback extends StringCallback {
    private String TAG = "Test";
    @Override
    public void onBefore(Request request, int id)
    {
        Log.i(TAG, "loading...");
    }

    @Override
    public void onAfter(int id)
    {
        Log.i(TAG, "Sample-okHttp");
    }

    @Override
    public void onError(Call call, Exception e, int id)
    {
        e.printStackTrace();
        Log.i(TAG, "onError:" + e.getMessage());
    }

    @Override
    public void onResponse(String response, int id)
    {
        Log.i(TAG, "onResponse：complete");
//        Toast.makeText(getActivity(), "onResponse"+response, Toast.LENGTH_LONG).show();
        switch (id)
        {
            case 100:
//                Toast.makeText(getActivity(), "http", Toast.LENGTH_SHORT).show();
                break;
            case 101:
//                Toast.makeText(getActivity(), "https", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void inProgress(float progress, long total, int id)
    {
        //可以设置一个获取数据时的进度条
        Log.i(TAG, "inProgress:" + progress);
        //mProgressBar.setProgress((int) (100 * progress));
    }
}
