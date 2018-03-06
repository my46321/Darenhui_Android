package com.idarenhui.tool_android.model;

import com.idarenhui.tool_android.R;

/**
 * Created by Administrator on 2017/7/19.
 */

public class OrderInfo {
    private String title;
    private String viewText;
    private int pictureId;

    public OrderInfo() {
        this("testTitle", "testViewContent", R.drawable.test);
    }
    public OrderInfo(String title, String viewText, int pictureId){
        this.title = title;
        this.viewText = viewText;
        this.pictureId = pictureId;;
    }
    public String getTitle() {
        return title;
    }
    public String getViewText() {
        return viewText;
    }
    public int getPictureId() {
        return pictureId;
    }

    public void setContent(String title, String text, int pictureId){
        this.title = title;
        this.viewText = text;
        this.pictureId = pictureId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setViewText(String viewText) {
        this.viewText = viewText;
    }
    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

}
