package com.idarenhui.tool_android.model.gson;

import com.idarenhui.tool_android.model.CommentData;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class CourseCommentJSON {
    public String code;
    public Data data;
    public static class Data{
        public List<CommentData> comments;
    }
}
