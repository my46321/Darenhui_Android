package com.idarenhui.tool_android.model;

import com.idarenhui.tool_android.R;


/**
 * Created by chen on 2017/7/21.
 */
public class ClassifyInfo{
    private String title;
    private String picURL;
    private String id;
    private int backBackgourndColor;
    private static int counter = 0;

    public ClassifyInfo(String title, String picURL, String id) {
        this.title = title;
        this.picURL = picURL;
        this.id = id;
        counter++;
        //目前只有五个背景色值
        switch (counter%5) {
            case 1:
                backBackgourndColor = R.color.fragment_classify_1;
                break;
            case 2:
                backBackgourndColor = R.color.fragment_classify_2;
                break;
            case 3:
                backBackgourndColor = R.color.fragment_classify_3;
                break;
            case 4:
                backBackgourndColor = R.color.fragment_classify_4;
                break;
            case 0:
                backBackgourndColor = R.color.fragment_classify_5;
                break;
        }
    }

    public String getPicURL() {
        return picURL;
    }

    public int getBackBackgourndColor() {
        return backBackgourndColor;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
//之前以为是五个给定的分类，现在分类也要从网络加载
//public class ClassifyInfo {
//    public List<Info> infoList;
//    public ClassifyInfo(){
//        infoList = new ArrayList<>(5);
//        infoList.add(new Info(R.color.fragment_classify_1, R.drawable.classify_icon_flower, "生活美学"));
//        infoList.add(new Info(R.color.fragment_classify_2, R.drawable.classify_icon_photo, "摄影"));
//        infoList.add(new Info(R.color.fragment_classify_3, R.drawable.classify_icon_book, "读书"));
//        infoList.add(new Info(R.color.fragment_classify_4, R.drawable.classify_icon_movie, "电影"));
//        infoList.add(new Info(R.color.fragment_classify_5, R.drawable.classify_icon_food, "厨艺"));
//    }
//    public class Info{
//        public int backColorId;
//        public int pictureId;
//        public String text;
//        public Info(int backColorId, int pictureId, String text) {
//            this.backColorId = backColorId;
//            this.pictureId = pictureId;
//            this.text = text;
//        }
//    }
//}
