package com.idarenhui.tool_android.model.gson;

import com.idarenhui.tool_android.model.CategoryData;
import com.idarenhui.tool_android.model.lesson_information;
import com.idarenhui.tool_android.model.teacher_information;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
//
//{
//        "data": {
//        "teacherCount": 0,
//        "lessonCount": 0,
//        "lessonList": [],
//        "teacherList": [],
//        "CategoryInfo": {
//        "category_id": "5971aad790c4900c53f89919",
//        "category_title": "声乐器乐"
//        }
//        },
//        "code": "200"
//}

public class CategoryInfoJson {
    public String code;
    public Data data;
    public static class Data{
        public int teacherCount;
        public int lessonCount;
        public List<lesson_information> lessonList;
        public List<teacher_information> teacherList;
        public CategoryData categoryData;
    }

}
