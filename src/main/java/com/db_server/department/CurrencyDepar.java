package com.db_server.department;

import com.db_server.info.Person_information;
import com.db_server.util.MySqlUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVY on 2017/6/18.
 */
public class CurrencyDepar {

    private JsonParser parser= new JsonParser();
    private JsonObject obj;
    private JsonObject jsonObject;
    private Gson gson = new Gson();
    private Person_department department;

    private List list_info;
    private List list_title;


    public static CurrencyDepar instance;
    public static CurrencyDepar getInstance(){
        if (instance ==null){
            synchronized (CurrencyDepar.class){
                if (instance ==null){
                    try {
                        instance =new CurrencyDepar();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }


    public Person_department getPerson_department(String json){
        obj = (JsonObject)parser.parse(json);
        department= gson.fromJson(obj,Person_department.class);
        return gson.fromJson(department.getSearch(),Person_department.class);
    }

    public JsonArray getInfoList(Person_department department,JsonObject page){
        return MySqlUtil.getInstance().sql_data_select(setSelectInfo(department),"LIKE","OR",page);
    }

    public JsonObject setSelectInfo(Person_department department){
        obj = new JsonObject();
        obj.addProperty("library","db_server");
        obj.addProperty("SurfaceName","db_department");
        list_title = new ArrayList();
        list_info = new ArrayList();
        if (department.getSearchInfo()!=null&&department.getSearchInfo().replaceAll(" ","").length()!=0){
            list_title.add("姓名");
            list_title.add("部门");
            list_title.add("管理");
            list_info.add("%"+department.getSearchInfo()+"%");
            list_info.add("%"+department.getSearchInfo()+"%");
            list_info.add("%"+department.getSearchInfo()+"%");
        }
        if (!department.getSearchState().equals("*")){
            list_title.add("状态");
            list_info.add(department.getSearchState());
        }
        obj.add("SelectName",parser.parse(list_title.toString()));
        obj.add("SelectValue",parser.parse(list_info.toString()));
        return obj;
    }
    public JsonObject getInfoPages(Person_department department){
        jsonObject = new JsonObject();
        jsonObject.addProperty("total",MySqlUtil.getInstance().sql_data_count(setSelectInfo(department),"LIKE","OR"));
        return jsonObject;
    }


    /**
     * 修改
     * @param Person_department
     * @return
     */
    public int SetDepartment(Person_department Person_department){

        if (Person_department.getSetDepartment().length()>1){
            obj = new JsonObject();
            obj.addProperty("library","db_server");
            obj.addProperty("SurfaceName","db_department");


            list_info = new ArrayList();
            list_info.add(Person_department.getSearchNumber());

            obj.addProperty("SelectName", "编号");
            obj.add("SelectValue", parser.parse(list_info.toString()));

            obj.add("ColumnName",parser.parse("部门"));
            obj.add("Value",parser.parse("["+Person_department.getSetDepartment()+"]"));
            return MySqlUtil.getInstance().sql_data_alter(obj);
        }else {
            return 1;
        }

    }

    public int SetAdministration(Person_department Person_department){


        if (Person_department.getSetAdministration().length()>1){
            obj = new JsonObject();
            obj.addProperty("library","db_server");
            obj.addProperty("SurfaceName","db_department");


            list_info = new ArrayList();
            list_info.add(Person_department.getSearchNumber());

            obj.addProperty("SelectName", "编号");
            obj.add("SelectValue", parser.parse(list_info.toString()));

            obj.add("ColumnName",parser.parse("管理"));
            obj.add("Value",parser.parse("["+Person_department.getSetAdministration()+"]"));
            return MySqlUtil.getInstance().sql_data_alter(obj);
        }else {
            return 1;
        }
    }


}
