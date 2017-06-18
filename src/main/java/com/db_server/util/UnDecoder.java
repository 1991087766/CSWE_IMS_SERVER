package com.db_server.util;

import com.db_server.login.Currency;
import com.db_server.login.Person_login;
import com.db_server.login.sql_lnfo;
import com.db_server.login.sql_login;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVY on 2017/6/3.
 */
public class UnDecoder {

    private final String ENCODE = "UTF-8";
    private String result = null;
    private JsonArray jsonArray;
    private int admin;

    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();
    private JsonObject obj = new JsonObject();
    private JsonObject object = new JsonObject();
    private Person_login person;
    private List list_info;
    private sql_lnfo si;
    private sql_login sl;
    private String md5;

    public static UnDecoder instance;

    public static UnDecoder getInstance(){
        if (instance ==null){
            synchronized (UnDecoder.class){
                if (instance ==null){
                    try {
                        instance =new UnDecoder();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }


    public boolean login(String data){
        person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getUserList(person);
        //检查账号
        if (jsonArray.size()==1){
            obj = jsonArray.get(0).getAsJsonObject();
            admin = jsonArray.get(0).getAsJsonObject().get("ADMIN").getAsInt();
            //检查登录状态
            jsonArray = Currency.getInstance().getLoginStart(person);
            if(jsonArray.size()==0){
                //未登录，检查密码
                if (Currency.getInstance().getPwd(obj,person)){
                    return true;
                }else {
                    //密码错误
                    return false;
                }
            }else {
                //已登录检查登录地址
                if (Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),person)){
                    return true;
                }else {
                    return false;
                }
            }
        }else if(jsonArray.size()>1){
            return false;
        }else {
            return false;
        }
    }

    public String getMd5(Person_login person) {
        return DigestUtils.md5Hex(person.getUsername()+person.getAccess_token()+System.currentTimeMillis());
    }

    public JsonObject setOperationLog(Person_login person, sql_lnfo sql_lnfo, JsonArray jsonArray){

        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_log_check");

        md5 = getMd5(person);

        list_info = new ArrayList();
        list_info.add(System.currentTimeMillis());
        list_info.add(System.currentTimeMillis());
        list_info.add(person.getUsername());
        list_info.add(sql_lnfo.getDEPARTMENT());
        list_info.add(sql_lnfo.getDEPARTMENT());
        list_info.add(person.getIp());
        list_info.add("查询数据");
        obj.add("SelectValue",parser.parse(list_info.toString()));
        MySqlUtil.getInstance().sql_surface_insert(obj);

        setLoginAlter(person,md5);

        obj = new JsonObject();
        object = new JsonObject();
        object.addProperty("username",person.getUsername());
        object.addProperty("access_token",md5);
        object.addProperty("admin",sql_lnfo.getADMIN());
        obj.add("access",parser.parse(object.toString()));
        obj.add("code", MessageCode.getInstance().getCode_1001000());
        obj.add("information", jsonArray);

        return obj;
    }

    public void setLoginAlter(Person_login person,String token){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_login");
        obj.add("SelectName", parser.parse("['USERNAME']"));
        list_info = new ArrayList();
        list_info.add(person.getUsername());
        obj.add("SelectValue",parser.parse(list_info.toString()));
        obj.add("ColumnName", parser.parse("['ACCESS_TOKEN']"));
        list_info = new ArrayList();
        list_info.add(token);
        obj.add("Value", parser.parse(list_info.toString()));
        MySqlUtil.getInstance().sql_data_alter(obj);
    }




    public String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public String getUnCode(String str) {
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE).replaceAll("}=","}");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String getUnCode(String str,String ENCODE) {

        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


}
