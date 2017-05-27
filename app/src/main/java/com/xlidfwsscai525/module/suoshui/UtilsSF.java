package com.xlidfwsscai525.module.suoshui;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class UtilsSF {

    public static ArrayList<Plsz> SuoShui(ArrayList<String> oneLists, ArrayList<String> twoLists) {
        ArrayList<Plsz> plszArrayList = new ArrayList<Plsz>();
        for (int j = 0; j < oneLists.size(); j++) {
            for (int z = 0; z < twoLists.size(); z++) {
                Plsz plsz = new Plsz(oneLists.get(j), twoLists.get(z));
                System.out.println("plsz:" + plsz.toString());
                plszArrayList.add(plsz);
            }
        }
        return plszArrayList;
    }

    public static String DaXiao(Plsz plsz) {

        int big = 0;
        int smail = 0;
        if (Integer.valueOf(plsz.thesingle) > 4)
        {   big ++;}
        else {smail ++;}

        if (Integer.valueOf(plsz.thedouble) > 4)
        {   big ++;}
         else {smail++;}
        return big+ ":" + smail;
    }

    public static String getDXGLV(Plsz plsz)
    {
        String a = "小";
        String b = "小";
        if (Integer.valueOf(plsz.thesingle) > 4){
            a = "大";
        }
        if (Integer.valueOf(plsz.thedouble) > 4){
            b = "大";
        }
        return a +  b;
    }

    public static String getJiOuLV(Plsz plsz){
        String a = "奇";
        String b = "奇";
        if (Integer.valueOf(plsz.thesingle) % 2 == 0)
            a = "偶";
        if (Integer.valueOf(plsz.thedouble) % 2 == 0)
            a = "偶";
        return a + b;
    }

    public static String JiOu(Plsz plsz) {
        int odd = 0;
        int even = 0;
        if (Integer.valueOf(plsz.thesingle) % 2 == 0)
            even++;
        else odd++;
        if (Integer.valueOf(plsz.thedouble) % 2 == 0) even++;
        else odd++;
        return odd + ":" + even;
    }
    public static int HeZhi(Plsz plsz) {
        int sum = Integer.valueOf(plsz.thesingle) + Integer.valueOf(plsz.thedouble);
        return sum;
    }

    //大大
    public static ArrayList<Plsz>  getPlszBB(ArrayList<Plsz> plszArrayList)
    {
     ArrayList<Plsz> bbPlszLists =   new ArrayList<>();
        for (int i = 0;i < plszArrayList.size();i++)
        {
            Plsz plsz = plszArrayList.get(i);
            if (Integer.valueOf(plsz.thesingle) > 4 && Integer.valueOf(plsz.thedouble) > 4)
            {
                bbPlszLists.add(plsz);
            }
        }
        return bbPlszLists;
    }

    //大小
    public static ArrayList<Plsz>  getPlszBS(ArrayList<Plsz> plszArrayList)
    {
        ArrayList<Plsz> bbPlszLists =   new ArrayList<>();
        for (int i = 0;i < plszArrayList.size();i++)
        {
            Plsz plsz = plszArrayList.get(i);
            if (Integer.valueOf(plsz.thesingle) > 4 && Integer.valueOf(plsz.thedouble) < 5)
            {
                bbPlszLists.add(plsz);
            }
        }
        return bbPlszLists;
    }
    //小小
    public static ArrayList<Plsz>  getPlszSS(ArrayList<Plsz> plszArrayList)
    {
        ArrayList<Plsz> bbPlszLists =   new ArrayList<>();
        for (int i = 0;i < plszArrayList.size();i++)
        {
            Plsz plsz = plszArrayList.get(i);
            if (Integer.valueOf(plsz.thesingle) < 5 && Integer.valueOf(plsz.thedouble) < 5)
            {
                bbPlszLists.add(plsz);
            }
        }
        return bbPlszLists;
    }

    //小大
    public static ArrayList<Plsz>  getPlszSB(ArrayList<Plsz> plszArrayList)
    {
        ArrayList<Plsz> bbPlszLists =   new ArrayList<>();
        for (int i = 0;i < plszArrayList.size();i++)
        {
            Plsz plsz = plszArrayList.get(i);
            if (Integer.valueOf(plsz.thesingle) < 5 && Integer.valueOf(plsz.thedouble) > 4)
            {
                bbPlszLists.add(plsz);
            }
        }
        return bbPlszLists;
    }

    //奇奇
    public static ArrayList<Plsz> getPlszOO(ArrayList<Plsz> plszArrayList){
        ArrayList<Plsz> plszLists = new ArrayList<>();
        for (int i = 0;i < plszArrayList.size();i++)
        {
            Plsz plsz = plszArrayList.get(i);
            if (Integer.valueOf(plsz.thesingle) %2 != 0 && Integer.valueOf(plsz.thedouble) %2 != 0){
                plszLists.add(plsz);
            }
        }
        return plszLists;
    }
    //奇偶
    public static ArrayList<Plsz> getPlszOE(ArrayList<Plsz> plszArrayList){
        ArrayList<Plsz> plszLists = new ArrayList<>();
        for (int i = 0;i < plszArrayList.size();i++)
        {
            Plsz plsz = plszArrayList.get(i);
            if (Integer.valueOf(plsz.thesingle) %2 != 0 && Integer.valueOf(plsz.thedouble) % 2== 0){
                plszLists.add(plsz);
            }
        }
        return plszLists;
    }

    //偶偶
    public static ArrayList<Plsz> getPlszEE(ArrayList<Plsz> plszArrayList){
        ArrayList<Plsz> plszLists = new ArrayList<>();
        for (int i = 0;i < plszArrayList.size();i++)
        {
            Plsz plsz = plszArrayList.get(i);
            if (Integer.valueOf(plsz.thesingle) %2 == 0 && Integer.valueOf(plsz.thedouble) % 2== 0){
                plszLists.add(plsz);
            }
        }
        return plszLists;
    }

    //偶奇
    public static ArrayList<Plsz> getPlszEO(ArrayList<Plsz> plszArrayList){
        ArrayList<Plsz> plszLists = new ArrayList<>();
        for (int i = 0;i < plszArrayList.size();i++)
        {
            Plsz plsz = plszArrayList.get(i);
            if (Integer.valueOf(plsz.thesingle) %2 == 0 && Integer.valueOf(plsz.thedouble) % 2 != 0){
                plszLists.add(plsz);
            }
        }
        return plszLists;
    }
}
