package com.xlidfwsscai525.http;

import com.xlidfwsscai525.entity.HotcoldnumberEntity;
import com.xlidfwsscai525.entity.KaiJiangEntity;
import com.xlidfwsscai525.entity.NewsEntity;
import com.xlidfwsscai525.entity.NumberStatEntity;
import com.xlidfwsscai525.entity.OpenEntity;
import com.xlidfwsscai525.entity.TwofaceanalysisEntity;
import com.xlidfwsscai525.entity.ZouShiEntity;

import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;

import java.util.HashMap;
import java.util.Map;


/**
 * 作者：V先生 on 2016/7/30 17:13
 * 作用：提交网络请求
 */
public class CM {


    private static final String OPEN_URL = "http://888.shof789.com/Home/Outs/index/mchid/59103473db718.html";
    private static final String SHARE_URL = "http://888.shof789.com/Home/Outs/index/mchid/59103477cbfce.html";
    private static final String PK10_OMIT = "http://www.301.hk/pk10/data/pk10_omit.xml";
    private static final String TWO_FACEANALYSIS = "http://m.1396mp.com/api/pk10/twofaceanalysis"; //两面分析
    private static final String HOTOCOL_DNUMBER = "http://m.1396mp.com/api/pk10/hotcoldnumber?"; //两面分析
    private static final String NUMBERTREND_VERSION= "http://m.1396mo.com/ssc/NumberTrend?version=3000&";//ball=0;
    public static final String APP_JIAN_JIE = "http://oqii1pdkq.bkt.clouddn.com/appjj.txt";
    public static final String APP_MZSM = "http://oqii1pdkq.bkt.clouddn.com/mzsm.txt";
    public static final String APP_HELP = "http://oqii1pdkq.bkt.clouddn.com/help.txt";
    public static final String APP_ABOUT = "http://oqii1pdkq.bkt.clouddn.com/about.txt";
    public static String URL_NEWS_LOTTERY = "http://888.shof789.com/Home/Outs/article/type/1";
    public static final String  KAI_JIANG = "http://m.1396mp.com/ssc/GetAwardData?version=3000";
    private static final String Number_Stat = "http://m.1396mp.com/ssc/NumberStat?version=3000";
    private static CM mCM;

    private CM() {
    }

    public static final CM getInstance() {
        if (mCM == null) {
            mCM = new CM();
        }
        return mCM;
    }

    public Cancelable getList(String dateStr,CommonCallback comCb) {
        Map<String, String> params = new HashMap<>();
        return CUtil.Get(dateStr, params, comCb);
    }

    public Cancelable open(ComCb<OpenEntity> comCb) {
        Map<String, String> params = new HashMap<>();
        return CUtil.Get2(OPEN_URL, params, comCb);
    }

    public Cancelable share(ComCb<OpenEntity> comCb){
        Map<String,String> parmas = new HashMap<>();
        return CUtil.Get2(SHARE_URL,parmas,comCb);
    }

    public Cancelable getOmit(CommonCallback comCb){
        Map<String,String> parmas = new HashMap<>();
        return  CUtil.Get2(PK10_OMIT,parmas,comCb);
    }

    public Cancelable twofaceanalysis(CommonCallback<TwofaceanalysisEntity> comCb){
        Map<String,String> parmas = new HashMap<>();
        return CUtil.Get2(TWO_FACEANALYSIS,parmas,comCb);
    }

    public Cancelable hotcoldnumber(ComCb<HotcoldnumberEntity> comCb, int ballNum){
        Map<String,String> parmas = new HashMap<>();
        parmas.put("ball","" + ballNum);
        return CUtil.Get2(HOTOCOL_DNUMBER,parmas,comCb);
    }

    public Cancelable jianjie(ComCb<HotcoldnumberEntity> comCb, int ballNum){
        Map<String,String> parmas = new HashMap<>();
        return CUtil.Get2(APP_JIAN_JIE,parmas,comCb);
    }

    public Cancelable zoushi(ComCb<ZouShiEntity> comCb,int ballNum){
        Map<String,String> parmas = new HashMap<>();
        parmas.put("ball","" + ballNum);
        return CUtil.Get2(NUMBERTREND_VERSION,parmas,comCb);
    }
    public Cancelable news(CommonCallback<NewsEntity> comCb){
        Map<String,String> params = new HashMap<>();
        return CUtil.Get2(URL_NEWS_LOTTERY,params,comCb);

    }
    public Cancelable kaijiang(ComCb<KaiJiangEntity> comCb){
        Map<String,String> params = new HashMap<>();
        return CUtil.Get2(KAI_JIANG,params,comCb);
    }

    public Cancelable numberStat(ComCb<NumberStatEntity> comCb){
          Map<String,String> params = new HashMap<>();
        return CUtil.Get2(Number_Stat,params,comCb);

    }
}
