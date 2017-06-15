package com.db_server.login;

import com.db_server.util.MessageCode;
import com.db_server.util.UnDecoder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by NAVY on 2017/6/2.
 */
@Controller
public class login implements EmbeddedServletContainerCustomizer {
    private Person_login person;
    private JsonArray jsonArray;
    private int admin;


    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(8802);
    }

    @RequestMapping(value="login")
    @ResponseBody
    public String login(@RequestBody String data,HttpServletRequest request){

//        return MessageCode.getInstance().getCode_1001000().toString();
        person = Currency.getInstance().getPerson_info(UnDecoder.getInstance().getUnCode(data));
        jsonArray = Currency.getInstance().getUserList(person);

        //检查账号
        if (jsonArray.size()==1){
            //检查密码
            if (Currency.getInstance().getPwd(jsonArray.get(0).getAsJsonObject(),person)){
                admin = jsonArray.get(0).getAsJsonObject().get("ADMIN").getAsInt();
                //检查是否登录
                jsonArray = Currency.getInstance().getLoginStart(person);
                if(jsonArray.size()!=1){
                    //未登录
//                    session.setAttribute("username",person.getUsername()+"-ip:"+UnDecoder.getInstance().getIp(request)+"-admin:"+admin);
                    Currency.getInstance().setLoginStart(person,UnDecoder.getInstance().getIp(request));
                    Currency.getInstance().setLoginLog(person,1);
                    return Currency.getInstance().setLoginLog(person,admin,1).toString();
                }else{
                    //已登录,检查登录token
                    if(Currency.getInstance().getLoginToken(jsonArray.get(0).getAsJsonObject(),person)){
                        Currency.getInstance().setLoginLog(person,1);
                        return Currency.getInstance().setLoginLog(person,admin,1).toString();
                    }else {
                        return MessageCode.getInstance().getCode_1001005().toString();
                    }
                }
            }else {
                return MessageCode.getInstance().getCode_1001003().toString();
            }
        }else {
            return MessageCode.getInstance().getCode_1001002().toString();
        }

    }


}
