package com.idarenhui.tool_android.model.gson;

/**
 * Created by zyc19 on 2017/7/25.
 */

public class UserLoginInfoJSON {
    public int code;
    public Stat data;

    public static class Stat{
        public boolean isRegister;
        public String accessToken;
    }
}
