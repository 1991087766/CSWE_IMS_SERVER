package com.db_server.login;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NAVY on 2017/6/3.
 */
public class Person_login {

    @SerializedName("info")
    private JsonObject info;

    @SerializedName("username")
    private String username;

    @SerializedName("access_token")
    private String access_token;

    @SerializedName("ip")
    private String ip;

    @SerializedName("pages")
    private JsonObject pages;

    @SerializedName("each_page")
    private String each_page;

    @SerializedName("Request")
    private String request;

    public String getRequest() {
        return request;
    }

    public String getEach_page() {
        return each_page;
    }


    public JsonObject getPages() {
        return pages;
    }

    public JsonObject getInfo() {
        return info;
    }

    public String getUsername() {
        return username;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getIp() {
        return ip;
    }
}
