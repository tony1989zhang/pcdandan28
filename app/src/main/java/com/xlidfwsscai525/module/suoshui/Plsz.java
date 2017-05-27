package com.xlidfwsscai525.module.suoshui;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class Plsz implements Serializable  {

    String thesingle;
    String thedouble;

    public Plsz(String thesingle, String thedouble){
        this.thesingle = thesingle;
        this.thedouble = thedouble;
    }

    @Override
    public String toString() {
        return "Plsz{" +
                "thesingle='" + thesingle + '\'' +
                ", thedouble='" + thedouble + '\'' +
                '}';
    }

    public String getThesingle() {
        return thesingle;
    }

    public void setThesingle(String thesingle) {
        this.thesingle = thesingle;
    }

    public String getThedouble() {
        return thedouble;
    }

    public void setThedouble(String thedouble) {
        this.thedouble = thedouble;
    }
}
