package com.xlidfwsscai525.entity;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class Pk10Entity {
    public String issue;
    public String nums;
    public String time;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Pk10Entity{" +
                "issue='" + issue + '\'' +
                ", nums='" + nums + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
