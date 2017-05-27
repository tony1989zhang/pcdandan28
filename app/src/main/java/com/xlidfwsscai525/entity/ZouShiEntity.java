package com.xlidfwsscai525.entity;

import com.xlidfwsscai525.http.JRParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
@HttpResponse(parser = JRParser.class)
public class ZouShiEntity {

    public int state;
    public String error;
    public List<ItemsBean> items;

    public static class ItemsBean {
        public String period;
        public String ball;
    }
}
