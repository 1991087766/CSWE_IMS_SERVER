package com.db_server.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NAVY on 2017/6/3.
 */
public class sql_login {

    @SerializedName("USERNAME")
    private String USERNAME;

    @SerializedName("ACCESS_TOKEN")
    private String ACCESS_TOKEN;

    @SerializedName("IP")
    private String IP;

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }

    public String getIP() {
        return IP;
    }
}
