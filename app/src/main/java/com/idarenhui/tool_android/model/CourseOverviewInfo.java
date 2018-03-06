package com.idarenhui.tool_android.model;

/**
 * Created by chen on 2017/7/12.
 */
//        "title": "新建的测试课",
//         "img": "http://7xl53f.com1.z0.glb.clouddn.com/596f39ed90c4907ea62e4f73",
//         "_id": "5970617690c4907a4d0144af",
//         "description": "这是一次神奇的旅行"
public class CourseOverviewInfo {
    private String title;
    private String viewText;
    private String imgURL;
    private String id;


    //最新的构造函数
    public CourseOverviewInfo(String title, String viewText, String imgURL, String id) {
        this.title = title;
        this.viewText = viewText;
        this.imgURL = imgURL;
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public String getViewText() {
        return viewText;
    }



    public void setTitle(String title) {
        this.title = title;
    }
    public void setViewText(String viewText) {
        this.viewText = viewText;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getId() {
        return id;
    }
}
