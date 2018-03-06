package com.idarenhui.tool_android.event;

/**
 * Created by chen on 2017/7/28.
 */

public class CampusChoseEvent {
    private String campusName;
    public CampusChoseEvent(String campusName) {
        this.campusName = campusName;
    }
    public String getCampusName() {
        return campusName;
    }
}
