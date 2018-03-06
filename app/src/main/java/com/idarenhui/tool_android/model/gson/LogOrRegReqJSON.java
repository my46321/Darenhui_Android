package com.idarenhui.tool_android.model.gson;

/**
 * Created by zyc19 on 2017/7/26.
 */

public class LogOrRegReqJSON {
    public reqData userInfo;
    public LogOrRegReqJSON(){
        this.userInfo = new reqData();
    }
    public static class reqData{
        public String mobile;
        public String code_str;
        }

}
