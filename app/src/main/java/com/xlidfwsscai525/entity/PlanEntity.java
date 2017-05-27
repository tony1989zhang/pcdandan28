package com.xlidfwsscai525.entity;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class PlanEntity {
    public int qs;
    public int rate;
    public int bqtrate;
    public int sumCost;
    public int win;

    @Override
    public String toString() {
        return "PlanEntity{" +
                "qs=" + qs +
                ", rate=" + rate +
                ", bqtrate=" + bqtrate +
                ", sumCost=" + sumCost +
                ", win=" + win +
                '}';
    }
}
