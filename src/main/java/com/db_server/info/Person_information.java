package com.db_server.info;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NAVY on 2017/6/3.
 */
public class Person_information {
    @SerializedName("search")
    private JsonObject search;

    @SerializedName("CarInfo")
    private String CarInfo;

    @SerializedName("State")
    private String State;

    @SerializedName("Type")
    private String Type;

    @SerializedName("CompulsoryInsurance")
    private JsonObject CompulsoryInsurance;

    @SerializedName("RegistrationDate")
    private JsonObject RegistrationDate;

    @SerializedName("CustomerService")
    private String CustomerService;

    @SerializedName("binding")
    private boolean binding;

    @SerializedName("position")
    private String position;

    @SerializedName("information")
    private JsonObject information;

    @SerializedName("header")
    private JsonArray header;

    @SerializedName("body")
    private JsonArray body;




    public JsonObject getInformation() {
        return information;
    }

    public JsonArray getHeader() {
        return header;
    }

    public JsonArray getBody() {
        return body;
    }

    public String getCarInfo() {
        return CarInfo;
    }

    @SerializedName("pages")
    private JsonObject pages;

    public JsonObject getSearch() {
        return search;
    }

    public String getState() {
        return State;
    }

    public String getType() {
        return Type;
    }

    public JsonObject getCompulsoryInsurance() {
        return CompulsoryInsurance;
    }

    public JsonObject getRegistrationDate() {
        return RegistrationDate;
    }

    public String getCustomerService() {
        return CustomerService;
    }

    public boolean isBinding() {
        return binding;
    }

    public String getPosition() {
        return position;
    }

    public JsonObject getPages() {
        return pages;
    }
}
