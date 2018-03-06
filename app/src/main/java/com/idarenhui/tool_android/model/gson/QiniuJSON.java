package com.idarenhui.tool_android.model.gson;

/**
 * Created by Administrator on 2017/7/28.
 */

public class QiniuJSON {
    public String code;
    public Data data;
    public static class Data{
        public String key;
        public String token;
        public String domain;
    }
}
