package com.db_server.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by NAVY on 2017/6/3.
 */
public class MessageCode {

    private JsonObject jsonObject;
    private JsonObject object;
    private JsonObject obj;
    private JsonObject obj2;
    public static MessageCode instance;
    public static MessageCode getInstance(){
        if (instance ==null){
            synchronized (MessageCode.class){
                if (instance ==null){
                    try {
                        instance =new MessageCode();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * 请求成功
     * @return
     */
    public JsonObject getCode_1001000(){
        object = new JsonObject();
        object.addProperty("MessageCode",1001000);
        return object;
    }
    /**
     * 导入成功
     * @return
     */
    public JsonObject getCode_1002000(){
        object = new JsonObject();
        object.addProperty("MessageCode",1002000);
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }

    /**
     * 删除成功
     * @return
     */
    public JsonObject getCode_1003000(){
        object = new JsonObject();
        object.addProperty("MessageCode",1003000);
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }

    /**
     * 返回成功信息
     * @param Object
     * @param admin
     * @return
     */
    public JsonObject getCode_1001000(JsonObject Object , String token, int admin){
        object = new JsonObject();
        object.addProperty("MessageCode",1001000);
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        jsonObject.add("access", getCode_userInfo(Object.get("USERNAME").getAsString(),token,admin));
        return jsonObject;
    }

    /**
     * 返回成功信息
     * @param jsonArray
     * @param pages
     * @return
     */
    public JsonObject getCode_1001000(JsonArray jsonArray,JsonObject pages){
        object = new JsonObject();
        object.addProperty("MessageCode",1001000);
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        jsonObject.add("information", jsonArray);
        jsonObject.add("pages", pages);
        return jsonObject;
    }
    public JsonObject getCode_1001000(JsonArray jsonArray){
        object = new JsonObject();
        object.addProperty("MessageCode",1001000);
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        jsonObject.add("information", jsonArray);
        return jsonObject;
    }


    /**
     * 组织返回信息
     * @param username
     * @param token
     * @param admin
     * @return
     */
    public JsonObject getCode_userInfo(String username ,String token, int admin){
        obj = new JsonObject();
        obj.addProperty("username",username);
        obj.addProperty("access_token",token);
        obj.addProperty("admin",admin);
        return obj;
    }

    /**
     * 用户已登录
     * @return
     */
    public JsonObject getCode_1001001(){
        object = new JsonObject();
        object.addProperty("MessageCode",1001001);
        object.addProperty("MsgInfo","用户已登录！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     * 用户不存在
     * @return
     */
    public JsonObject getCode_1001002(){
        object = new JsonObject();
        object.addProperty("MessageCode",1001002);
        object.addProperty("MsgInfo","用户不存在！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     * 密码错误
     * @return
     */
    public JsonObject getCode_1001003(){
        object = new JsonObject();
        object.addProperty("MessageCode",1001003);
        object.addProperty("MsgInfo","密码错误！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     * 用户信息校验异常
     * @return
     */
    public JsonObject getCode_1001004(){
        object = new JsonObject();
        object.addProperty("MessageCode",1001004);
        object.addProperty("MsgInfo","用户信息校验异常！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     *  异地登录
     * @return
     */
    public JsonObject getCode_1001005(){
        object = new JsonObject();
        object.addProperty("MessageCode",1001005);
        object.addProperty("MsgInfo","异地登录！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     * 重复的账户
     * @return
     */
    public JsonObject getCode_1001006(){
        object = new JsonObject();
        object.addProperty("MessageCode",1001006);
        object.addProperty("MsgInfo","重复的账户！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }

    /**
     * 账户未登录
     * @return
     */
    public JsonObject getCode_1001007(){
        object = new JsonObject();
        object.addProperty("MessageCode",1001007);
        object.addProperty("MsgInfo","账户未登录！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }

    /**
     *导入数据格式不正确，请检查导入字段！
     * @return
     */
    public JsonObject getCode_1002001(){
        object = new JsonObject();
        object.addProperty("MessageCode",1002001);
        object.addProperty("MsgInfo","导入数据格式不正确，请检查导入字段！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     * 修改失败
     * @return
     */
    public JsonObject getCode_1002002(){
        object = new JsonObject();
        object.addProperty("MessageCode",1002002);
        object.addProperty("MsgInfo","修改失败");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     * 无访问权限！
     * @return
     */
    public JsonObject getCode_1002003(){
        object = new JsonObject();
        object.addProperty("MessageCode",1002003);
        object.addProperty("MsgInfo","无访问权限！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     *
     * @return
     */
    public JsonObject getCode_1002004(){
        object = new JsonObject();
        object.addProperty("MessageCode",1002004);
        object.addProperty("MsgInfo","");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     * 修改成功
     * @return
     */
    public JsonObject getCode_1002005(){
        object = new JsonObject();
        object.addProperty("MessageCode",1002005);
        object.addProperty("MsgInfo","修改成功！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }

    /**
     * 添加成功
     * @return
     */
    public JsonObject getCode_1002006(){
        object = new JsonObject();
        object.addProperty("MessageCode",1002006);
        object.addProperty("MsgInfo","添加成功！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     *
     * @return
     */
    public JsonObject getCode_1003001(){
        object = new JsonObject();
        object.addProperty("MessageCode",1003001);
        object.addProperty("MsgInfo","操作失败！");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     *
     * @return
     */
    public JsonObject getCode_1003002(){
        object = new JsonObject();
        object.addProperty("MessageCode",1003002);
        object.addProperty("MsgInfo","");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     *
     * @return
     */
    public JsonObject getCode_1003003(){
        object = new JsonObject();
        object.addProperty("MessageCode",1003003);
        object.addProperty("MsgInfo","");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     *
     * @return
     */
    public JsonObject getCode_1003004(){
        object = new JsonObject();
        object.addProperty("MessageCode",1003004);
        object.addProperty("MsgInfo","");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
    /**
     *
     * @return
     */
    public JsonObject getCode_1003005(){
        object = new JsonObject();
        object.addProperty("MessageCode",1003005);
        object.addProperty("MsgInfo","");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }

    /**
     *
     * @return
     */
    public JsonObject getCode_1004004(){
        object = new JsonObject();
        object.addProperty("MessageCode",1004004);
        object.addProperty("MsgInfo","请求失败，请重试");
        jsonObject = new JsonObject();
        jsonObject.add("code", object);
        return jsonObject;
    }
}
