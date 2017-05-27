package com.xlidfwsscai525.entity;

import com.xlidfwsscai525.http.JRParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
@HttpResponse(parser = JRParser.class)
public class NewsEntity {

    public int status;
    public Object url;
    public List<MsgBean> msg;

    public static class MsgBean {
        public String id;
        public String catid;
        public String title;
        public String summary;
        public String logofile;
        public String url;
        public String publishdate;
    }
}
