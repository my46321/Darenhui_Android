package com.idarenhui.tool_android.model.gson;

import com.idarenhui.tool_android.model.RecommendList;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */

public class recommendData {
    public String code;
    public Data data;
    public static class Data{
        public List<RecommendList> recommend;
        public List<RecommendList> getrecommend() {
            return recommend;
        }

        public void setrecommendList(List<RecommendList> recommendList) {
            this.recommend = recommendList;
        }

    }
    public String getCode() {
        return code;
    }

    public Data getDate() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(Data data){
        this.data = data;
    }

}
