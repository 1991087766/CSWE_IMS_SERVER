package com.db_server.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NAVY on 2017/6/3.
 */
public class sql_lnfo {

    @SerializedName("ID")
    private String ID;

    @SerializedName("USERNAME")
    private String USERNAME;

    @SerializedName("PWD")
    private String PWD;

    @SerializedName("GENDER")
    private String GENDER;

    @SerializedName("NUMBER")
    private String NUMBER;

    @SerializedName("DEPARTMENT")
    private String DEPARTMENT;

    @SerializedName("ADDRESS")
    private String ADDRESS;

    @SerializedName("PHONE")
    private String PHONE;

    @SerializedName("MAIL")
    private String MAIL;

    @SerializedName("ICON")
    private String ICON;

    @SerializedName("ADMIN")
    private int ADMIN;

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

}
