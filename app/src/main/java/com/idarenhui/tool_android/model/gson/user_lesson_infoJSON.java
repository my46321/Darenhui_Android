package com.idarenhui.tool_android.model.gson;

import com.idarenhui.tool_android.model.LessonData;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class user_lesson_infoJSON {
    public String code;
    public Data data;
    public static class Data{
        public List<LessonData> infolist;
//        public static class lesson_info{
//            public String _id;
//            public String title;
//            public String description;
//            public String img;
//        }
    }
}
