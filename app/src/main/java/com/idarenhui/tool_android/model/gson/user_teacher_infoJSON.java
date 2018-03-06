package com.idarenhui.tool_android.model.gson;

import com.idarenhui.tool_android.model.TeacherData;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class user_teacher_infoJSON {
    public String code;
    public Data data;
    public static class Data{
        public List<TeacherData> infolist;
//        public static class teacher_info{
//            public String teacher_id;
//            public String name;
//            public String description;
//            public List<String> tags;
//            public String avatar;
//        }
    }
}
