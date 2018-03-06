package com.idarenhui.tool_android.model.gson;

/**
 * Created by chen on 2017/7/24.
 */
/*
{
    "code": "200",
    "data": {
        "imgarticle_info": {
            "_id": "5975634290c4905246ac743e",
            "_content": "图文展示也<div><img src=\"http://7xl53f.com1.z0.glb.clouddn.com/5975633a90c4905246ac743a\"><br></div>"
        }
    }
}
{
    "data": {
        "imgarticle_info": {
            "_content": "<div style=\"text-align: center;\"><font color=\"#ff0d0d\">课程标题</font></div><div style=\"text-align: center;\"><font color=\"#ff0d0d\"><br></font></div><div style=\"text-align: center;\"><font color=\"#000000\">化妆不是一日之功，需滴水穿石之恒心方可学成。</font></div><div style=\"text-align: center;\"><font color=\"#000000\"><br></font></div><div style=\"text-align: center;\"><font size=\"5\" color=\"#fff019\"><b style=\"background-color: rgb(31, 31, 255);\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 加油 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</b></font></div>",
            "_id": "5971b4eb90c4900c53f8992b"
        }
    },
    "code": "200"
}
 */
public class ImageArticleInfoJSON {
    public String code;
    public Data data;
    public static class Data{
        public ImageArticle imgarticle_info;
        public static class ImageArticle{
            public String _id;
            public String _content;
        }
    }
}
