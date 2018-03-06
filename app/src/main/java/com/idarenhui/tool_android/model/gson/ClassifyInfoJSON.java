package com.idarenhui.tool_android.model.gson;

import java.util.List;

/**
 * Created by chen on 2017/7/21.
 */

//{
//        "data": {
//            "categoryList": [
//                {
//                    "_id": "5971aad790c4900c53f89919",
//                    "title": "声乐器乐",
//                    "img": "http://7xl53f.com1.z0.glb.clouddn.com/5971aad790c4900c53f89918"
//                },
//                {
//                    "_id": "5971b27b90c4900c53f89926",
//                    "title": "化妆美甲",
//                    "img": "http://7xl53f.com1.z0.glb.clouddn.com/5971b27a90c4900c53f89925"
//                }
//            ]
//        },
//        "code": "200"
//}
public class ClassifyInfoJSON {
    public String code;
    public Data data;

    public static class Data {
        public List<CourseData> categoryList;

        public static class CourseData {
            public String _id;
            public String title;
            public String img;
        }
    }
}
