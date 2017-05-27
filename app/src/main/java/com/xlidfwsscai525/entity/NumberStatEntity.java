package com.xlidfwsscai525.entity;

import com.xlidfwsscai525.http.JRParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27 0027.
 */
@HttpResponse(parser = JRParser.class)
public class NumberStatEntity {

    public int state;
    public String error;
    public List<ItemsBean> items;

    public static class ItemsBean {
        public String date;
        public String data;
    }
}
