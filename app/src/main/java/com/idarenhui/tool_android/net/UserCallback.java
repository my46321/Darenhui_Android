package com.idarenhui.tool_android.net;

import com.dreamspace.util.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idarenhui.tool_android.model.gson.ClassifyInfoJSON;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by chen on 2017/7/24.
 */

public abstract class UserCallback<T> extends Callback<T>
{
    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        //进行json解析
        String string = response.body().string();
        Logger.json(string);
        //获取T.class
        Class<T> TClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        Gson gson = new Gson();
//        Type type = new TypeToken<T>(){}.getType();
//        T data = gson.fromJson(string, type);
        T data = new Gson().fromJson(string, TClass);
        return data;
    }

    @Override
    public boolean validateReponse(Response response, int id) {
        return true;
    }
}
