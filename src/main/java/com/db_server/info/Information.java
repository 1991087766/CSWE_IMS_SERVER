package com.db_server.info;

import com.db_server.login.Person_login;
import com.db_server.login.sql_lnfo;
import com.db_server.login.Currency;
import com.db_server.util.MessageCode;
import com.db_server.util.UnDecoder;
import com.db_server.util.UnDecoder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by NAVY on 2017/6/3.
 */

@Controller
public class Information {
    private Person_login Person;
    private sql_lnfo sql_lnfo;
    private Person_information Person_information;
    private Person_delInfo deinfo;
    private Person_Info_name PIN;
    private JsonArray jsonArray;
    private JsonObject jsonObject;
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();

    private HttpSession session;

    private String str;

    /**
     * 查询库
     * @param data
     * @return
     */
    @RequestMapping(value="getInformation")
    @ResponseBody
    public String InformationService(@RequestBody String data){
        Person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));


        Person_information = CurrencyInfo.getInstance().getPerson_information(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getLoginStart(Person);
        if (jsonArray.size()==1){
            if(Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),Person)){
                return MessageCode.getInstance().getCode_1001000(
                        CurrencyInfo.getInstance().getInfoList(Person_information,gson.fromJson((JsonObject)parser.parse(UnDecoder.getInstance().getUnCode(data)),Person_information.class).getPages()),
                        CurrencyInfo.getInstance().getInfoPages(Person_information)
                ).toString();
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
     * 导入
     * @param data
     * @return
     */
    @RequestMapping(value="ImportInfo")
    @ResponseBody
    public String InformationImportInfo(@RequestBody String data){
        Person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        Person_information = CurrencyInfo.getInstance().getInformation(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getLoginStart(Person);
        if (jsonArray.size()==1){
            if(Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),Person)){
                if(Person_information.getBody().size()>0){
                    for (int i = 0;i<Person_information.getBody().size();i++){
                        PIN = gson.fromJson(Person_information.getBody().get(i).getAsJsonObject(),Person_Info_name.class);
                        if (i>=1){
                            str = str+","+ PIN.getData(i);
                        }else {
                            str = PIN.getData(i);
                        }
                    }
                    CurrencyInfo.getInstance().setInformation((JsonArray)parser.parse("["+str+"]"));
                    return MessageCode.getInstance().getCode_1002000().toString();
                }else {
                    return MessageCode.getInstance().getCode_1002001().toString();
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
     * 删除
     * @param data
     * @return
     */
    @RequestMapping(value="delInfo")
    @ResponseBody
    public String InformationDelInfoInfo(@RequestBody String data){

        Person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getLoginStart(Person);
        if (jsonArray.size()==1){
            if(Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),Person)){

                jsonObject = (JsonObject)parser.parse(UnDecoder.getInstance().getUnCode(data));
                deinfo = gson.fromJson(jsonObject,Person_delInfo.class);
                if(CurrencyInfo.getInstance().delInformation(deinfo.getDelInformation(),"db_customer_all","del_customer")){
                    return MessageCode.getInstance().getCode_1003000().toString();
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
     * 获取客服信息
     * @param username
     * @param access_token
     * @return
     */
    @RequestMapping(value="getCustomerService")
    @ResponseBody
    public String InformationCustomerService(String username,String access_token){
        jsonArray = Currency.getInstance().getLoginStart(username);
        if (jsonArray.size()==1){
            if(Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),access_token)){
                jsonArray = Currency.getInstance().getCustomerService();
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
     * 修改
     */
    @RequestMapping(value="ChangeSalesman")
    @ResponseBody
    public String InformationChangeSalesman(@RequestBody String data){
        Person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        Person_information = gson.fromJson((JsonObject)parser.parse(UnDecoder.getInstance().getUnCode(data)),Person_information.class);
        jsonArray = Currency.getInstance().getLoginStart(Person);
        if (jsonArray.size()==1){
            if(Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),Person)){
                if(CurrencyInfo.getInstance().ChangeSalesman(Person_information)!=0){
                    return MessageCode.getInstance().getCode_1002005().toString();
                }else {
                    return MessageCode.getInstance().getCode_1002002().toString();
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
}
