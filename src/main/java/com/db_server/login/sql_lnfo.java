package com.db_server.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NAVY on 2017/6/3.
 */
public class sql_lnfo {

    @SerializedName("编号")
    private String ID;

    @SerializedName("账号")
    private String USERNAME;

    @SerializedName("密码")
    private String PWD;

    @SerializedName("性别")
    private String GENDER;

    @SerializedName("混编")
    private String NUMBER;

    @SerializedName("部门")
    private String DEPARTMENT;

    @SerializedName("地址")
    private String ADDRESS;

    @SerializedName("手机")
    private String PHONE;

    @SerializedName("邮件")
    private String MAIL;

    @SerializedName("头像")
    private String ICON;

    @SerializedName("管理员")
    private int ADMIN;

    @SerializedName("更新时间")
    private String UPDATE;

    public String getID() {
        return ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPWD() {
        return PWD;
    }

    public String getGENDER() {
        return GENDER;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getPHONE() {
        return PHONE;
    }

    public String getMAIL() {
        return MAIL;
    }

    public String getICON() {
        return ICON;
    }

    public int getADMIN() {
        return ADMIN;
    }

    public String getUPDATE() {
        return UPDATE;
    }

}
