package com.db_server.info;

import com.db_server.login.sql_lnfo;
import com.db_server.util.MySqlUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVY on 2017/6/3.
 */
public class CurrencyInfo {

    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();
    private JsonObject obj = new JsonObject();
    private JsonObject object;
    private List list_info;
    private List list_title;
    private List list_info_date;
    private JsonArray jsonArray;
    private sql_lnfo sinfo;
    private Person_information  person;

    public static CurrencyInfo instance;
    public static CurrencyInfo getInstance(){
        if (instance ==null){
            synchronized (CurrencyInfo.class){
                if (instance ==null){
                    try {
                        instance =new CurrencyInfo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public Person_information getPerson_information(String json){
        obj = (JsonObject)parser.parse(json);
        person= gson.fromJson(obj,Person_information.class);
        return gson.fromJson(person.getSearch(),Person_information.class);
    }

    public Person_information getInformation(String json){
        obj = (JsonObject)parser.parse(json);
        person= gson.fromJson(obj,Person_information.class);
        return gson.fromJson(person.getInformation(),Person_information.class);
    }

    /**
     * 获取数据信息
     * @param person_information
     * @return
     */
    public JsonArray getInfoList(Person_information person_information){
        return MySqlUtil.getInstance().sql_data_select(setSelectInfo(person_information),"LIKE","AND",person_information.getPages());
    }
    public JsonObject getInfoPages(Person_information person_information){
        object = new JsonObject();
        object.addProperty("total",MySqlUtil.getInstance().sql_data_count(setSelectInfo(person_information),"LIKE","AND"));
        return object;
    }
    public JsonObject setSelectInfo(Person_information person_information){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_customer_all");
        list_title= new ArrayList();
        list_info = new ArrayList();

        if (person_information.getCarInfo()!=null && person_information.getCarInfo()!=""&&person_information.getCarInfo().length()!=0){
            list_title.add("PLATER_NUMBER");
            list_title.add("PHONE");
            list_title.add("CUSTUMER_NAME");
            list_title.add("VIN_NO");
            list_title.add("IDCARD");
            list_title.add("ENGINE");
            list_title.add("ADDRESS");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
        }
//        System.out.println("??"+person_information.getCustomerService()!=null && person_information.getCustomerService()!=""&&person_information.getCustomerService().equals("ALL"));
        if (person_information.getCustomerService()!=null && person_information.getCustomerService()!=""&& !person_information.getCustomerService().equals("ALL")){
            list_title.add("CUSTOMER_SERVICE");
            list_info.add(person_information.getCustomerService());
        }
//        System.out.println("list_info:"+list_info);
        if (person_information.getState()!=null && person_information.getState()!="" && !person_information.getState().equals("ALL")){
            list_title.add("STATE");
            list_info.add(person_information.getState());
        }

        list_info_date = new ArrayList();
        System.out.println("getCompulsoryInsurance-startDate:"+person_information.getCompulsoryInsurance().getAsJsonObject().get("startDate").isJsonNull());
        if( !person_information.getCompulsoryInsurance().getAsJsonObject().get("startDate").isJsonNull() ){
            list_info_date.add(person_information.getCompulsoryInsurance().getAsJsonObject().get("startDate").getAsLong());
        }else {
            list_info_date.add(0);
        }
        System.out.println("getCompulsoryInsurance-endDate:"+person_information.getCompulsoryInsurance().getAsJsonObject().get("endDate").isJsonNull());
        if( !person_information.getCompulsoryInsurance().getAsJsonObject().get("endDate").isJsonNull() ){
            list_info_date.add(person_information.getCompulsoryInsurance().getAsJsonObject().get("endDate").getAsLong());
        }else {
            list_info_date.add(System.currentTimeMillis()/1000);
        }
        obj.add("SelectValueCOMMERCIAL", parser.parse(list_info_date.toString()));
        list_info_date = new ArrayList();
        if ( !person_information.getRegistrationDate().getAsJsonObject().get("startDate").isJsonNull() ){
            list_info_date.add(person_information.getRegistrationDate().getAsJsonObject().get("startDate").getAsLong());

        }else {
            list_info_date.add(0);
        }
        if ( !person_information.getRegistrationDate().getAsJsonObject().get("endDate").isJsonNull() ){
            list_info_date.add(person_information.getRegistrationDate().getAsJsonObject().get("endDate").getAsLong());
        }else {
            list_info_date.add(System.currentTimeMillis()/1000);
        }
        obj.add("SelectValueCOMPULSORY", parser.parse(list_info_date.toString()));
        obj.add("SelectName", parser.parse(list_title.toString()));
        obj.add("SelectValue",parser.parse(list_info.toString()));
        obj.addProperty("Binding", person_information.isBinding());
        return obj;
    }

    /**
     * 导入数据
     * @param jsonArray
     */
    public void setInformation(JsonArray jsonArray){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_customer_all");
        obj.add("SelectValue",jsonArray);
        MySqlUtil.getInstance().sql_surface_insert_list(obj);
    }

    /**
     * 删除
     * @param jsonArray
     */
    public int delInformation(JsonArray jsonArray){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_customer_all");
        obj.addProperty("SelectName", "ID");
        obj.add("SelectValue",jsonArray);
        return MySqlUtil.getInstance().sql_data_del(obj);
    }

}
