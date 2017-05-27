package com.xlidfwsscai525.entity;


import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
public class Pk10EntityLists {
    public ArrayList<Pk10Entity> pk10EntityArrayList;

    @Override
    public String toString() {
        return "Pk10EntityLists{" +
                "pk10EntityArrayList=" + pk10EntityArrayList +
                '}';
    }

    public ArrayList<Pk10Entity> getPk10EntityArrayList() {
        return pk10EntityArrayList;
    }

    public void setPk10EntityArrayList(ArrayList<Pk10Entity> pk10EntityArrayList) {
        this.pk10EntityArrayList = pk10EntityArrayList;
    }

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

}
