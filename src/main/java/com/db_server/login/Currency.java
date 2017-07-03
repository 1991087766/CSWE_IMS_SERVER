package com.db_server.login;

import com.db_server.util.MessageCode;
import com.db_server.util.MySqlUtil;
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
        obj.add("SelectName", parser.parse("['账号']"));
        list_info = new ArrayList();
        list_info.add(person.getUsername());
        obj.add("SelectValue",parser.parse(list_info.toString()));
        JsonArray jsonArray = MySqlUtil.getInstance().sql_data_select(obj,"LIKE","AND",person.getPages());

        return jsonArray;
    }
    public JsonArray getCustomerService(){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_info");
        JsonArray jsonArray = MySqlUtil.getInstance().sql_data_select(obj);
        return jsonArray;
    }
    public JsonArray getAdmin(){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_info");
        obj.addProperty("SelectName", "管理员");
        obj.addProperty("SelectValue",2);
        JsonArray jsonArray = MySqlUtil.getInstance().sql_data_select_admin(obj);
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
        JsonArray jsonArray = MySqlUtil.getInstance().sql_data_select(obj,"LIKE","AND");

        return jsonArray;
    }

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    public JsonArray getUserInfo(String username){
        JsonObject obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_info");
        obj.add("SelectName", parser.parse("['账号']"));
        list_info = new ArrayList();
        list_info.add(username);
        obj.add("SelectValue", parser.parse(list_info.toString()));
        JsonArray jsonArray = MySqlUtil.getInstance().sql_data_select(obj,12,"LIKE","AND");

        return jsonArray;
    }

    /**
     * 检测登录
     * @return
     */
    public JsonArray getLoginInspect(){
        JsonObject obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_login");
        JsonArray jsonArray = MySqlUtil.getInstance().sql_data_select(obj,1);

        return jsonArray;
    }

    /**
     * 设置登录状态
     * @param person
     */
    public String setLoginStart(Person_login person, String IP){

        String token = DigestUtils.md5Hex(person.getUsername()+person.getIp()+person.getAccess_token()+System.currentTimeMillis());

        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_login");
        list_info = new ArrayList();
        list_info.add(System.currentTimeMillis()+"");
        list_info.add(person.getUsername());
        list_info.add(token);
        list_info.add(IP);
        obj.add("SelectValue",parser.parse(list_info.toString()));
        MySqlUtil.getInstance().sql_surface_insert(obj);
        return token;
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
     * @param admin
     * @param start
     * @param token
     */
    public JsonObject setLoginLog(Person_login person, int admin , String start,String token,String Name){

        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_log_login");

        list_info = new ArrayList();
        list_info.add("'"+System.currentTimeMillis()+"'");
        list_info.add(person.getUsername());
        list_info.add(person.getIp());
        list_info.add(start);
        list_info.add(null);
        obj.add("SelectValue",parser.parse(list_info.toString()));
        MySqlUtil.getInstance().sql_surface_insert(obj);


        obj = new JsonObject();
        object = new JsonObject();
        object.addProperty("username",person.getUsername());
        object.addProperty("access_token",token);
        object.addProperty("admin",admin);
        object.addProperty("Name",Name);
        obj.add("access",parser.parse(object.toString()));
        obj.add("code", MessageCode.getInstance().getCode_1001000());
        return obj;
    }
    public void setLoginLog(Person_login person ,String start){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_log_login");
        list_info = new ArrayList();
        list_info.add("'"+System.currentTimeMillis()+"'");
        list_info.add(person.getUsername());
        list_info.add(person.getIp());
        list_info.add(start);
        list_info.add(null);

        obj.add("SelectValue",parser.parse(list_info.toString()));
        MySqlUtil.getInstance().sql_surface_insert(obj);
    }



}
