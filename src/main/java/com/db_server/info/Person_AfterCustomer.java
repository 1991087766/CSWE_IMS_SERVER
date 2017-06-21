package com.db_server.info;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;

/**
 * Created by NAVY on 2017/6/21.
 */
public class Person_AfterCustomer {
    @SerializedName("search")
    private JsonObject search;

    @SerializedName("编号")
    private String a1 = null;

    @SerializedName("客户名称")
    private String a2 = null;

    @SerializedName("车牌号")
    private String a3 = null;

    @SerializedName("客服")
    private String a4 = null;

    @SerializedName("车架号")
    private String a5 = null;

    @SerializedName("品牌")
    private String a6 = null;

    @SerializedName("车型号")
    private String a7 = null;

    @SerializedName("发动机号")
    private String a8 = null;

    @SerializedName("固定电话")
    private String a9 = null;

    @SerializedName("手机号码")
    private String a10 = null;

    @SerializedName("身份证号")
    private String a11 = null;

    @SerializedName("登记日期")
    private String a12 = null;

    @SerializedName("交强险日期")
    private String a13 = null;

    @SerializedName("商业险日期")
    private String a14 = null;

    @SerializedName("投保公司")
    private String a15 = null;

    @SerializedName("座位数")
    private int a16 = 4;

    @SerializedName("客户类型")
    private String a17 = null;

    @SerializedName("地址")
    private String a18 = null;

    @SerializedName("备注")
    private String a19 = null;

    @SerializedName("状态")
    private String a20 = null;

    public JsonObject getSearch() {
        return search;
    }

    public String getA1() {
        if(a1.replaceAll(" ","").length()!=0){
            return "'"+a1+"'";
        }else {
            return "'—'";
        }
    }

    public String getA2() {
        if(a2.replaceAll(" ","").length()!=0){
            return "'"+a2+"'";
        }else {
            return "'null'";
        }
    }

    public String getA3() {
        if(a3.replaceAll(" ","").length()!=0){
            return "'"+a3+"'";
        }else {
            return "'—'";
        }
    }

    public String getA4() {
        if(a4.replaceAll(" ","").length()!=0){
            return "'"+a4+"'";
        }else {
            return "'—'";
        }
    }

    public String getA5() {
        if(a5.replaceAll(" ","").length()!=0){
            return "'"+a5+"'";
        }else {
            return "'—'";
        }
    }

    public String getA6() {
        if(a6.replaceAll(" ","").length()!=0){
            return "'"+a6+"'";
        }else {
            return "'—'";
        }
    }

    public String getA7() {
        if(a7.replaceAll(" ","").length()!=0){
            return "'"+a7+"'";
        }else {
            return "'—'";
        }
    }

    public String getA8() {

        if(a8.replaceAll(" ","").length()!=0){
            return "'"+a8+"'";
        }else {
            return "'—'";
        }
    }

    public String getA9() {
        if(a9.replaceAll(" ","").length()!=0){
            return "'"+a9+"'";
        }else {
            return "'null'";
        }
    }

    public String getA10() {
        if(a10.replaceAll(" ","").length()!=0){
            return "'"+a10+"'";
        }else {
            return "'null'";
        }
    }

    public String getA11() {
        if(a11.replaceAll(" ","").length()!=0){
            return "'"+a11+"'";
        }else {
            return "'null'";
        }
    }

    public long getA12() {
        return Date2TimeStamp(a12);

    }

    public long getA13() {
        return Date2TimeStamp(a13);

    }

    public long getA14() {
        return Date2TimeStamp(a14);
    }

    public String getA15() {
        if(a15.replaceAll(" ","").length()!=0){
            return "'"+a15+"'";
        }else {
            return "'无'";
        }
    }

    public int getA16() {
        if(a16 != 0){
            return a16;
        }else {
            return 4;
        }
    }

    public String getA17() {
        if(a17.replaceAll(" ","").length()!=0){
            return "'"+a17+"'";
        }else {
            return "'无'";
        }
    }

    public String getA18() {
        if(a18.replaceAll(" ","").length()!=0){
            return "'"+a18+"'";
        }else {
            return "'null'";
        }
    }

    public String getA19() {
        if(a19.replaceAll(" ","").length()!=0){
            return "'"+a19+"'";
        }else {
            return "'null'";
        }
    }

    public String getA20() {
        if(a20.replaceAll(" ","").length()!=0){
            return "'"+a20+"'";
        }else {
            return "'未处理'";
        }
    }


    private long Date2TimeStamp(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            return sdf.parse(dateStr+" 12:00:00").getTime()/1000;
        } catch (Exception e) {
            return 0;
        }

    }
}