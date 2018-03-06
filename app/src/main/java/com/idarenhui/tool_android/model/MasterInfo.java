package com.idarenhui.tool_android.model;

import java.util.List;

/**
 * Created by chen on 2017/7/19.
 */
/*
{
                    "teacher_id": "5971abba90c4900c53f8991b",
                    "tags": [
                        "111"
                    ],
                    "avatar": "http://7xl53f.com1.z0.glb.clouddn.com/596f39ed90c4907ea62e4f73",
                    "description": [
                        "description"
                    ],
                    "name": "小明"
                }
 */
public class MasterInfo {
    private String name;
    private String id;
    private String picUrl;
    private String description;
    private List<String> tags;

    public MasterInfo(String name, String id, String picUrl, String description, List<String> tags) {
        this.name = name;
        this.id = id;
        this.picUrl = picUrl;
        this.description = description;
        this.tags = tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }
}
