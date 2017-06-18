package com.db_server.department;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NAVY on 2017/6/18.
 */
public class Person_department {

    @SerializedName("search")
    private JsonObject search = null;

    public JsonObject getSearch() {
        return search;
    }

    @SerializedName("depar")
    private String depar = null;

    public String getDepar() {
        return depar;
    }

    @SerializedName("SearchNumber")
    private String SearchNumber = null;

    @SerializedName("setDepartment")
    private String setDepartment = null;

    @SerializedName("setAdministration")
    private String setAdministration = null;

    public String getSearchNumber() {
        return SearchNumber;
    }

    public String getSetDepartment() {
        return setDepartment;
    }

    public String getSetAdministration() {
        return setAdministration;
    }

    @SerializedName("SearchInfo")
    private String searchInfo = null;

    @SerializedName("SearchState")
    private String searchState = null;

    public String getSearchInfo() {
        return searchInfo;
    }

    public String getSearchState() {
        return searchState;
    }

    @SerializedName("编号")
    private String number = null;

    @SerializedName("姓名")
    private String username = null;

    @SerializedName("部门")
    private String department = null;

    @SerializedName("管理")
    private String administration = null;

    @SerializedName("状态")
    private String start = null;

    @SerializedName("入职时间")
    private String entry = null;

    @SerializedName("离职时间")
    private String quit = null;

    @SerializedName("地址")
    private String address = null;


    public String getNumber() {
        return number;
    }

    public String getUsername() {
        return username;
    }

    public String getDepartment() {
        return department;
    }

    public String getAdministration() {
        return administration;
    }

    public String getStart() {
        return start;
    }

    public String getEntry() {
        return entry;
    }

    public String getQuit() {
        return quit;
    }

    public String getAddress() {
        return address;
    }
}
