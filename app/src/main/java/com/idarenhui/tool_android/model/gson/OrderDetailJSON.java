package com.idarenhui.tool_android.model.gson;

import com.idarenhui.tool_android.model.OrderInfo;

/**
 * Created by Administrator on 2017/7/24.
 */

//{
//        "data": {
//        "order_info": {
//        "team_info": {
//        "time": "2017年7月23日",
//        "site": "东大九龙湖校区 健身房",
//        "price": "199",
//        "duration": ""
//        },
//        "college": "seu",
//        "mobile": "15051862878",
//        "name": "caochangwei",
//        "lesson_info": {
//        "lesson_id": "5971ad2590c4900c53f8991e",
//        "img": "http://7xl53f.com1.z0.glb.clouddn.com/596f39ed90c4907ea62e4f73",
//        "description": "欢迎想减脂增肌人士参与，期待爱健身的你的到来",
//        "title": "男生女生训练营"
//        }
//        }
//        },
//        "code": "200"
//        }
public class OrderDetailJSON {
    public String code;
    public Data data;
    public static class Data{
        public OrderInfo order_info;
        public static class OrderInfo{
            public String college;
            public String mobile;
            public String name;
            public String avatar;
            public String comment;
            public LessonInfo lesson_info;
            public TeamInfo team_info;
            public static class LessonInfo{
                public String lesson_id;
                public String img;
                public String title;
                public String description;
                public String note;
            }
            public static class TeamInfo{
                public String time;
                public String site;
                public String price;
                public String duration;
            }
        }
    }
}
