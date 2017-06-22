package com.db_server.login;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NAVY on 2017/6/23.
 */
public class Person_logout {
    @SerializedName("search")
    private JsonObject search;

    @SerializedName("assect1")
    private String assect1 = null;

    @SerializedName("assect2")
    private String assect2 = null;

    @SerializedName("手机")
    private String a = null;

    @SerializedName("邮箱")
    private String b = null;

    @SerializedName("地址")
    private String c = null;

    public JsonObject getSearch() {
        return search;
    }

    public String getAssect1() {
        return assect1;
    }

    public String getAssect2() {
        return assect2;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }
}
