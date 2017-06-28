package com.db_server.login;

import com.db_server.info.Person_AfterCustomer;
import com.db_server.info.Person_information;
import com.db_server.util.MessageCode;
import com.db_server.util.MySqlUtil;
import com.db_server.util.UnDecoder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by NAVY on 2017/6/2.
 */
@Controller
public class logout {
    private JsonArray jsonArray;
    private JsonObject jsonObject;
    private JsonObject obj;
    private Person_login Person;
    private Person_logout pl;
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();

    /**
     * 退出登录
     * @param username
     * @param access_token
     * @return
     */
    @RequestMapping(value="logout")
    @ResponseBody
    public String getLogout(String username,String access_token){

        jsonArray = Currency.getInstance().getLoginStart(username);
        if (jsonArray.size()==1){
            if(Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),access_token)){
                if(del_Iogin(access_token)==1){
                    return MessageCode.getInstance().getCode_1002004().toString();
                }else {
                    return MessageCode.getInstance().getCode_1003001().toString();
                }

            }else {
                return MessageCode.getInstance().getCode_1001004().toString();
            }
        }else if (jsonArray.size()>1){
            return MessageCode.getInstance().getCode_1001004().toString();
        }else{
            return MessageCode.getInstance().getCode_1001007().toString();
        }
    }

    /**
     * 获取个人主页信息
     * @param username
     * @param access_token
     * @return
     */
    @RequestMapping(value="getIndividual")
    @ResponseBody
    public String getIndividual(String username,String access_token){

        jsonArray = Currency.getInstance().getLoginStart(username);
        if (jsonArray.size()==1){
            if(Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),access_token)){
                jsonArray = Currency.getInstance().getUserInfo(username);
                return MessageCode.getInstance().getCode_1001000(jsonArray.get(0).getAsJsonObject()).toString();
            }else {
                return MessageCode.getInstance().getCode_1001004().toString();
            }
        }else if (jsonArray.size()>1){
            return MessageCode.getInstance().getCode_1001004().toString();
        }else{
            return MessageCode.getInstance().getCode_1001007().toString();
        }
    }

    /**
     * 修改密码
     * @param data
     * @return
     */
    @RequestMapping(value="setPInformation")
    @ResponseBody
    public String setPInformation(@RequestBody String data){

        Person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        pl = CurrencyLogout.getInstance().getSearch(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getUserList(Person);
        if(jsonArray.size()==1){
            obj = jsonArray.get(0).getAsJsonObject();
            jsonArray = Currency.getInstance().getLoginStart(Person);
            if (jsonArray.size()==1){
                if(obj.get("密码").getAsString().equals(pl.getAssect1())){
                    if (CurrencyLogout.getInstance().SetPWD(pl,obj.get("编号").toString())==1){
                        return MessageCode.getInstance().getCode_1002007().toString();
                    }else {
                        return MessageCode.getInstance().getCode_1003001().toString();
                    }
                }else {
                    return MessageCode.getInstance().getCode_1001003().toString();
                }
            }else if (jsonArray.size()>1){
                return MessageCode.getInstance().getCode_1001004().toString();
            }else{
                return MessageCode.getInstance().getCode_1001007().toString();
            }
        }else {
            return MessageCode.getInstance().getCode_1001002().toString();
        }

    }

    /**
     * 修改用户信息
     * @param data
     * @return
     */
    @RequestMapping(value="setPersonalInformation")
    @ResponseBody
    public String setPersonalInformation(@RequestBody String data){
        System.out.println(data);
        Person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        pl = CurrencyLogout.getInstance().getSearch(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getUserList(Person);
        if(jsonArray.size()==1){
            obj = jsonArray.get(0).getAsJsonObject();
            jsonArray = Currency.getInstance().getLoginStart(Person);
            if (jsonArray.size()==1){
                if (CurrencyLogout.getInstance().setPersonalInformation(pl,obj.get("编号").getAsString())==1){
                    return MessageCode.getInstance().getCode_1002005().toString();
                }else {
                    return MessageCode.getInstance().getCode_1003001().toString();
                }
            }else if (jsonArray.size()>1){
                return MessageCode.getInstance().getCode_1001004().toString();
            }else{
                return MessageCode.getInstance().getCode_1001007().toString();
            }
        }else {
            return MessageCode.getInstance().getCode_1001002().toString();
        }

    }




    /**
     * 删除登录
     * @param access_token
     */
    private int del_Iogin(String access_token){
        jsonObject = new JsonObject();
        jsonObject.addProperty("library","db_server");
        jsonObject.addProperty("SurfaceName","db_login");
        jsonObject.addProperty("SelectName", "ACCESS_TOKEN");
        jsonObject.addProperty("SelectValue", access_token);
        return MySqlUtil.getInstance().sql_data_del(jsonObject);
    }
}
