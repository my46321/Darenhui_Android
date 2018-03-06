package com.idarenhui.tool_android.model.gson;

import com.idarenhui.tool_android.model.ExperienceData;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class user_experience_infoJSON {
    public String code;
    public Data data;
    public static class Data{
        public List<ExperienceData> infolist;
//        public static class experience_info{
//            public String _id;
//            public String title;
//            public String lesson_id;
//            public String img;
//            public String state;
//        }
    }
}
