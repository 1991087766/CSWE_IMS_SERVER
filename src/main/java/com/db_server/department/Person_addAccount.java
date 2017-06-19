package com.db_server.department;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xc on 2017/6/19.
 */
public class Person_addAccount {
    @SerializedName("search")
    private JsonObject search;

    @SerializedName("Id")
    private long Id;

    @SerializedName("Username")
    private String Username;

    @SerializedName("Name")
    private String Name;

    @SerializedName("Gender")
    private String Gender;

    @SerializedName("Department")
    private String Department;

    @SerializedName("Address")
    private String Address;

    @SerializedName("Phone")
    private String Phone;

    @SerializedName("Mail")
    private String Mail;

    @SerializedName("admin")
    private int Admin;

    @SerializedName("Leader")
    private String Leader;



    public JsonObject getSearch() {
        return search;
    }

    public void setId() {
        this.Id = System.currentTimeMillis();
    }
    public long getId() {
        return Id;
    }

    public String getUsername() {
        return Username;
    }

    public String getName() {
        return Name;
    }

    public String getGender() {
        return Gender;
    }

    public String getDepartment() {
        return Department;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone() {
        return Phone;
    }

    public String getMail() {
        return Mail;
    }

    public int getAdmin() {
        return Admin;
    }

    public String getLeader() {
        return Leader;
    }

    public String getInfoData(){
        setId();
        return "["+getId()+","+getUsername()+","+getName()
                +","+getGender()+","+getDepartment()+","
                +getAddress()+","+getPhone()+","+getMail()
                +getAdmin()+","+getLeader()+"]";
    }
    public String getDeparData(){
        return "["+getId()+","+getUsername()+","+getName()
                +","+getGender()+","+getDepartment()+","
                +getAddress()+","+getPhone()+","+getMail()
                +getAdmin()+","+getLeader()+"]";
    }

}
