package com.idarenhui.tool_android.model.gson;

import com.idarenhui.tool_android.model.OrderData;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
//
//{
//        "data": {
//        "orders": [
//        {
//        "lesson_title": "男生女生训练营",
//        "order_id": "5971e55076bdfc6b6dec9477",
//        "lesson_img": "http://7xl53f.com1.z0.glb.clouddn.com/596f39ed90c4907ea62e4f73"
//        }
//        ]
//        },
//        "code": "200"
//}
//
public class OrderInfoJSON {
    public String code;
    public Data data;
    public static class Data{
        public List<OrderData> orders;
    }
}
