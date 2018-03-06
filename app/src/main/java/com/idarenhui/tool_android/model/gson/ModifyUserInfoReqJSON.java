package com.idarenhui.tool_android.model.gson;

/**
 * Created by zyc19 on 2017/7/26.
 */

public class ModifyUserInfoReqJSON {
    public myData user_info;
    public ModifyUserInfoReqJSON(){
        this.user_info = new myData();
    }
    public static class myData{
        public String realName;
        public String sex;
        public String college;
        public String identity;
        public String avatar;
        public String grade;
    }
}
