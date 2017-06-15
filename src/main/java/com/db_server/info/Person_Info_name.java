package com.db_server.info;


import com.google.gson.annotations.SerializedName;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by NAVY on 2017/6/11.
 */
public class Person_Info_name {

    private UUID uuid ;


    @SerializedName("车牌号")
    private String a = null;

    @SerializedName("车架号")
    private String b = null;

    @SerializedName("客户名称")
    private String c = null;

    @SerializedName("品牌")
    private String d = null;

    @SerializedName("车型号")
    private String e = null;

    @SerializedName("发动机号")
    private String f = null;

    @SerializedName("固定电话")
    private String g = null;

    @SerializedName("手机")
    private String h = null;

    @SerializedName("身份证号")
    private String i = null;

    @SerializedName("车座位数")
    private int j = 0;

    @SerializedName("商业险日期")
    private String k = null;

    @SerializedName("交强险日期")
    private String l = null;

    @SerializedName("登记日期")
    private String m = null;

    @SerializedName("地址")
    private String n = null;

    @SerializedName("车价格")
    private String o = null;

    @SerializedName("是否初保")
    private String p = null;

    @SerializedName("客服工号")
    private String q = null;

    @SerializedName("状态")
    private String r = null;

    @SerializedName("备注")
    private String s = null;

    @SerializedName("编号")
    private String t = null;

    private String getA() {
        return "'"+a+"',";
    }

    private String getB() {
        return "'"+b+"',";
    }

    private String getC() {
        return "'"+c+"',";
    }

    private String getD() {
        return "'"+d+"',";
    }

    private String getE() {
        return "'"+e+"',";
    }

    private String getF() {
        return "'"+f+"',";
    }

    private String getG() {
        return "'"+g+"',";
    }

    private String getH() {
        return "'"+h+"',";
    }

    private String getI() {
        return "'"+i+"',";
    }

    private String getJ() {
        return j+",";
    }

    private String getK() {
        return Date2TimeStamp(k)+",";
    }

    private String getL() {
        return Date2TimeStamp(l)+",";
    }

    private String getM() {
        return Date2TimeStamp(m)+",";
    }

    private String getN() {
        return "'"+n+"',";
    }

    private String getO() {
        return "'"+o+"',";
    }

    private String getP() {
        return "'"+p+"',";
    }

    private String getQ() {
        return "'"+q+"',";
    }

    private String getR() {
        return "'"+r+"',";
    }

    private String getS() {
        return "'"+s+"'";
    }

    private String getT(int i) {
        return "'"+System.currentTimeMillis()+"."+i+"',";
    }


    private long dateToStamp(String s)  {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(s).getTime();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return 0;
    }


    private String stampToDate(String s){
        return new SimpleDateFormat("MM/dd/yy").format(new Date(new Long(s)));
    }
    private String stampToDate(long s){
        return new SimpleDateFormat("MM/dd/yy").format(new Date(s));
    }

    private long Date2TimeStamp(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            return sdf.parse(dateStr+" 12:0:0").getTime()/1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getData(int i){

        return "["+getT(i)+getA()+getB()+getC()+getD()+getE()+getF()+getG()+getH()+getI()+getJ()+getK()+getL()+getM()+getN()+getO()+getP()+getQ()+getR()+getS()+"]";
    }
}
