package com.idarenhui.tool_android.model.api;

/**
 * Created by Administrator on 2017/7/26.
 */

public class GenerateOrderPostInfo {
    public String lesson_id;
    public String team_id;
    public String name;
    public String mobile;
    public String comment;
    public  String price;
    public GenerateOrderPostInfo(String lesson_id,String team_id,String name,String mobile,String comment,String price)
    {
        this.lesson_id=lesson_id;
        this.team_id=team_id;
        this.name=name;
        this.mobile=mobile;
        this.comment=comment;
        this.price=price;
    }
}
