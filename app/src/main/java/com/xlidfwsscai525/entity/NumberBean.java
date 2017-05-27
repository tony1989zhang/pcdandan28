package com.xlidfwsscai525.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class NumberBean {
    @SerializedName("-appearrate")
    public String appearrate;
    @SerializedName("-aver_omit")
    public String aver_omit;
    @SerializedName("-current_omit")
    public String current_omit;
    @SerializedName("-history_max_omit")
    public String history_max_omit;
    @SerializedName("-last_time_omit")
    public String last_time_omit;
    @SerializedName("-no")
    public String no;
    @SerializedName("-wishrate")
    public String wishrate;

    @Override
    public String toString() {
        return "NumberBean{" +
                "appearrate='" + appearrate + '\'' +
                ", aver_omit='" + aver_omit + '\'' +
                ", current_omit='" + current_omit + '\'' +
                ", history_max_omit='" + history_max_omit + '\'' +
                ", last_time_omit='" + last_time_omit + '\'' +
                ", no='" + no + '\'' +
                ", wishrate='" + wishrate + '\'' +
                '}';
    }
}
