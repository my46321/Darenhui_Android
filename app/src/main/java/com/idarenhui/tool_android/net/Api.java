package com.idarenhui.tool_android.net;

/**
 * Created by chen on 2017/7/24.
 */

public class Api {
    //获取讲师详情
    public static final String getTeacherDetailInfo = "https://api.idarenhui.com/DRH_Test/v1.0/teacher";
    //获取课程详情
    public static final String getLessonInfo = "https://api.idarenhui.com/DRH_Test/v1.0/lesson";
    //班级信息获取
    public static final String getLessonTeam = "https://api.idarenhui.com/DRH_Test/v1.0/lesson/team";
    //用户体验课程
    public static final String buildUserExperienceLesson = "https://api.idarenhui.com/DRH_Test/v1.0/lesson/experience";
    //修改用户信息
    public  static final  String modifyUserInfo = "https://api.idarenhui.com/DRH_Test/v1.0/user";
    //获取验证码
    public static final String getVerifyCode = "https://api.idarenhui.com/DRH_Test/v1.0/phone/sendcode";
}
