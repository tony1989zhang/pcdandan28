package com.xlidfwsscai525.entity;

import com.xlidfwsscai525.http.JRParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18 0018.
 */
@HttpResponse(parser = JRParser.class)
public class TwofaceanalysisEntity
{
    public String error;
    public List<List<String>> itemArray;

    @Override
    public String toString() {
        return "TwofaceanalysisEntity{" +
                "error='" + error + '\'' +
                ", itemArray=" + itemArray +
                '}';
    }
}
