package com.idarenhui.tool_android.model;

/**
 * Created by Administrator on 2017/9/14.
 */

public class RecommendList {
        String lesson_id;
        String picture;
        public String getlesson_id(){return lesson_id;}
        public String getImg_url(){return  picture;}
        public void setLessonid(String lessonid){
            this.lesson_id=lessonid;
        }
        public void setimg_url(String img_url){
            this.picture=img_url;
        }

}
