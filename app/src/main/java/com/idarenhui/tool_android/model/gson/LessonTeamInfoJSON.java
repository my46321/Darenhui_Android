package com.idarenhui.tool_android.model.gson;

import java.util.List;

/**
 * Created by chen on 2017/7/26.
 */
/*
{
    "code": "200",
    "data": {
        "teams_info": [
            {
                "team_id": "5978393b90c49030321e10c8",
                "remark": "",
                "price": "250",
                "time": "周三",
                "duration": "6次*45分钟",
                "site": "东大"
            }
        ]
    }
}
 */
public class LessonTeamInfoJSON {
    public String code;
    public Data data;
    public static class Data{
        public List<TeamInfo> teams_info;
        public static class TeamInfo{
            public String team_id;
            public String remark;
            public String price;
            public String time;
            public String duration;
            public String site;
        }
    }
}
