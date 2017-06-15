package com.db_server.login;

import com.db_server.util.MessageCode;
import com.db_server.util.UnDecoder;
import com.google.gson.JsonArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by NAVY on 2017/6/11.
 */
@Controller
public class loading {

    private Person_login person;
    private JsonArray jsonArray;
    private HttpSession session;
    private int admin;
    private String token;

    @RequestMapping(value="loading")
    @ResponseBody
    public String loading(@RequestBody String data, HttpServletRequest request){
        person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getUserList(person);
        if (jsonArray.size()==1){
            admin = jsonArray.get(0).getAsJsonObject().get("ADMIN").getAsInt();
            jsonArray = Currency.getInstance().getLoginStart(person);
            if (jsonArray.size()==1){
                if(Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),person)){
                    token = UnDecoder.getInstance().getMd5(person);
                    UnDecoder.getInstance().setLoginAlter(person,token);
                    return MessageCode.getInstance().getCode_1001000(jsonArray.get(0).getAsJsonObject(),token,admin).toString();
                }else {
                    return MessageCode.getInstance().getCode_1001004().toString();
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
