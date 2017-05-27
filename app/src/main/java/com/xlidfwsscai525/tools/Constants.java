package com.xlidfwsscai525.tools;

/**
 * Created by 张灿能 on 2016/8/9.
 * 作用：保存gid
 */
public class Constants {



    public static final String DEFAULT_PWD = "*&^^ABC))assfgh";//默认设置的密码

    public static final String IS_FRIST_START_APP = "IS_FRIST_START_APP";
    public static final String USER_GID = "users_gid";
    public static final String USER_NAME = "user_name";
    public static final int CLIENT_TYPE = 2;

    public static final int CLIENT_TYPE_PASSENGER = 1;
    public static final int CLIENT_TYPE_RESPONSIBLE = 2;
    public static final int CLIENT_TYPE_DRIVER = 3;
    public static final int CLIENT_TYPE_OTHER = 4;


    public static final String APP_FILE_PATH = "SDCard/Android/data/www.lvchehui.com.carteam/files/"; //缓存路径

    private static final String HTTP_URL = "http://www.4000592122.com/";
    private static final String HTTP_PATH = "xbc_app/H5_driver/";
    public static final String ORDER_GUIDE = HTTP_URL + HTTP_PATH + "order_guide.html";
    public static final String ABOUT_US = HTTP_URL + HTTP_PATH + "about_us.html";
    public static final String OVER_TRAVEL_BILL = HTTP_URL + HTTP_PATH + "over_travel_bill.html";
    public static final String CAR_LEVEL = HTTP_URL + HTTP_PATH + "car_level.html";
    public static final String FAQ = HTTP_URL + HTTP_PATH + "FAQ.html";
    public static final String PRIVACY_POLICY = HTTP_URL + HTTP_PATH + "privacy_policy.html";
    public static final String MEMBERSHIP_AGREEMENT = HTTP_URL + HTTP_PATH + "membership_agreement.html";

    public interface YUNTU_MAP {
        String AMAP_YUNTU_TABLEID = "5796bb0e7bbf1978ba69246d";
        String AMAP_WEB_API_KEY = "1524b138d4f4c2702dc4cdb916a13fc3";
        long AMAP_UPLOAD_INTERVAL = 10000;
    }
}
