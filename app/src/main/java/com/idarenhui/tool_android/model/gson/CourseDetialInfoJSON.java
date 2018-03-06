package com.idarenhui.tool_android.model.gson;

import java.util.List;

/**
 * Created by chen on 2017/7/24.
 */

//获取课程详情
public class CourseDetialInfoJSON {
    public String code;
    public Data data;
    public static class Data{
        public Lesson lesson;
        public static class Lesson{
            public String _id;
            public String img;
            public String title;
            public String organization;
            public String note;
            public List<Teacher> teachers;
            public static class Teacher{
                public String name;
                public String avatar;
                public List<String> tags;
                public String teacher_id;
                //public List<String> description;
                public String description;
            }
            public String category;
            public String description;
            public String cate_img;
            public boolean isCollection;
        }
    }
}

//首页课程详情--管理员获取到的信息
/*
{
    "data": {
        "lesson": {
            "teams": [],
            "category": "5971aad790c4900c53f89919",
            "organization": "",
            "_id": "5971ad5f90c4900c53f89922",
            "img": "http://7xl53f.com1.z0.glb.clouddn.com/596f39ed90c4907ea62e4f73",
            "title": "课程名",
            "state": "0",
            "teachers": [
                {
                    "state": 1,
                    "tags": [
                        "逗",
                        " 有趣"
                    ],
                    "categories": [],
                    "avatar": "http://7xl53f.com1.z0.glb.clouddn.com/59759cb790c4905246e380b9",
                    "teacher_id": "5971ac3d90c4900c53f8991c",
                    "name": "大雄",
                    "description": "吉他教学老师"
                }
            ],
            "create_time": 1500622175.5108247,
            "description": "测试课",
            "cate_img": "http://7xl53f.com1.z0.glb.clouddn.com/5971aad790c4900c53f89918"
        }
    },
    "code": "200"
}
 */
//首页课程详情--管理员获取到的信息
//public class CourseDetialInfoJSON {
//    public String code;
//    public Data data;
//    public static class Data{
//        public Lesson lesson;
//        public static class Lesson{
//            public List<Team> teams;
//            public static class Team{};
//            public String category;
//            public String organization;
//            public String _id;
//            public String img;
//            public String title;
//            public String state;
//            public List<Teacher> teachers;
//            public static class Teacher{
//                public String state;
//                public List<String> tags;
//                public List<Categorie> categories;
//                public static class Categorie{};
//                public String avatar;
//                public String teacher_id;
//                public String name;
//                public String description;
//            }
//            public String create_time;
//            public String description;
//            public String cate_img;
//        }
//    }
//}
