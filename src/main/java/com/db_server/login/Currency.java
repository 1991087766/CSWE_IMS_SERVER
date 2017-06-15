package com.db_server.login;

import com.db_server.util.MessageCode;
import com.db_server.util.MySqlUtil;
import com.db_server.util.UnDecoder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVY on 2017/6/3.
 */
public class Currency {

    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();
    private JsonObject obj = new JsonObject();
    private JsonObject object = new JsonObject();
    private Person_login person;
    private List list_info;
    private sql_lnfo si;
    private sql_login sl;
    private String md5;

    public static Currency instance;
    public static Currency getInstance(){
        if (instance ==null){
            synchronized (Currency.class){
                if (instance ==null){
                    try {
                        instance =new Currency();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * 解析用户信息
     * @param json
     * @return
     */
    public Person_login getPerson_info(String json){
        obj = (JsonObject)parser.parse(json);
        person= gson.fromJson(obj,Person_login.class);
        return gson.fromJson(person.getInfo(),Person_login.class);
    }

    /**
     * 获取用户信息
     * @param json
     * @return
     */
    public JsonObject getPerson_info_obj(String json){
        obj = (JsonObject)parser.parse(json);
        person= gson.fromJson(obj,Person_login.class);
        return person.getInfo();
    }

    /**
     * 获取页码
     * @param json
     * @return
     */
    public JsonObject getPerson_Pages(String json){
        obj = (JsonObject)parser.parse(json);
        person= gson.fromJson(obj,Person_login.class);
        return person.getPages();
    }

    /**
     * 获取登录信息
     * @param person
     * @return
     */
    public JsonArray getUserList(Person_login person){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_info");
        obj.add("SelectName", parser.parse("['USERNAME']"));
        list_info = new ArrayList();
        list_info.add(person.getUsername());
        obj.add("SelectValue",parser.parse(list_info.toString()));
        JsonArray jsonArray = MySqlUtil.getInstance().sql_data_select(obj,"LIKE","AND",person.getPages());
//        System.out.println(jsonArray.toString());
        return jsonArray;
    }
    public JsonArray getCustomerService(){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_info");
        JsonArray jsonArray = MySqlUtil.getInstance().sql_data_select(obj);
        return jsonArray;
    }

    /**
     * 校验密码
     * @param jsonObject
     * @param person
     * @return
     */
    public boolean getPwd(JsonObject jsonObject, Person_login person){
        si = gson.fromJson(jsonObject,sql_lnfo.class);
        if(si.getPWD().equals(person.getAccess_token())){
            return true;
        }
        return false;
    }

    /**
     * 获取登录状态
     * @param person
     * @return
     */
    public JsonArray getLoginStart(Person_login person){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_login");
        obj.add("SelectName", parser.parse("['USERNAME']"));
        list_info = new ArrayList();
        list_info.add(person.getUsername());
        obj.add("SelectValue", parser.parse(list_info.toString()));
        JsonArray jsonArray = MySqlUtil.getInstance().sql_data_select(obj,"LIKE","AND",person.getPages());
        System.out.println(jsonArray.toString());
        return jsonArray;
    }
    public JsonArray getLoginStart(String username){
        JsonObject obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_login");
        obj.add("SelectName", parser.parse("['USERNAME']"));
        list_info = new ArrayList();
        list_info.add(username);
        obj.add("SelectValue", parser.parse(list_info.toString()));
        System.out.println("getLoginStart:"+obj.toString());
        JsonArray jsonArray = MySqlUtil.getInstance().sql_data_select(obj,"LIKE","AND");

        return jsonArray;
    }

    /**
     * 设置登录状态
     * @param person
     */
    public void setLoginStart(Person_login person,String IP){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_login");
        list_info = new ArrayList();
        list_info.add(person.getUsername());
        list_info.add(DigestUtils.md5Hex(person.getUsername()+person.getIp()+person.getAccess_token()+System.currentTimeMillis()));
        list_info.add(IP);
        list_info.add("");
        list_info.add(System.currentTimeMillis());
        obj.add("SelectValue",parser.parse(list_info.toString()));
        MySqlUtil.getInstance().sql_surface_insert(obj);
    }


    /**
     * 已登录地址检查
     * @param jsonObject
     * @param person
     * @return
     */
    public boolean getLoginToken(JsonObject jsonObject, Person_login person){
        sl = gson.fromJson(jsonObject,sql_login.class);
        if(sl.getACCESS_TOKEN().equals(person.getAccess_token())){
            return true;
        }
        return false;
    }
    public boolean getLoginToken(JsonObject jsonObject, String access_token){
        sl = gson.fromJson(jsonObject,sql_login.class);
        if(sl.getACCESS_TOKEN().equals(access_token)){
            return true;
        }
        return false;
    }

    /**
     * 登录log写入
     * @param person
     * @param i
     */
    public JsonObject setLoginLog(Person_login person, int admin , int i){

        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_log_login");

        md5 = DigestUtils.md5Hex(person.getUsername()+person.getIp()+person.getAccess_token()+System.currentTimeMillis());

        list_info = new ArrayList();
        list_info.add(person.getUsername());
        list_info.add(md5);
        list_info.add(person.getIp());
        list_info.add(i);
        obj.add("SelectValue",parser.parse(list_info.toString()));
        MySqlUtil.getInstance().sql_surface_insert(obj);

        UnDecoder.getInstance().setLoginAlter(person,md5);

        obj = new JsonObject();
        object = new JsonObject();
        object.addProperty("username",person.getUsername());
        object.addProperty("access_token",md5);
        object.addProperty("admin",admin);
        obj.add("access",parser.parse(object.toString()));
        obj.add("code", MessageCode.getInstance().getCode_1001000());
        return obj;
    }
    public void setLoginLog(Person_login person ,int i){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_log_login");
        list_info = new ArrayList();
        list_info.add(person.getUsername());
        list_info.add(DigestUtils.md5Hex(person.getUsername()+person.getIp()+person.getAccess_token()+System.currentTimeMillis()));
        list_info.add(person.getIp());
        list_info.add(i);
        obj.add("SelectValue",parser.parse(list_info.toString()));
        MySqlUtil.getInstance().sql_surface_insert(obj);
    }



}
