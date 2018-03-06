package com.idarenhui.tool_android.model;

import com.idarenhui.tool_android.model.gson.LessonTeamInfoJSON;

/**
 * Created by chen on 2017/7/26.
 */

public class PurchaseInfo {
    private String duration;
    private String remark;
    private String team_id;
    private String time;
    private String price;
    private String site;

    public PurchaseInfo(String duration, String remark, String team_id, String time, String price, String site) {
        this.duration = duration;
        this.remark = remark;
        this.team_id = team_id;
        this.time = time;
        this.price = price;
        this.site = site;
    }
    public PurchaseInfo(LessonTeamInfoJSON.Data.TeamInfo teamInfo){
        this.duration = teamInfo.duration;
        this.remark = teamInfo.remark;
        this.team_id = teamInfo.team_id;
        this.time = teamInfo.time;
        this.price = teamInfo.price;
        this.site = teamInfo.site;
    }
    public String getDuration() {
        return duration;
    }

    public String getRemark() {
        return remark;
    }

    public String getTeam_id() {
        return team_id;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    public String getSite() {
        return site;
    }
}
