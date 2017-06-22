package com.db_server.login;

import com.db_server.util.MySqlUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVY on 2017/6/23.
 */
public class CurrencyLogout {
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();
    private JsonObject obj = new JsonObject();
    private JsonObject object = new JsonObject();
    private Person_logout person;
    private List list_title;
    private List list_info;
    private sql_lnfo si;
    private sql_login sl;
    private String md5;

    public static CurrencyLogout instance;
    public static CurrencyLogout getInstance(){
        if (instance ==null){
            synchronized (CurrencyLogout.class){
                if (instance ==null){
                    try {
                        instance =new CurrencyLogout();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public Person_logout getSearch(String json){
        obj = (JsonObject)parser.parse(json);
        person= gson.fromJson(obj,Person_logout.class);
        return gson.fromJson(person.getSearch(),Person_logout.class);
    }

    /**
     * 修改密码
     * @param Person_logout
     * @return
     */
    public int SetPWD(Person_logout Person_logout,String ID) {


        obj = new JsonObject();
        obj.addProperty("library", "db_server");
        obj.addProperty("SurfaceName", "db_info");


        list_info = new ArrayList();
        list_info.add(Person_logout.getAssect2());

        obj.addProperty("SelectName", "密码");
        obj.add("SelectValue", parser.parse(list_info.toString()));

        obj.add("ColumnName", parser.parse("[密码]"));
        obj.add("Value", parser.parse("[" + Person_logout.getAssect2() + "]"));
        return MySqlUtil.getInstance().sql_data_alter_string(ID,obj);

    }

    /**
     * 修改信息
     * @param Person_logout
     * @return
     */
    public int setPersonalInformation(Person_logout Person_logout,String ID){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_info");

        list_title = new ArrayList();
        list_info = new ArrayList();
        list_title.add("手机");
        list_info.add(Person_logout.getA());
        list_title.add("邮箱");
        list_info.add(Person_logout.getB());
        list_title.add("地址");
        list_info.add(Person_logout.getC());
        obj.add("ColumnName",parser.parse(list_title.toString()));
        obj.add("Value",parser.parse(list_info.toString()));

        return MySqlUtil.getInstance().sql_data_alter_string(ID,obj);
    }
}
