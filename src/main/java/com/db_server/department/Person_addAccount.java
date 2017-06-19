package com.db_server.department;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.codec.digest.DigestUtils;
import sun.security.provider.MD5;

import java.util.Random;

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

    private String Pwd;

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


    public void setPwd(){
        this.Pwd = getRandomString(8);
    }

    public String getPwd8(){
        return Pwd;
    }
    public String getPwd32(){
        return DigestUtils.md5Hex(getUsername()+getPwd8());
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "ABCDEFGHIGKLMNOPQRSTUVW0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
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

    public String getInfoData(String ID){
        setPwd();
        return "['"+ID+"','"+getUsername()+"','"+getPwd32()+"','"+getGender()+"','"+getDepartment()+"','"+getAddress()+"','"+getPhone()+"','"+getMail()+"',"+getAdmin()+",'"+getName()+"',"+null+"]";
    }
    public String getDeparData(String ID){

        return "['"+ID+"','"+getName()+"','"+getDepartment()+"','"+getLeader()+"','在职','"+ID+"','—'"+",'"+getAddress()+"',"+null+"]";
    }

}
