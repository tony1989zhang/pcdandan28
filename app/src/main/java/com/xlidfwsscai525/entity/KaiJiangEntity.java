package com.xlidfwsscai525.entity;

import com.xlidfwsscai525.http.JRParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
@HttpResponse(parser = JRParser.class)
public class KaiJiangEntity {

    public int state;
    public ItemsBean items;
    public String error;

    public static class ItemsBean {
        public long time;
        public CurrentBean current;
        public NextBean next;
        public boolean state;

        public static class CurrentBean {
            public String period;
            public String date;
            public String time;
            public String result;
        }

        public static class NextBean {
            public String period;
            public String date;
            public String time;
            public int interval;
            public int delayinterval;
        }
    }
}
