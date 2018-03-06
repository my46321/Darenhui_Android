package com.idarenhui.tool_android.model.gson;

/**
 * Created by Administrator on 2017/7/28.
 */

public class UserInfoJSON{
    public String code;
    public Data data;
    public static class Data{
        public User_info user_info;
        public static class User_info{
            public String realName;
            public String sex;
            public String grade;
            public String avatar;
            public String college;
            public String identity;
            public int teacher_count;
            public int exp_count;
            public int lesson_count;
        }
    }
}
