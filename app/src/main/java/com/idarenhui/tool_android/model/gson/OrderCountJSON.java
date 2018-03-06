package com.idarenhui.tool_android.model.gson;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class OrderCountJSON {
    public String code;
    public Data data;
    public static class Data
    {
        public List<String>  orders_count;
    }
}
