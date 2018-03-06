package com.idarenhui.tool_android.model.gson;

import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by chen on 2017/7/25.
 */
/*
{
    "code": "200",
    "data": {
        "teacher_info": {
            "name": "小明",
            "sex": "男",
            "mobile": "111",
            "tags": [
                "111"
            ],
            "description": [
                "description"
            ],
            "introduction": "111",
            "cover": "http://7xl53f.com1.z0.glb.clouddn.com/596f3a1b90c4907ea62e4f74",
            "lessons": [
                {
                    "lesson_id": "5975634290c4905246ac743c",
                    "title": "漫画课",
                    "img": "http://7xl53f.com1.z0.glb.clouddn.com/5975634290c4905246ac743b",
                    "organization": "",
                    "description": "【通知】\n1、不许迟到"
                }
            ],
            "avatar": "http://7xl53f.com1.z0.glb.clouddn.com/596f39ed90c4907ea62e4f73"
        }
    }
}
 */
public class MasterDetailInfoJSON {
    public String code;
    public Data data;
    public static class Data{
        public TeacherInfo teacher_info;
        public static class TeacherInfo{
            public String name;
            public String sex;
            public String mobile;
            public List<String> tags;
            //public List<String> description;
            public String description;
            public String introduction;
            public String cover;
            public boolean isCollection;
            public List<Lesson> lessons;
            public static class Lesson{
                public String lesson_id;
                public String title;
                public String img;
                public String organization;
                public String description;
            }
            public String avatar;
        }
    }
}
