package com.idarenhui.tool_android.net;

import com.google.gson.Gson;
import com.idarenhui.tool_android.model.OrderInfo;
import com.idarenhui.tool_android.model.gson.OrderInfoJSON;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/24.
 */

public abstract class OrderInfoJSONCallback extends Callback<OrderInfoJSON>
{
    @Override
    public OrderInfoJSON parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        OrderInfoJSON orderJ = new Gson().fromJson(string, OrderInfoJSON.class);
        return orderJ;
    }
}
