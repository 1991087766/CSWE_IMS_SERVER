package com.db_server.department;

import com.db_server.MailUtil.Email;
import com.db_server.login.Currency;
import com.db_server.login.Person_login;
import com.db_server.util.MessageCode;
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
 * Created by NAVY on 2017/6/18.
 */

@Controller
public class Department {
    private Person_login Person;
    private Person_login person_login;
    private Person_department department;
    private Person_addAccount addAccount;
    private JsonArray jsonArray;
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();

    private String Id = "";
    private int admin;

    /**
     * 查询库
     * @param data
     * @return
     */
    @RequestMapping(value="getDepartment")
    @ResponseBody
    public String DepartmentService(@RequestBody String data){
        Person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        person_login = gson.fromJson((JsonObject)parser.parse(UnDecoder.getInstance().getUnCode(data)),Person_login.class);
        department = CurrencyDepar.getInstance().getPerson_department(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getUserList(Person);
        if(jsonArray.size()==1){
            admin = jsonArray.get(0).getAsJsonObject().get("管理员").getAsInt();
            jsonArray = Currency.getInstance().getLoginStart(Person);
            if (jsonArray.size()==1){
                if(admin==2){
                    return MessageCode.getInstance().getCode_1001000(
                            CurrencyDepar.getInstance().getInfoList(department,person_login.getPages()),
                            CurrencyDepar.getInstance().getInfoPages(department)
                    ).toString();
                }else {
                    return MessageCode.getInstance().getCode_1002003().toString();
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
     * 获取管理列表
     * @param username
     * @param access_token
     * @return
     */
    @RequestMapping(value="getAdminList")
    @ResponseBody
    public String DepartmentGetAdminList(String username,String access_token){
        jsonArray = Currency.getInstance().getLoginStart(username);
        if (jsonArray.size()==1){
            if(Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),access_token)){
                jsonArray = Currency.getInstance().getAdmin();
                return MessageCode.getInstance().getCode_1001000(jsonArray).toString();
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
     * 设置
     * @param data
     * @return
     */
    @RequestMapping(value="setDepartment")
    @ResponseBody
    public String DepartmentSet(@RequestBody String data){
        Person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        person_login = gson.fromJson((JsonObject)parser.parse(UnDecoder.getInstance().getUnCode(data)),Person_login.class);
        department = CurrencyDepar.getInstance().getPerson_department(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getUserList(Person);
        if(jsonArray.size()==1){
            admin = jsonArray.get(0).getAsJsonObject().get("管理员").getAsInt();
            jsonArray = Currency.getInstance().getLoginStart(Person);
            if (jsonArray.size()==1){
                if(admin==2){
                    if(CurrencyDepar.getInstance().SetDepartment(department)!=0&& CurrencyDepar.getInstance().SetAdministration(department)!=0){
                        return MessageCode.getInstance().getCode_1002005().toString();
                    }else {
                        return MessageCode.getInstance().getCode_1002002().toString();
                    }
                }else {
                    return MessageCode.getInstance().getCode_1002003().toString();
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


    @RequestMapping(value="addAccount")
    @ResponseBody
    public String AddAccount(@RequestBody String data){

        Person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        person_login = gson.fromJson((JsonObject)parser.parse(UnDecoder.getInstance().getUnCode(data)),Person_login.class);
        System.out.println(UnDecoder.getInstance().getUnCode(data));
        addAccount = CurrencyDepar.getInstance().getPerson_addAccount(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getUserList(Person);
        if(jsonArray.size()==1){
            admin = jsonArray.get(0).getAsJsonObject().get("管理员").getAsInt();
            jsonArray = Currency.getInstance().getLoginStart(Person);
            if (jsonArray.size()==1){
                if(admin==2){
                    jsonArray = CurrencyDepar.getInstance().getUserList(addAccount);
                    if(jsonArray.size()<1){
                        Id += System.currentTimeMillis();
                        if(CurrencyDepar.getInstance().setInfo(addAccount.getInfoData(Id))>0){
                            if(addAccount.getMail().contains("@")&&addAccount.getMail().length()>6){
                                Email.getInstance().SondHtmlMail(addAccount.getName(),addAccount.getUsername(),addAccount.getPwd8(),addAccount.getMail());
                            }
                            return MessageCode.getInstance().getCode_1002006().toString();
                        }else {
                            return MessageCode.getInstance().getCode_1002002().toString();
                        }
                    }else {
                        return MessageCode.getInstance().getCode_1001006().toString();
                    }
                }else {
                    return MessageCode.getInstance().getCode_1002003().toString();
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


}
