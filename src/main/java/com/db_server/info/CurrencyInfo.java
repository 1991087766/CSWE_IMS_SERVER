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

    public Person_AfterCustomer getPerson_AfterCustomern(String json){
        obj = (JsonObject)parser.parse(json);
        person= gson.fromJson(obj,Person_information.class);
        return gson.fromJson(person.getSearch(),Person_AfterCustomer.class);
    }

    /**
     * 获取数据信息
     * @param person_information
     * @return
     */
    public JsonArray getInfoList(Person_information person_information,JsonObject page){
        return MySqlUtil.getInstance().sql_data_select(setSelectInfo(person_information),"LIKE","OR",page);
    }
    public JsonObject getInfoPages(Person_information person_information){
        object = new JsonObject();
        object.addProperty("total",MySqlUtil.getInstance().sql_data_count(setSelectInfo(person_information),"LIKE","OR"));
        return object;
    }
    public JsonObject setSelectInfo(Person_information person_information){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_customer_all");
        list_title= new ArrayList();
        list_info = new ArrayList();

        if (person_information.getCarInfo()!=null && person_information.getCarInfo()!=""&&person_information.getCarInfo().length()!=0){
            list_title.add("车牌号");
            list_title.add("手机");
            list_title.add("客户名称");
            list_title.add("车架号");
            list_title.add("身份证号");
            list_title.add("发动机号");
            list_title.add("地址");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
            list_info.add("%"+person_information.getCarInfo()+"%");
        }
        if (person_information.getCustomerService()!=null && person_information.getCustomerService()!=""&& !person_information.getCustomerService().equals("ALL")){
            list_title.add("客服");
            list_info.add(person_information.getCustomerService());
        }
        if (person_information.getState()!=null && person_information.getState()!="" && !person_information.getState().equals("ALL")){
            list_title.add("状态");
            list_info.add(person_information.getState());
        }

        list_info_date = new ArrayList();
        if( !person_information.getCompulsoryInsurance().getAsJsonObject().get("startDate").isJsonNull() ){
            list_info_date.add(person_information.getCompulsoryInsurance().getAsJsonObject().get("startDate").getAsLong());
        }else {
            list_info_date.add(0);
        }
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
    public boolean delInformation(JsonArray jsonArray,String A,String B){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SelectName", "编号");
        obj.add("SelectValue",jsonArray);
        return MySqlUtil.getInstance().sql_data_del(obj,A,B);
    }

    /**
     * 修改客服
     * @param person_information
     * @return
     */
    public int ChangeSalesman(Person_information person_information){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_customer_all");


        list_info = new ArrayList();
        for(int i = 0;i<person_information.getCheckboxModel().size();i++){
            if(!person_information.getCheckboxModel().get(i).isJsonNull()){
                list_info.add(person_information.getCheckboxModel().get(i).getAsString());
            }
        }

        obj.addProperty("SelectName", "编号");
        obj.add("SelectValue", parser.parse(list_info.toString()));

        obj.add("ColumnName",parser.parse("客服"));
        obj.add("Value",parser.parse("["+person_information.getCustomerService()+"]"));

        return MySqlUtil.getInstance().sql_data_alter(obj);
    }

    /**
     * 修改客服
     * @param afterCustomer
     * @return
     */
    public int AfterCustomer(Person_AfterCustomer afterCustomer){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_customer_all");

        list_title = new ArrayList();
        list_info = new ArrayList();
        list_title.add("客户名称");
        list_info.add(afterCustomer.getA2());
        list_title.add("车牌号");
        list_info.add(afterCustomer.getA3());
        list_title.add("客服");
        list_info.add(afterCustomer.getA4());
        list_title.add("车架号");
        list_info.add(afterCustomer.getA5());
        list_title.add("品牌");
        list_info.add(afterCustomer.getA6());
        list_title.add("车型号");
        list_info.add(afterCustomer.getA7());
        list_title.add("发动机号");
        list_info.add(afterCustomer.getA8());
        list_title.add("固定电话");
        list_info.add(afterCustomer.getA9());
        list_title.add("手机");
        list_info.add(afterCustomer.getA10());
        list_title.add("身份证号");
        list_info.add(afterCustomer.getA11());
        list_title.add("登记日期");
        list_info.add(afterCustomer.getA12());
        list_title.add("交强险日期");
        list_info.add(afterCustomer.getA13());
        list_title.add("商业险日期");
        list_info.add(afterCustomer.getA14());
        list_title.add("投保公司");
        list_info.add(afterCustomer.getA15());
        list_title.add("座位数");
        list_info.add(afterCustomer.getA16());
        list_title.add("客户类型");
        list_info.add(afterCustomer.getA17());
        list_title.add("地址");
        list_info.add(afterCustomer.getA18());
        list_title.add("备注");
        list_info.add(afterCustomer.getA19());
        list_title.add("状态");
        list_info.add(afterCustomer.getA20());
        System.out.println(list_title.toString());
        System.out.println(list_info.toString());
        obj.add("ColumnName",parser.parse(list_title.toString()));
        obj.add("Value",parser.parse(list_info.toString()));

        return MySqlUtil.getInstance().sql_data_alter(afterCustomer.getA1(),obj);
    }
    /**
     * 修改客服
     * @param afterCustomer
     * @return
     */
    public int AfterCustomerState(Person_AfterCustomer afterCustomer){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_customer_all");

        list_title = new ArrayList();
        list_info = new ArrayList();
        list_title.add("状态");
        list_info.add(afterCustomer.getA20());
        obj.add("ColumnName",parser.parse(list_title.toString()));
        obj.add("Value",parser.parse(list_info.toString()));

        System.out.println(obj.toString());
        return MySqlUtil.getInstance().sql_data_alter(afterCustomer.getA1(),obj);
    }


}
