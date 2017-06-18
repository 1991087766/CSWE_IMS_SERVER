package com.db_server.Script;

import com.db_server.login.Currency;
import com.db_server.util.MySqlUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by NAVY on 2017/6/18.
 */
public class Script {

    private JsonArray jsonArray;
    private JsonObject obj;
    private JsonObject jsonObject;
    private int aLong = 2;

    public static Script instance;
    public static Script getInstance(){
        if (instance ==null){
            synchronized (Script.class){
                if (instance ==null){
                    try {
                        instance =new Script();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }


    public void  inspect_login(){
        new Thread(){
            @Override
            public void run() {
                setLogin();
            }
        }.start();
    }

    private void setLogin(){
        while (true){
            jsonArray = Currency.getInstance().getLoginInspect();
            if(jsonArray.size()>0){
                for (int i = 0; i<jsonArray.size();i++){
                    jsonObject = jsonArray.get(i).getAsJsonObject();
                    if(Long.parseLong(jsonObject.get("TIME").getAsString())+86399999*aLong<System.currentTimeMillis()){
                        getLoginLogout(jsonObject.get("USERNAME").getAsString());
                    }
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void getLoginLogout(String USERNAME){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_login");
        obj.addProperty("SelectName", "USERNAME");
        obj.addProperty("SelectValue", USERNAME);
        MySqlUtil.getInstance().sql_data_del(obj);
    }
}
