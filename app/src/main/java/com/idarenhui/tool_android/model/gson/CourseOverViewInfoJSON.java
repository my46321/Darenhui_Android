package com.idarenhui.tool_android.model.gson;

import java.util.List;

/**
 * Created by chen on 2017/7/19.
 */
//Gson解析时对应的bean类
/*
1、内部嵌套的类必须是static的，要不然解析会出错；
2、类里面的属性名必须跟Json字段里面的Key是一模一样的；
3、内部嵌套的用[]括起来的部分是一个List，所以定义为 public List<B> b，而只用{}嵌套的就定义为 public C c，
*/
/*
{
        "code": "200",
        "data": {
            "lessonList": [
                {
                    "_id": "596829a92e6255194883a148",
                    "description    ": "茶道简述",
                    "img": "img_url",
                    "title": "茶道"
                }
            ]
        }
}
*/

public class CourseOverViewInfoJSON {
    String code;
    Data data;
    public static class Data{
        List<CourseData> lessonList;

        public List<CourseData> getLessonList() {
            return lessonList;
        }

        public void setLessonList(List<CourseData> lessonList) {
            this.lessonList = lessonList;
        }
        public static class CourseData{
            String _id;
            String description;
            String img;
            String title;

            public String get_id() {
                return _id;
            }

            public String getDescription() {
                return description;
            }

            public String getImg() {
                return img;
            }

            public String getTitle() {
                return title;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
    public String getCode() { return code; }

    public Data getDate() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(Data date) {
        this.data  = date;
    }
}
